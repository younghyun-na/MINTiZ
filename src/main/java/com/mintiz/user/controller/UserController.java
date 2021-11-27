package com.mintiz.user.controller;

import com.mintiz.domain.Level;
import com.mintiz.post.model.PostSaveDto;
import com.mintiz.user.model.UserProfileDto;
import com.mintiz.user.model.UserSignupDto;
import com.mintiz.user.repository.UserRepository;
import com.mintiz.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;


@Controller
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;
    private UserRepository userRepository;


    // 회원가입 창 불러오기
    @GetMapping("/signup")
    public String signupForm(@RequestParam(value = "level") Integer level, Model model) {
        model.addAttribute("level",Level.valueOf(level));
        return "user/Signup";
    }


    // 회원가입 기능
    @PostMapping("/signup")
    public String signup(@RequestParam(value = "level") Integer level,
                         @RequestParam("files") MultipartFile file,
                         @Valid @ModelAttribute UserSignupDto userSignupDto,
                         BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "user/Signup";
        }

        userSignupDto.setLevel(Level.valueOf(level));
        userService.join(userSignupDto);
        return "redirect:/user/Login";
    }


    //
    /*
    // 아이디 중복확인 기능
    @PostMapping("/idCheck")
    @ResponseBody
    public int idCheck(@RequestParam("loginId") String loginId){
        int cnt = userService.idCheck(loginId);
        return cnt;
    }
    */



}