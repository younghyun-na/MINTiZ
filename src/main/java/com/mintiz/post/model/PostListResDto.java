package com.mintiz.post.model;

import com.mintiz.domain.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PostListResDto {         //메인 페이지 dto

    private long postId;

    private long userId;

    private String userName;

    private ImageFile userImg;

    private String content;

    private String location;

    private ImageFile image;

    private String updatedTime;

    private boolean checkBookmark;

    @Builder
    public PostListResDto(Post post, String updatedTime, ImageFile image, Boolean check) {
        this.userId = post.getUser().getId();
        this.postId = post.getId();
        this.userName = post.getUser().getName();
        this.userImg = post.getUser().getProfile();
        this.content = post.getContent();
        this.location = post.getLocation();
        this.updatedTime = updatedTime;
        this.image = image;
        this.checkBookmark = check;
    }

}
