package com.mintiz.user.model;

import com.mintiz.domain.Level;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;


@Getter
@Setter
public class UserSignupDto {

    @Enumerated(EnumType.STRING)
    private Level level;

    @NotBlank (message = "이메일은 필수입니다.")
    @Email
    private String email;

    private String loginId;

    @NotBlank (message = "이름은 필수입니다.")
    private String name;

    @NotBlank (message = "비밀번호는 필수입니다.")
    private String password;

    private MultipartFile profile;

}
