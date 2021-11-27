package com.mintiz.user.controller;

import com.mintiz.domain.Level;
import com.mintiz.user.model.UserSignupDto;
import com.mintiz.user.repository.UserRepository;
import com.mintiz.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;


@Controller
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;
    private UserRepository userRepository;

    /*
    // 회원가입 페이지 이동
    @GetMapping("/signup")
    public String signupForm(@RequestParam("level") String level, Model model){
        model.addAttribute("level", level);
        model.addAttribute("UserSignupDto", new UserSignupDto());
        return "user/Signup";
    }

    @PostMapping("/signup")
    public String signup(@Valid @ModelAttribute  UserSignupDto userSignupDto,
                         BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "user/Signup";
        }

        // userSignupDto.setLevel(level);
        userService.join(userSignupDto);
        return "user/Login";
    }
    */

    /*
    // 회원가입 페이지 이동
    @GetMapping("/signup")
    public String signupForm(Model model){
        model.addAttribute("UserSignupDto", new UserSignupDto());
        return "user/Signup";
    }
     */

    //회원가입 창 불러오기
    @GetMapping("/signup")
    public String signupForm(@RequestParam(value = "level") Integer level, Model model) {
        //log.info("level ={}",intToLevel);
        model.addAttribute("level",Level.valueOf(level));
        return "user/Signup";
    }
    //회원가입
    //@PostMapping("/signup")


    // 회원가입 기능
    @PostMapping("/signup")
    public String signup(@Valid @ModelAttribute UserSignupDto userSignupDto,
                         BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "user/Signup";
        }

        // userSignupDto.setLevel(level);
        userService.join(userSignupDto);
        return "user/Login";
    }


    /*
    // 회원가입 기능
    @PostMapping("/signup")
    public String signup(@RequestParam("level") String level,
                         @Valid UserSignupDto userSignupDto,
                         BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "user/Signup";
        }
        userSignupDto.setLevel(level);
        userService.join(userSignupDto);
        return "user/Login";
    }
    */


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