package com.mintiz.post.model;

import com.mintiz.domain.Comment;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentResDto {

    private String content;              //댓글 내용
    private String updateTime;    //댓글 쓴 날짜
    private String userName;           //작성자 이름
    private String userImgUrl;         //작성자 프로필 이미지

    public CommentResDto(Comment comment, String updateTime){
        this.content = comment.getContent();
        this.updateTime = updateTime;
        this.userName = comment.getUser().getName();
        this.userImgUrl = comment.getUser().getProfile();
    }

}
