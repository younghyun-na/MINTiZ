package com.mintiz.user.controller;

import com.mintiz.domain.Level;
import com.mintiz.user.service.UserService;
import com.mintiz.user.model.UserSignupDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;


    // 회원가입 페이지 이동
    @GetMapping("/signup")
    public String createForm(Model model){
        model.addAttribute("UserSignupDto", new UserSignupDto());
        return "user/Signup";
    }


    // /user/signup?level=0
    // 회원가입 기능
    @PostMapping("/signup")
    public String signup(@Valid UserSignupDto userSignupDto,
                         @RequestParam("level") Level level, BindingResult result) {

        if (result.hasErrors()) {
            return "user/Signup";
        }

        userSignupDto.setLevel(level.반민초단);
        userService.join(userSignupDto);
        return "redirect:/user/Login";
    }

}
