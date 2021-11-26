package com.mintiz.post.controller;

import com.mintiz.bookmark.BookmarkService;
import com.mintiz.domain.User;
import com.mintiz.post.model.PostListResDto;
import com.mintiz.post.service.PostService;
import com.mintiz.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MainController {

    private final PostService postService;
    private final long userId = 1L;
    private final UserService userService;
    private final BookmarkService bookmarkService;

    /**
     * 게시글 전체 조회
     **/
    @GetMapping(value = {"/main","/"})
    public String showPostList(@RequestParam(required = false) String content, Model model){

        //로그인 기능 아직 안해서 임시로 해둠
        User user = userService.findUser(userId);
        model.addAttribute("user",user);


        //내용으로 조회(제목이 X)
        if(content != null){
            List<PostListResDto> postListByName = postService.searchPostByContent(content,user);
            model.addAttribute("post", postListByName);
            return "post/Main";
        }


        List<PostListResDto> postList = postService.findPostAll(user);
        model.addAttribute("post", postList);
        return "post/Main";
    }

    /**
     * 게시글 태그로 조회
     * main/?tagName=
     **/

    @GetMapping("/main/select")
    public String showPostListByTag(@RequestParam String tagName, Model model){
        //로그인 기능 아직 안해서 임시로 해둠
        User user = userService.findUser(userId);
        model.addAttribute("user",user);

        List<PostListResDto> postListByTag = postService.findPostAllByTag(tagName,user);
        model.addAttribute("post", postListByTag);
        return "post/Main";
    }


}
