package com.mintiz.user.controller;

import com.mintiz.domain.Level;
import com.mintiz.user.model.UserSignupDto;
import com.mintiz.user.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@Controller
@RequestMapping("/user")
@AllArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    // 회원가입 창 불러오기
    @GetMapping("/signup")
    public String signupForm(@RequestParam(value = "level") Integer level, Model model) {
        model.addAttribute("level",Level.valueOf(level));
        model.addAttribute("userSignup", new UserSignupDto());
        return "user/Signup";
    }


    // 회원가입 기능
    @PostMapping("/signup")
    public String signup(
                         @Valid @ModelAttribute UserSignupDto userSignupDto) {

        userService.join(userSignupDto);
        return "redirect:/user/login";
    }


/*
    // 이메일 중복확인 기능
    @PostMapping("/emailCheck")
    public boolean emailCheck(@RequestBody UserSignupCheckDto checkDto){
        String email = checkDto.getEmail();
        return userService.emailCheck(email);
    }
*/



}