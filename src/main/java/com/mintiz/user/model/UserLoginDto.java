package com.mintiz.user.model;

import lombok.*;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginDto {

    @NotEmpty
    private String loginId;
    @NotEmpty
    private String password;

}
