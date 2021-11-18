package com.mintiz.post.model;

import com.mintiz.domain.ImageFile;
import com.mintiz.domain.Post;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class PostUpdateDto {

    private String content;

    private String location;

    private List<ImageFile> images = new ArrayList<>();

    public Post toEntity(){
        return Post.builder()
                .content(content)
                .location(location)
                .images(images).build();
    }
}
