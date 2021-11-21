package com.mintiz.post.model;

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

    private String updatedTime;

    public PostListResDto(Post post, String updatedTime) {
        this.user = post.getUser();
        this.content = post.getContent();
        this.location = post.getLocation();
        this.tagPosts = post.getTagPosts();
        this.updatedTime = updatedTime;
    }

    public Post toEntity(){
        return Post.builder()
                .content(content)
                .location(location)
                .images(images)
                .tagPosts(tagPosts).build();
    }
}
