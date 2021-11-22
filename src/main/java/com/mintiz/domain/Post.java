package com.mintiz.domain;

import com.mintiz.post.model.PostUpdateDto;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)   //protected Tag(){}
//@Builder(builderMethodName = "PostBuilder")
public class Post{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name="user_id")
    private User user;

    @Column(columnDefinition = "TEXT")
    private String content;

    private String location;

    @OneToMany(mappedBy = "post", orphanRemoval = true, cascade = CascadeType.ALL)   //자식 entity 고아(NULL) 객체 삭제
    @Builder.Default
    private List<ImageFile> images = new ArrayList<>();

    //post 삭제되면 tagPost 삭제, 업데이트되면 tagPost 업데이트
    @OneToMany(mappedBy = "post", orphanRemoval = true, cascade = CascadeType.ALL)
    @Builder.Default
    private List<TagPost> tagPosts = new ArrayList<>();

    //post 삭제되면 연관되어 있는 댓글들도 삭제
    @OneToMany(mappedBy = "post", orphanRemoval = true, cascade = CascadeType.ALL)
    @Builder.Default
    private List<Comment> comments = new ArrayList<>();

    /**
     * 생성 일시:INSERT/UPDATE 쿼리 발생 시 저장
     */
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    /**
     * 수정 일시: UPDATE 쿼리 발생 시 저장
     */
    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    @Builder.Default
    private LocalDateTime updatedTime = LocalDateTime.now();

    public void updatePost(PostUpdateDto postUpdateDto){
        this.content = postUpdateDto.getContent();
        this.images = postUpdateDto.getImages();
        this.location = postUpdateDto.getLocation();
    }

    //==비즈니스 로직==//
    /**Post 에서 파일 추가 처리**/
    public void addImageFile(ImageFile imageFile){
        this.images.add(imageFile);  //list 에 추가

        //게시글에 파일이 저장되어 있지 않은 경우
        if(imageFile.getPost() != this){
            imageFile.setPost(this);
        }
    }
}

