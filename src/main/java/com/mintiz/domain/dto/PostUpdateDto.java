package com.mintiz.domain.dto;

import com.mintiz.domain.ImageFile;
import com.mintiz.domain.Post;
import com.mintiz.domain.TagPost;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class PostUpdateDto {

    private String content;

    private String location;

    private List<ImageFile> images = new ArrayList<>();

    private LocalDateTime updatedTime;

    public Post toEntity(){
        return Post.builder()
                .content(content)
                .location(location)
                .images(images)
                .updatedTime(updatedTime).build();
    }
}
