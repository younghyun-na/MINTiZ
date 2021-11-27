package com.mintiz.user.model;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@Builder
public class UserFindPwDto {

    @NotBlank(message = "이메일은 필수입니다.")
    @Email
    private String email;

    @NotBlank (message = "아이디는 필수입니다.")
    private String loginId;
}
