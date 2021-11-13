package com.mintiz.domain;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)  //protected Tag(){}
public class Tag {
    /**
     * 일상 소통 / 맛집 추천(장소 입력) / 맛집 후기(장소 입력)
     */
    @Id
    @GeneratedValue
    @Column(name = "tag_id")
    private Long id;

    @Column(unique = true)
    private String tag_name;

    @OneToMany(mappedBy = "tag")
    @Builder.Default
    private List<TagPost> posts = new ArrayList<>();
}
