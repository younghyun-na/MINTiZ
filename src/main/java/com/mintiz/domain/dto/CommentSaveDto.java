package com.mintiz.domain.dto;

import com.mintiz.domain.Comment;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CommentSaveDto {
    private long userId;     //작성자 ID
    private long postId;      //작성 게시글 ID
    private String content;    //작성할 내용 글
}
