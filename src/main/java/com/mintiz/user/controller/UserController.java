package com.mintiz.user.controller;

import com.mintiz.user.model.UserLoginDto;
import com.mintiz.user.service.UserService;
import com.mintiz.user.model.UserSignupDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    // 회원가입 페이지 이동
    @GetMapping("/signup")
    public String signup(){
        return "login";
    }

    // /user/signup?level=0
    // 회원가입 기능
    @PostMapping("/signup")
    public String signup(@Valid UserSignupDto userSignupDto, @RequestParam("level") String level) {

        return "redirect:/user/login";
    }
}
