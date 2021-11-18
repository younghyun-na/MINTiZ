package com.mintiz.domain.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserLoginDto {

    private String id;
    private String password;

    public UserLoginDto(String id, String password){
        this.id = id;
        this.password = password;
    }
}
