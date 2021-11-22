package com.mintiz.post.model;

import com.mintiz.domain.ImageFile;
import com.mintiz.domain.Post;
import com.mintiz.domain.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class PostSaveDto {
    private User user;

    @NotEmpty(message = "내용을 입력해주세요")
    private String content;

    private String tagName;

    private String location;

    private List<MultipartFile> images;

    public Post toEntity(){
        return Post.builder()
                .user(user)
                .content(content)
                .location(location)
                .build();
    }

}
