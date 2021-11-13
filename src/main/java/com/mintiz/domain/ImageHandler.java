package com.mintiz.domain;

import com.mintiz.domain.dto.ImageFileDto;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
public class ImageHandler {
    /**
     * List<MultipartFile> 을 전달받아 파일을 저장한 후
     * 관련 정보를 List<ImageFile>로 변환하여 반환할 FileHandler 를 생성
     */

    public List<ImageFile> parseFileInfo(List<MultipartFile> multipartFiles) throws Exception {
        List<ImageFile> imageFileList = new ArrayList<>();

        if (!CollectionUtils.isEmpty(multipartFiles)) {
            // 파일명을 업로드 한 날짜로 변환하여 저장
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");  //이 형식으로 날짜를 지정
            String current_date = now.format(dateTimeFormatter);

            // 프로젝트 디렉터리 내의 저장을 위한 절대 경로 설정
            // File.separator : OS 환경에 맞는 파일 구분자를 제공하는 API
            String absolutePath = new File("").getAbsolutePath() + File.separator + File.separator;

            // 파일을 저장할 세부 경로 지정
            String path = "images" + File.separator + current_date;
            File file = new File(path);

            //디렉터리가 존재하지 않을 경우 생성
            if (!file.exists()) {
                file.mkdirs();
            }

            //다중 파일 처리
            for (MultipartFile multipartFile : multipartFiles) {
                //파일 확장자 추출(이름에서 가져오는거보다 안전)
                String originFileExtension;
                String contentType = multipartFile.getContentType();

                //확장자명이 존재하지 않을 경우(확장자는 jpeg, png만)
                if (ObjectUtils.isEmpty(contentType)) {
                    break;
                } else {
                    if (contentType.contains("image/jpeg"))
                        originFileExtension = ".jpg";
                    else if (contentType.contains("image/png"))
                        originFileExtension = ".png";
                    else
                        break;
                }

                // 파일명 중복 피하고자 나노초까지 얻어와 저장될 파일 이름 지정
                String newFileName = System.nanoTime() + originFileExtension;

                //반환해야할 이미지 리스트에 추가
                ImageFileDto imageFileDto = ImageFileDto.builder()
                        .originFileName(multipartFile.getOriginalFilename())
                        .uploadFilePath(path + File.separator + newFileName)
                        .fileSize(multipartFile.getSize())
                        .build();

                ImageFile imageFile = imageFileDto.toEntity();

                imageFileList.add(imageFile);

                // 업로드 한 파일 데이터를 특정(지정한) 파일로 저장
                file = new File(absolutePath + path + File.separator + newFileName);
                multipartFile.transferTo(file);

                // 파일 권한 설정(쓰기, 읽기)
                file.setWritable(true);
                file.setReadable(true);

            }

        }
        return imageFileList;
    }
}
