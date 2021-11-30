package com.mintiz.bookmark.model;

import com.mintiz.domain.ImageFile;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookmarkRes {
    private Long postId;
    private ImageFile image;
}
