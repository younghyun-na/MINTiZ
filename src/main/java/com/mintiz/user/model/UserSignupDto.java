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

    @Enumerated(EnumType.STRING)
    private Level level;

    @NotBlank (message = "이메일은 필수입니다.")
    @Email
    private String email;

    @NotBlank (message = "아이디는 필수입니다.")
    private String loginId;

    @NotBlank (message = "이름은 필수입니다.")
    private String name;

    @NotBlank (message = "비밀번호는 필수입니다.")
    private String password;
    private String check_password;

    private String profile;

    public UserSignupDto() {
    }

    public Level getLevel() {
        return level;
    }

<<<<<<< HEAD

    /*
    public void setLevel(String level){
        this.level = level;
    }
 */
=======
>>>>>>> 712a6f9f95b5eb2d2258dc1ff060c6047e396b52
}
