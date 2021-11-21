package com.mintiz.post.model;

import com.mintiz.domain.ImageFile;
import com.mintiz.domain.Post;
import com.mintiz.domain.TagPost;
import com.mintiz.domain.User;
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

    private String userImg;

    private String content;

    private String location;

    private List<ImageFile> images = new ArrayList<>();

    private String updatedTime;

    public PostListResDto(Post post, String updatedTime) {
        this.userId = post.getUser().getId();
        this.postId = post.getId();
        this.userName = post.getUser().getName();
        this.userImg = post.getUser().getProfile();
        this.content = post.getContent();
        this.location = post.getLocation();
        this.updatedTime = updatedTime;
    }

}
