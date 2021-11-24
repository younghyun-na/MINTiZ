package com.mintiz.user.model;

public class UserLoginDto {
    private Long id;
    private String password;

    public UserLoginDto(Long id, String password){
        this.id = id;
        this.password = password;
    }
}
