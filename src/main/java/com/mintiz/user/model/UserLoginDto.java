package com.mintiz.user.model;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Builder
@Data
public class UserLoginDto {

    @NotEmpty
    private String loginId;
    @NotEmpty
    private String password;

    public UserLoginDto(String loginId, String password){
        this.loginId = loginId;
        this.password = password;
    }

    public UserLoginDto() {
    }

}
