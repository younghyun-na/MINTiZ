package com.mintiz.user.controller;

import com.mintiz.domain.User;
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
import javax.validation.Valid;

@Controller
@RequestMapping("/user")
public class LoginController {

    private LoginService loginService;
    private UserRepository userRepository;


    @GetMapping("/login")
    public String login(Model model){
        model.addAttribute("UserLoginDto", new UserLoginDto());
        return "user/Login";
    }

    /*
    // 로그인 페이지 이동
    @GetMapping("/login")
    public String login(@ModelAttribute UserLoginDto userLoginDto){
        return "user/Login";
    }
    */

    /*
    // 로그인 기능
    @PostMapping("/login")
    public String login(@Valid @ModelAttribute UserLoginDto userLoginDto,
                        BindingResult bindingResult, HttpServletResponse response){
        if (bindingResult.hasErrors()){
            return "user/Login";
        }

        User loginUser = loginService.login(userLoginDto.getId(), userLoginDto.getPassword());

        if (loginUser == null){
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다");
            return "user/Login";
        }

        // 로그인 성공 -> 쿠키 생성
        Cookie idCookie = new Cookie("id", String.valueOf(loginUser.getId()));
        response.addCookie(idCookie);
        return "redirect:/main";
    }

    /*
    // 로그인 후 메인페이지로 이동
    @GetMapping("/main")
    public String afterLogin(@CookieValue(name="id",required = false) Long id, Model model){
        if (id == null){
            return "main";
        }

        // 로그인
        //User loginUser = userRepository.findById(id);

    }
    */
}
