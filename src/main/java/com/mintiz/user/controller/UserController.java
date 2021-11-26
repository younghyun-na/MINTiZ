package com.mintiz.user.controller;

import com.mintiz.domain.Level;
import com.mintiz.user.model.UserSignupDto;
import com.mintiz.user.repository.UserRepository;
import com.mintiz.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.beans.PropertyEditorSupport;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    private UserRepository userRepository;


<<<<<<< HEAD
    @Autowired
    private ConversionService conversionService;




    // 회원가입 페이지 이동
    @GetMapping("/signup")
    public String goSignup(Model model){
        model.addAttribute("UserSignupDto", new UserSignupDto());
        return "user/Signup";
    }

    /*
    // WebDataBinder 등록
    @InitBinder
    public void initBinder(WebDataBinder dataBinder){
        dataBinder.setConversionService(this.conversionService);
    }
    */

    // 회원가입 기능
    @PostMapping("/signup")
    public String signup(UserSignupDto userSignupDto,
                         BindingResult bindingResult,
                         @RequestParam(value ="level", defaultValue = "0") Level level) {

        if (bindingResult.hasErrors()){
            return "user/Signup";
        }

        userSignupDto.setLevel(level);
        userService.join(userSignupDto);
        return "user/Login";
    }


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