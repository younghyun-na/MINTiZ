package com.mintiz.user.controller;


import com.mintiz.domain.User;
import com.mintiz.user.SessionConst;
import com.mintiz.user.model.UserLoginDto;
import com.mintiz.user.model.UserSignupDto;
import com.mintiz.user.repository.UserRepository;
import com.mintiz.user.service.LoginService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequestMapping("/user")
public class LoginController {

    private LoginService loginService;
    private UserRepository userRepository;


    // 로그인 페이지 이동
    @GetMapping("/login")
    public String login(@ModelAttribute UserLoginDto userLoginDto){
        return "user/Login";
    }



    @PostMapping("/login")
    public String login(@Valid @ModelAttribute UserLoginDto userLoginDto,
                          BindingResult bindingResult,
                          HttpServletRequest request) {

        if (bindingResult.hasErrors()) {
            return "user/Login";
        }

        User loginMember = loginService.login(userLoginDto.getLoginId(), userLoginDto.getPassword());

        if (loginMember == null) {
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
            return "user/Login";
        }

        //로그인 성공
        HttpSession session = request.getSession();
        //세션에 로그인 회원 정보 보관
        session.setAttribute(SessionConst.LOGIN_MEMBER, loginMember);

        // 로그인 후 메인페이지로 이동
        return "post/Main";

    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/user/Signup";
    }
}
