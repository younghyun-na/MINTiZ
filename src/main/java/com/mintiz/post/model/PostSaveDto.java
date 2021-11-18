package com.mintiz.post.model;

import com.mintiz.domain.ImageFile;
import com.mintiz.domain.Post;
import com.mintiz.domain.User;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class PostSaveDto {

    /** 들어올때 ?tag = recommend
     * 아예 PostSave 랑 Tag 를 따로 저장
     * controller 에서 파라미터로 넘어온 태그 이름으로, post 글을 추가한 후, insert 글 pk를 구해서
     * post_id를 통해, TagPost 에 태그 추가.
     * 태그, 이미지 둘다 post save 할때 따로 저장해야해..이미지는 multipart로 처리해야하기 때문에**/

    private User user;

    @NotEmpty(message = "내용을 입력해주세요")
    private String content;

    private String location;

    private List<ImageFile> images = new ArrayList<>();

    public Post toEntity(){
        return Post.builder()
                .user(user)
                .content(content)
                .location(location)
                .build();
    }

}
