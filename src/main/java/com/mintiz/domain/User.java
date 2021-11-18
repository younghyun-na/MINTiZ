package com.mintiz.domain;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {
    
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    private String name;

    @Enumerated(EnumType.STRING)
    private Level level;

    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private List<Post> posts = new ArrayList<>();

    private String profile;

    @Builder
    private User(String email, String name, Long id, String password, Level level) {
        this.email = email;
        this.name = name;
        this.id = id;
        this.password = password;
    }

}
