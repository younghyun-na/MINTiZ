package com.mintiz.user.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserUpdateDto {

    private String name;
    private String password;

    public UserUpdateDto(String name, String password){
        this.name = name;
        this.password = password;
    }
}
