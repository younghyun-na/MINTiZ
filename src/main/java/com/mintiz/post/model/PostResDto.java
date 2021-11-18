package com.mintiz.post.model;

import com.mintiz.domain.Post;
import com.mintiz.domain.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostResDto {     //상세페이지 dto + 댓글도..?
    private long postId;
    private String tagName;
    private User user;
    private String content;
    private String location;
    private String writeDate;

    public PostResDto(Post post, String tagName) {
        this.postId = post.getId();
        this.user = post.getUser();
        this.content = post.getContent();
        this.location = post.getLocation();
        this.writeDate = post.getCreatedAt().toString();
        this.tagName = tagName;
    }
}
