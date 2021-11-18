package com.mintiz.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
public class MypageController {

    @GetMapping("/mypage")
    public String mypage(Model model){
        return "MyPage";
    }

    @GetMapping("/writing")
    public String writing(Model model){
        return "WritingList";
    }

    @GetMapping("/bookmark")
    public String bookmark(Model model){
        return "BookMark";
    }

}
