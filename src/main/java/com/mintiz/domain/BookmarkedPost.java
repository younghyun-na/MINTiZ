package com.mintiz.domain;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Entity
public class BookmarkedPost {

    @Id@GeneratedValue
    @Column(name = "bookmark_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

}
