package com.mintiz.user.controller;

import com.mintiz.domain.User;
import com.mintiz.user.model.UserLoginDto;
import com.mintiz.user.service.LoginService;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
public class LoginController {

    private LoginService loginService;

    // 로그인 페이지 이동
    @GetMapping("/login")
    public String login(@ModelAttribute UserLoginDto userLoginDto){
        return "login";
    }

    // 로그인 기능
    @PostMapping("/login")
    public String login(@Valid @ModelAttribute UserLoginDto userLoginDto, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return "login";
        }

        User loginUser = loginService.login(userLoginDto.getId(), userLoginDto.getPassword());

        if (loginUser == null){
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다");
            return "login";
        }

        // 로그인 성공

        return "redirect:/main";
    }
}
