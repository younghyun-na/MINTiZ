package com.mintiz.profile.model;

import com.mintiz.domain.ImageFile;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WritingRes {
    private Long postId;
    private ImageFile image;
}
