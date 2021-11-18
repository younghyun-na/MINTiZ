package com.mintiz.web.controller;

import com.mintiz.domain.dto.UserSignupDto;
import com.mintiz.domain.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    // /user/signup?level=0
    // 회원가입 기능
    @PostMapping("/signup")
    public String signup(@Valid UserSignupDto userSignupDto, @RequestParam("level") String level) {

        return "redirect:/user/login";
    }
}
