package com.mintiz.user.controller;

import com.mintiz.domain.Level;
import com.mintiz.user.model.UserSignupDto;
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


    /*
    // 회원가입 페이지
    @GetMapping("/signup")
    public String createForm(Model model){
        model.addAttribute("UserSignupDto", new UserSignupDto());
        return "user/Signup";
    }
*/

    /*
    // 레벨 enum값
    @InitBinder
    public void initBinder(WebDataBinder dataBinder){
        dataBinder.registerCustomEditor(Level.class, new LevelPropertyEditor());
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