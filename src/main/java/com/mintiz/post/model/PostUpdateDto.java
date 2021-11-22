package com.mintiz.post.model;

import com.mintiz.domain.ImageFile;
import com.mintiz.domain.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PostUpdateDto {

    private String content;

    private String tagName;  //tagPost?

    private String location;

    private List<ImageFile> images = new ArrayList<>();

    public Post toEntity(){
        return Post.builder()
                .content(content)
                .location(location)
                .images(images).build();
    }
}
