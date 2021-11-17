package com.mintiz.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)   //protected Tag(){}
public class ImageFile {
    /**
     * 게시글 작성 및 파일 업로드 동시 처리
     * 다중 파일 업로드
     * DB에는 파일 관련 정보만 저장하고 실제 파일은 서버 내의 지정 경로에 저장
     * → DB에 파일 자체 저장은 여러 가지 문제점으로 인하여 권장하지 않음
     */

    @Id
    @GeneratedValue
    @Column(name = "image_file_id")
    private Long id;

    private String originFileName;      //파일 원본명

    private String uploadFilePath;      //파일 저장 경로

    private Long fileSize;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="post_id")
    private Post post;

    public void setPost(Post post){
        this.post = post;

        //게시글에 현재 파일이 존재하지 않는다면, 파일 추가
        if(!post.getImages().contains(this)){
            post.getImages().add(this);
        }

    }
}
