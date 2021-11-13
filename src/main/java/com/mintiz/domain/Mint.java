package com.mintiz.domain;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
public class Mint {

    @Id @GeneratedValue
    @Column(name = "mint_id")
    private Long id;

    private LocalDateTime mintDay;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="user_id")
    private User user;

}
