package com.mintiz.domain.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CommentGetResDto {

    private String content;
    private String userName;
    private String userImgUrl;
    private LocalDateTime updateTime;

}
