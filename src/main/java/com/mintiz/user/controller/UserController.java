package com.mintiz.user.controller;

import com.mintiz.domain.Level;
import com.mintiz.user.model.UserSignupDto;
import com.mintiz.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.beans.PropertyEditorSupport;

@Controller
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    /*
    // WebDataBinder 등록
    @InitBinder
    public void initBinder(WebDataBinder dataBinder){
        dataBinder.setConversionService(this.conversionService);
    }
    */


    //회원가입 창 불러오기
    @GetMapping("/signup")
    public String userLevel(@RequestParam(value = "level") Integer level, Model model) {
        //log.info("level ={}",intToLevel);
        model.addAttribute("level",Level.valueOf(level));
        return "user/Signup";
    }
    //회원가입
    //@PostMapping("/signup")


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