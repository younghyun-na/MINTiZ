package com.mintiz.domain.dto;

import com.mintiz.domain.ImageFile;
import com.mintiz.domain.Post;
import com.mintiz.domain.TagPost;
import com.mintiz.domain.User;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class PostListResDto {         //메인 페이지 dto

    private long postId;

    private User user;

    private String content;

    private String location;

    private List<ImageFile> images = new ArrayList<>();

    private List<TagPost> tagPosts = new ArrayList<>();

    private LocalDateTime updatedTime;

    public PostListResDto(Post post) {
        this.user = user;
        this.content = content;
        this.location = location;
        this.images = images;
        this.tagPosts = tagPosts;
        this.updatedTime = updatedTime;
    }

    public Post toEntity(){
        return Post.builder()
                .content(content)
                .location(location)
                .images(images)
                .tagPosts(tagPosts)
                .updatedTime(updatedTime).build();
    }
}
