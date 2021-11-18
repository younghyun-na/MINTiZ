package com.mintiz.post.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommentSaveDto {
    private long userId;     //작성자 ID
    private long postId;      //작성 게시글 ID
    private String content;    //작성할 내용 글
}
