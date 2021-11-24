package com.mintiz.user.controller;

import com.mintiz.domain.Level;
import com.mintiz.user.model.UserSignupDto;
import com.mintiz.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    ConversionService conversionService;


    /*
    // 회원가입 페이지
    @GetMapping("/signup")
    public String createForm(Model model){
        model.addAttribute("UserSignupDto", new UserSignupDto());
        return "user/Signup";
    }
    */


    // WebDataBinder 등록
    @InitBinder
    public void initBinder(WebDataBinder dataBinder){
        dataBinder.setConversionService(this.conversionService);
    }


    // 회원가입 + 레벨 값 저장
    @GetMapping("/signup")
    public String goSignup(@RequestParam("level") Level level, UserSignupDto userSignupDto) {
        userSignupDto.setLevel(level);
        return "user/Signup";
    }

    // 회원가입
    @PostMapping("/signup")
    public String Signup(@Valid @ModelAttribute UserSignupDto userSignupDto, BindingResult bindingResult){
        if (bindingResult.hasErrors() ){
            return "user/Signup";
        }
        userService.join(userSignupDto);
        return "user/Login";

    }



    /*
    // 아이디 중복확인 기능
    @PostMapping("/idCheck")
    @ResponseBody
    public int idCheck(@RequestParam("id") Long id){
        int cnt = userService.idCheck(id);
        return cnt;
    }
    */

    /*
    // 로그인 화면으로 이동
    @GetMapping("/login")
    public String login(){
        return "login";
    }
    */


}