package com.mintiz.user.model;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;

@Data
@Builder
public class UserFindIdDto {

    @Email
    private String email;

}
