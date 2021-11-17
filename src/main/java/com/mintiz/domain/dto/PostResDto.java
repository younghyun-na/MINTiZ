package com.mintiz.domain.dto;

import com.mintiz.domain.Post;
import com.mintiz.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PostResDto {     //상세페이지 dto
    private long postId;
    private String tagName;
    private User user;
    private String content;
    private String location;
    private String writeDate;

    public PostResDto(Post post, String tagName) {
        this.user = post.getUser();
        this.content = post.getContent();
        this.location = post.getLocation();
        this.writeDate = post.getCreatedAt().toString();
        this.tagName = tagName;
    }
}
