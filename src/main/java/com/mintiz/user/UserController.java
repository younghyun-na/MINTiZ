package com.mintiz.user;

import com.mintiz.domain.Level;
import com.mintiz.user.model.UserSignupDto;
import com.mintiz.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    // /user/signup?level=0
    // 회원가입 기능
    @PostMapping("/signup")
    public String signup(@Valid UserSignupDto userSignupDto,
                         @RequestParam("level") Level level) {

        userSignupDto.setLevel(level);
        userService.join(userSignupDto);
        return "redirect:/user/login";
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

    // 로그인 화면으로 이동
    @GetMapping("/login")
    public String login(){
        return "login";
    }

}
