package com.mintiz.post.model;

import com.mintiz.domain.ImageFile;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ImageFileDto {

    private String originFileName;   //파일 원본명

    private String uploadFilePath;      //파일 저장 경로

    private Long fileSize;

    public ImageFile toEntity(){
        return ImageFile.builder()
                .originFileName(originFileName)
                .uploadFilePath(uploadFilePath)
                .fileSize(fileSize)
                .build();
    }
}
