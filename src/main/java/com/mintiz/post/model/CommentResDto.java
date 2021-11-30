package com.mintiz.post.model;

import com.mintiz.domain.Comment;
import com.mintiz.domain.ImageFile;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentResDto {

    private long commentId;       //화면에 전달해야 해서
    private String content;              //댓글 내용
    private String updateTime;    //댓글 쓴 날짜
    private String userName;           //작성자 이름
    private ImageFile userImgUrl;         //작성자 프로필 이미지
    private long userId;

    public CommentResDto(Comment comment, String updateTime){
        this.commentId = comment.getId();
        this.content = comment.getContent();
        this.updateTime = updateTime;
        this.userName = comment.getUser().getName();
        this.userImgUrl = comment.getUser().getProfile();
        this.userId = comment.getUser().getId();
    }

}
