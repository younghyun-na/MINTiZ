package com.mintiz.user.controller;


import com.mintiz.domain.User;
import com.mintiz.user.SessionConst;
import com.mintiz.user.model.UserLoginDto;
import com.mintiz.user.repository.UserRepository;
import com.mintiz.user.service.LoginService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class LoginController {

    private final LoginService loginService;

    // 로그인 페이지
    @GetMapping("/login")
    public String loginForm(Model model){
        model.addAttribute("userLoginDto", new UserLoginDto());
        return "user/Login";
    }

    // 로그인 기능
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
            log.info("실패인가?");
            return "user/Login";
        }

        //로그인 성공 처리
        HttpSession session = request.getSession();
        //세션에 로그인 회원 정보 보관
        session.setAttribute(SessionConst.LOGIN_MEMBER, loginMember);

        // 로그인 후 메인페이지로 이동
        return "redirect:/main";
    }



    // 로그아웃
    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (session != null) {
            session.invalidate();
        }
        return "redirect:/user/Signup";
    }
}
