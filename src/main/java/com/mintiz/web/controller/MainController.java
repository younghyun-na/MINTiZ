package com.mintiz.web.controller;

import com.mintiz.post.model.PostListResDto;
import com.mintiz.post.service.PostService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/main")
public class MainController {

    private final PostService postService;

    /*



     * 게시글 전체 조회

    @GetMapping("")
    public void showPostList(){
        List<PostListResDto> postList = postService.findPostAll();

    }

    /**
     * 게시글 제목으로 조회

    @GetMapping("")
    public void showPostListByName(){

    }

    /**
     * 게시글 태그로 조회

    @GetMapping("")
    public void showPostListByTag(@RequestParam(required = false) String tagName){

    }
    */
}
