package com.mintiz.domain;

import lombok.*;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@RequiredArgsConstructor      //@NonNull,final 필드 값만 파라미터로 받는 생성자
public class TagPost {
    /**
     * 게시글 id를 통해 tag 저장
     * 게시글 id 1 태그 id 1 region = "서울"?
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name=  "tagPost_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "tag_id")
    @NonNull
    private Tag tag;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "post_id")
    @NonNull
    private Post post;

    public void updateTagPost(Tag tag){
        this.tag = tag;
    }

}
