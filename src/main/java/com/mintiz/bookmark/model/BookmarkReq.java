package com.mintiz.bookmark.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BookmarkReq {
    private Long userId;
    private Long postId;
}
