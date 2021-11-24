package com.mintiz.post.model;

import com.mintiz.domain.ImageFile;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor( access= AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class PostUpdateDto {

    private String content;

    private String tagName;

    private String location;

    private List<MultipartFile> newImages;

    private List<ImageFile> images;
}
