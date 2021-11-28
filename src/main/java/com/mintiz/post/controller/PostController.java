package com.mintiz.post.controller;

import com.mintiz.domain.User;
import com.mintiz.post.model.*;
import com.mintiz.post.service.CommentService;
import com.mintiz.post.service.PostService;
import com.mintiz.user.service.LoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;
import com.mintiz.file.FileStore;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/post")
@Slf4j
public class PostController {

    private final PostService postService;
    private final CommentService commentService;
    private final LoginService loginService;
    private final FileStore fileStore;
    private final long userId = 1L;

    @GetMapping("/add")
    public String addPostView(Model model){
        model.addAttribute("saveForm", new PostSaveDto());
        return "post/Write";
    }

    @PostMapping("/add")
    public String addPost(@ModelAttribute PostSaveDto postSaveDto, HttpServletRequest request){
        User loginUser = loginService.getLoginUser(request);

        Long postId = postService.savePost(loginUser.getId(), postSaveDto);
        return "redirect:/post/" + postId;
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

    //사진 불러오기
    @ResponseBody
    @GetMapping("/images/{filename}")
    public Resource downloadImage(@PathVariable("filename") String filename) throws MalformedURLException {
        //경로에 있는 파일에 직접 접근해서 stream 으로 반환해옴
        return new UrlResource("file:" + fileStore.getFullPath(filename));
    }

    @GetMapping("/{postId}/update")
    public String updatePostForm(@PathVariable("postId") long postId, Model model){
        PostResDto post = postService.findPost(postId);
        PostUpdateDto postUpdate = PostUpdateDto.builder()
                .content(post.getContent())
                .location(post.getLocation())
                .tagName(post.getTagName())
                .images(post.getImageFiles()).build();

        model.addAttribute("postId", postId);
        model.addAttribute("updateForm", postUpdate);
        return "post/Modify";
    }

    //게시글 수정
    @PostMapping("/{postId}/update")
    public String updatePost(@PathVariable("postId") long postId, PostUpdateDto postUpdateDto){
        postService.updatePost(postId,postUpdateDto);
        return "redirect:/post/" + postId;
    }

    //게시글 삭제
    @PostMapping("/{postId}/delete")
    public String deletePost(@PathVariable("postId") long postId){
        postService.deletePost(postId);
        return "redirect:/main";
    }

    //댓글 등록
    @ResponseBody
    @PostMapping("/{postId}/comments")
    public long addComment(@PathVariable("postId") long postId,
                           @RequestBody CommentSaveDto commentSaveDto, HttpServletRequest request){
        User loginUser = loginService.getLoginUser(request);
        return commentService.addComment(new CommentSaveDto(loginUser.getId(), postId, commentSaveDto.getContent()));
        //return "redirect:/post/"+ postId;
    }

    //댓글 수정
    @PostMapping("/{postId}/comments/{commentId}/update")
    public String updateComment(@PathVariable("commentId") long commentId, @PathVariable("postId") long postId,
                                CommentUpdateReqDto commentUpdateReqDto){
        commentUpdateReqDto.setCommentId(commentId);
        commentService.updateComment(commentUpdateReqDto);
        return "redirect:/post/" + postId;
    }

    //댓글 삭제
    @PostMapping("/{postId}/comments/{commentId}/delete")
    public String deleteComment(@PathVariable("commentId") long commentId, @PathVariable("postId") long postId){
        commentService.deleteComment(commentId);
        return "redirect:/post/" + postId;
    }

}
