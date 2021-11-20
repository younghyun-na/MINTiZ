package com.mintiz.user.model;

import com.mintiz.domain.Level;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;


@Builder
@Data
@AllArgsConstructor
public class UserSignupDto {

    @NotBlank (message = "이메일은 필수입니다.")
    @Email
    private String email;

    @NotBlank (message = "아이디는 필수입니다.")
    private String id;

    @NotBlank (message = "이름은 필수입니다.")
    private String name;

    @NotBlank (message = "비밀번호는 필수입니다.")
    private String password;

    @Enumerated(EnumType.STRING)
    private Level level;

    public UserSignupDto() {
    }
}