package com.mintiz.domain.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CommentUpdateReqDto {
    private long commentId;
    private String updateContent;
}
