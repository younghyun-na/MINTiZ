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



@Controller
@RequestMapping("/user")
public class FindIdPwController {

    private UserService userService;
    private LoginService loginService;


    // 아이디 찾기 페이지 이동
    @GetMapping("/findId")
    public String findIdForm(){
        return "user/FindId";
    }



    // 아이디 찾기 기능
    @PostMapping("/findId")
    public String findId(@RequestParam("email") String email, Model model) {
        User member = userService.findUserByEmail(email);  // 이메일로 멤버 찾기

        if(member == null) {     // 존재하지 않는 회원
            model.addAttribute("check", 1);
        } else {                 // 존재하는 회원
            model.addAttribute("check", 0);
            model.addAttribute("id", member.getId());   // 아이디 받기
        }

        return "user/FindId";
    }

    // 비밀번호 찾기 페이지 이동
    @GetMapping("/findPW")
    public String findPw(){
        return "user/FindPW";
    }

    // 비밀번호 찾기 기능
    @PostMapping("/findPW")
    public String findPW(@RequestParam("email") String email,
                         @RequestParam("loginId") String loginId,
                         Model model) {

        User member = loginService.login(email,loginId);   // 이메일, 아이디로 멤버 찾기

        if(member == null) {   // 존재하지 않는 회원
            model.addAttribute("check", 1);
        } else {               // 존재하는 회원
            model.addAttribute("check", 0);
            model.addAttribute("password", member.getPassword());  // 비밀번호 받기
        }

        return "user/FindPW";
    }
}
