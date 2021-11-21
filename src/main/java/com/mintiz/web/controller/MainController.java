package com.mintiz.web.controller;

import com.mintiz.post.model.PostListResDto;
import com.mintiz.post.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/main")
@Slf4j
public class MainController {

    private final PostService postService;

    /**
     * 게시글 전체 조회
     **/
    @GetMapping("")
    public String showPostList(@RequestParam(required = false) String content, Model model){

        //내용으로 조회(제목이 X)
        if(content != null){
            List<PostListResDto> postListByName = postService.searchPostByContent(content);
            model.addAttribute("post", postListByName);
            return "post/Main";
        }
        List<PostListResDto> postList = postService.findPostAll();
        model.addAttribute("post", postList);
        return "post/Main";
    }

    /**
     * 게시글 태그로 조회
     * main/?tagName=
    **/
    @GetMapping("/select")
    public String showPostListByTag(@RequestParam String tagName, Model model){

        List<PostListResDto> postListByTag = postService.findPostAllByTag(tagName);
        model.addAttribute("post", postListByTag);
        return "post/Main";
    }
}
