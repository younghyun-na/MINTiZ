package com.mintiz.domain;
;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class FileStore {


    private String fileDir = "C:/Users/ADMIN/Documents/img/";
    //private String fileDir = "/Users/jimin/dev/img/";
    //private String fileDir = new File("").getAbsolutePath() + File.separator;

    public String getFullPath(String filename) {
        return fileDir + filename;
    }

    public List<ImageFile> storeFiles(List<MultipartFile> multipartFiles) throws IOException {
        List<ImageFile> storeFileResult = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            if(!multipartFile.isEmpty()){
                storeFileResult.add(storeFile(multipartFile));
            }
        }
        return storeFileResult;

    }

    public ImageFile storeFile(MultipartFile multiPartFile) throws IOException {
        if(multiPartFile.isEmpty()){
            return null;
        }

        //서버에 저장하는 파일명, 확장자 포함
        String originalFilename = multiPartFile.getOriginalFilename(); //사용자가 업로드한 파일 이름
        String storeFileName = createStoreFileName(originalFilename);
        multiPartFile.transferTo(new File(getFullPath(storeFileName)));

        ImageFile imageFile = ImageFile.builder()
                .originFileName(originalFilename)
                .uploadFilePath(storeFileName).build();
        return imageFile;
    }

    private String createStoreFileName(String originalFilename) {
        String uuid = UUID.randomUUID().toString();      //uuid로 db에 저장할 이름 생성
        String ext = extractExt(originalFilename);   //확장자 추출
        return uuid + "." + ext;
    }

    private String extractExt(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos + 1);//png
    }
}
