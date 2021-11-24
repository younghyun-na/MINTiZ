package com.mintiz.user.controller;

import com.mintiz.domain.Level;
import com.mintiz.user.service.UserService;
import com.mintiz.user.model.UserSignupDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;


    // 회원가입 페이지
    @GetMapping("/signup")
    public String createForm(Model model){
        model.addAttribute("UserSignupDto", new UserSignupDto());
        return "user/Signup";
    }


    /*
    @InitBinder
    public void initBinder(WebDataBinder dataBinder){
        dataBinder.registerCustomEditor(Level.class, new LevelPropertyEditor());
    }
    */

    /*
    // 회원가입 + 레벨 값 저장
    @GetMapping("/signup")
    public String userLevel(@RequestParam("level") Level level, UserSignupDto userSignupDto) {
        userSignupDto.setLevel(level);
        userService.join(userSignupDto);
        return "user/Signup";
    }
    */



}
