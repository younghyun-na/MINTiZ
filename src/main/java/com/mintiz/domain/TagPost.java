package com.mintiz.domain;

import lombok.*;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@RequiredArgsConstructor      //@NonNull,final 필드 값만 파라미터로 받는 생성자
public class TagPost {

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
