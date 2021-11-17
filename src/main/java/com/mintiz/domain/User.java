package com.mintiz.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(nullable = false)
    private String email;

    private String password;
    private String name;

    @Enumerated(EnumType.STRING)
    private Level level;

    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private List<Post> posts = new ArrayList<>();

    private String profile;
}
