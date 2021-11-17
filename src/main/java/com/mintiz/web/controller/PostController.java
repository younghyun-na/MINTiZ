package com.mintiz.web.controller;

import com.mintiz.domain.Comment;
import com.mintiz.domain.Post;
import com.mintiz.domain.dto.*;
import com.mintiz.domain.service.CommentService;
import com.mintiz.domain.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/post")
@Slf4j
public class PostController {

    private final PostService postService;
    private final CommentService commentService;
    private final long userId = 1L;   //임시

    @GetMapping("/add")
    public String addPostView(Model model){
        model.addAttribute("postSaveDto", new PostSaveDto());
        return "post/Write";
    }

    @PostMapping("/add")
    public String addPost(@RequestParam("tagName") String tagName,
                          @RequestParam("location") String location,
                          @RequestParam("chooseImg") String img, @ModelAttribute PostSaveDto postSaveDto){
        postSaveDto.setLocation(location);
        Long postId = postService.savePost(userId, postSaveDto, tagName);

        log.info("img = {}", img);

        return "redirect:/post/all";     //메인화면으로 가야하는데..
    }

    //게시글 상세페이지 조회
    @GetMapping("/{postId}")
    public String getPost(@PathVariable("postId") long postId, Model model){
        PostResDto postResDto = postService.findPost(postId);
        model.addAttribute("PostResDto", postResDto);
        return "post/WritingDetails";
    }

    @GetMapping("/all")   //임시..
    public String getAllPost(){
        //List<Post> postAll = postService.findPostAll();
        return "post/Main";
    }

    //게시글 수정
    @PostMapping("/{postId}/update")
    public String updatePost(@PathVariable("postId") long postId,
                           @RequestParam("tagName") String updateTagName,
                           @ModelAttribute PostUpdateDto postUpdateDto){

        postService.updatePost(postId,postUpdateDto,updateTagName);
        return "redirect:/post/" + postId;
    }


    //게시글 삭제
    @PostMapping("/{postId}/delete")
    public String deletePost(@PathVariable("postId") long postId){
        postService.deletePost(postId);
        return "redirect:/post/all";
    }

    //댓글 등록
    @PostMapping("/{postId}/comments")
    public void addComment(@PathVariable("commentId") long commentId,
                           @ModelAttribute CommentSaveDto commentSaveDto){
        commentSaveDto.setUserId(1L);
        commentService.addComment(commentSaveDto);
    }

    //댓글 조회
    @GetMapping("/{postId}/comments")
    public void getComments(@PathVariable("postId") long postId){
        List<Comment> commentList = commentService.getCommentListByPost(postId);

    }

    //댓글 수정
    @PostMapping("/{postId}/comments/{commentId}/update")
    public void updateComment(@PathVariable("commentId") long commentId,
                              @PathVariable("postId") long postId){

    }

    //댓글 삭제
    @PostMapping("/{postId}/comments/{commentId}/delete")
    public void deleteComment(@PathVariable("commentId") long commentId,
                              @PathVariable("postId") long postId){

    }



}
