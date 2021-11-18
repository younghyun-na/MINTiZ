package com.mintiz.post.controller;

import com.mintiz.post.model.*;
import com.mintiz.post.service.CommentService;
import com.mintiz.post.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
        model.addAttribute("saveForm", new PostSaveDto());
        return "post/Write";
    }

    @PostMapping("/add")
    public String addPost(@RequestParam("tagName") String tagName,
                          @RequestParam("location") String location, @ModelAttribute PostSaveDto postSaveDto){
        postSaveDto.setLocation(location);
        Long postId = postService.savePost(userId, postSaveDto, tagName);

        return "redirect:/post/all";
    }

    @GetMapping("/all")   //임시..
    public String getAllPost(){
        //List<Post> postAll = postService.findPostAll();
        return "post/Main";
    }

    //게시글 상세페이지 조회 + 댓글까지 함께 조회
    @GetMapping("/{postId}")
    public String getPost(@PathVariable("postId") long postId, Model model){
        PostResDto postResDto = postService.findPost(postId);
        List<CommentResDto> commentList = commentService.getCommentListByPost(postId);

        model.addAttribute("commentSize", commentList.size());
        model.addAttribute("PostResDto", postResDto);
        model.addAttribute("commentList", commentList);

        return "post/WritingDetails";
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
    public String addComment(@PathVariable("postId") long postId, @RequestParam("comment-input") String comment){

        commentService.addComment(new CommentSaveDto(1L, postId, comment));  //이게 실행이 안됌
        return "redirect:/post/" + postId;
    }

    //댓글 수정
    @PostMapping("/{postId}/comments/{commentId}/update")
    public void updateComment(@PathVariable("commentId") long commentId, @PathVariable("postId") long postId){

    }

    //댓글 삭제
    @PostMapping("/{postId}/comments/{commentId}/delete")
    public void deleteComment(@PathVariable("commentId") long commentId, @PathVariable("postId") long postId){
        commentService.deleteComment(commentId);
    }



}
