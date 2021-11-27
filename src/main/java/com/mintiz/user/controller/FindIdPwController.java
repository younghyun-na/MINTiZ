package com.mintiz.user.controller;

import com.mintiz.domain.User;
import com.mintiz.user.service.LoginService;
import com.mintiz.user.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;


@Controller
@RequestMapping("/user")
public class FindIdPwController {


    private UserService userService;

    // 아이디 찾기 페이지 이동
    @GetMapping("/findId")
    public String findIdForm(){
        return "user/findId";
    }


    /*
    // 아이디 찾기 기능
    @PostMapping
    public String findId(@RequestParam("email") String email, Model model) {
        User member = userService.findUserByEmail(email);

        if(member == null) {
            model.addAttribute("check", 1);
        } else {
            model.addAttribute("check", 0);
            model.addAttribute("id", user.getId());
        }

        return "member/findId";
    }

    // 비밀번호 찾기 페이지 이동
    @GetMapping("/findPW")
    public String findPw(){
        return "user/findPW";
    }

    // 비밀번호 찾기 기능
    */
}
