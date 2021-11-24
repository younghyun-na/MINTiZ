package com.mintiz.user.controller;

import com.mintiz.user.model.UserSignupDto;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

public class FindIdPwController {
    // 아이디 찾기 페이지 이동
    @GetMapping("/findId")
    public String findId(){
        return "user/Login";
    }

    // 아이디 찾기 기능


    // 비밀번호 찾기 페이지 이동
    @GetMapping("/findPW")
    public String findPw(){
        return "user/Login";
    }

    // 비밀번호 찾기 기능
}
