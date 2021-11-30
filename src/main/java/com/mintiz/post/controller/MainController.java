package com.mintiz.post.controller;

import ch.qos.logback.core.encoder.EchoEncoder;
import com.mintiz.bookmark.BookmarkService;
import com.mintiz.domain.User;
import com.mintiz.post.model.PostListResDto;
import com.mintiz.post.service.PostService;
import com.mintiz.user.repository.UserRepository;
import com.mintiz.user.service.LoginService;
import com.mintiz.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.pool.TypePool;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MainController {

    private final PostService postService;
    private final UserService userService;
    private final UserRepository userRepository;
    private final LoginService loginService;
    private final BookmarkService bookmarkService;

    /**
     * 게시글 전체 조회
     **/
    @GetMapping(value = {"/main","/"})
    public String showPostList(@RequestParam(required = false) String content, Model model, HttpServletRequest request){

        User loginUser = loginService.getLoginUser(request);
        if (loginUser == null){
            return "redirect:/user/login";
        }
        User user = userService.findUser(loginUser.getId());
        model.addAttribute("user", user);

        //내용으로 조회
        if(content != null){
            List<PostListResDto> postListByName = postService.searchPostByContent(content,user);
            if(postListByName.isEmpty()){
                nonePostHandler(model);
            }
            model.addAttribute("post", postListByName);
            return "post/Main";
        }

        List<PostListResDto> postList = postService.findPostAll(user);
        if(postList.isEmpty()){            //게시글 없을때 처리
            nonePostHandler(model);
        }
        model.addAttribute("post", postList);
        return "post/Main";

    }

    /**
     * 게시글 태그로 조회
     * main/?tagName=
     **/

    @GetMapping("/main/select")
    public String showPostListByTag(@RequestParam String tagName, Model model, HttpServletRequest request){
        User loginUser = loginService.getLoginUser(request);

        User user = userService.findUser(loginUser.getId());
        model.addAttribute("user",user);

        List<PostListResDto> postListByTag = postService.findPostAllByTag(tagName,user);
        if(postListByTag.isEmpty()){
            nonePostHandler(model);
        }
        model.addAttribute("post", postListByTag);
        return "post/Main";
    }

    private String nonePostHandler(Model model){
        model.addAttribute("post", new ArrayList<PostListResDto>());
        model.addAttribute("postSize", 1);
        return "post/Main";
    }
}
