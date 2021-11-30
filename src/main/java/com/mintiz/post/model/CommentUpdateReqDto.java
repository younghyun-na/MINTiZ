package com.mintiz.post.model;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CommentUpdateReqDto {
    private long commentId;
    private String updateContent;
}
