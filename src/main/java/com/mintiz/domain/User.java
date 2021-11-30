package com.mintiz.domain;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;


@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {
    
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;  // DB 관리용 아이디

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    private String name;

    @Enumerated(EnumType.STRING)
    private Level level;

    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private List<Post> posts = new ArrayList<>();

    @OneToOne (cascade = CascadeType.ALL)
    private ImageFile profile;

    private String loginId;

    @Builder
    private User(String email, String name, String password, Level level, ImageFile profile, String loginId) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.level = level;
        this.profile = profile;
        this.loginId = loginId;
    }

}
