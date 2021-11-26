package com.mintiz.user.model;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Builder
@Data
public class UserLoginDto {

    private String loginId;
    private String password;

    public UserLoginDto(String loginId, String password){
        this.loginId = loginId;
        this.password = password;
    }

    public String getLoginId() {
        return loginId;
    }

    public String getPassword() {
        return password;
    }
}
