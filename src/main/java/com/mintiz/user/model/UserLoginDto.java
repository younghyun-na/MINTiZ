package com.mintiz.user.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserLoginDto {

    private Long id;
    private String password;

    public UserLoginDto(Long id, String password){
        this.id = id;
        this.password = password;
    }
}
