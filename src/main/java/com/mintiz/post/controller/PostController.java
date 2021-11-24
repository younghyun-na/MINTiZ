package com.mintiz.post.controller;

import com.mintiz.post.model.*;
import com.mintiz.post.service.CommentService;
import com.mintiz.post.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;
import com.mintiz.domain.FileStore;
import java.nio.file.Files;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/post")
@Slf4j
public class PostController {

    private final PostService postService;
    private final CommentService commentService;
    private final FileStore fileStore;
    private final long userId = 1L;   //임시

    @GetMapping("/add")
    public String addPostView(Model model){
        model.addAttribute("saveForm", new PostSaveDto());
        return "post/Write";
    }

    /**
     * 그냥 getImage : img = [org.springframework.web.multipart.support.StandardMultipartHttpServletRequest$StandardMultipartFile@1d90a735]
     * StringUtils.cleanPath(postSaveDto.getImages().get(0).getOriginalFilename()) : originImg = '.png
     */
    @PostMapping("/add")
    public String addPost(@ModelAttribute PostSaveDto postSaveDto){
        Long postId = postService.savePost(userId, postSaveDto);

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

    //사진 불러오기:toDo 아예 이 경로로 안가는데..?왜 ㅠㅠㅠㅠ시바
    @ResponseBody
    @GetMapping("/images/{filename}")
    public Resource downloadImage(@PathVariable("filename") String filename) throws MalformedURLException {
        //경로에 있는 파일에 직접 접근해서 stream 으로 반환해옴
        log.info("filename = {}", filename);
        return new UrlResource("file:" + fileStore.getFullPath(filename));
    }

    /*
    @ResponseBody
    @CrossOrigin
    @GetMapping(value = "/images/{filename}", produces = {MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE})
    public byte[] downloadImage(@PathVariable String filename) throws IOException {
        //경로에 있는 파일에 직접 접근해서 stream 으로 반환해옴
        String absolutePath = new File("").getAbsolutePath(); //절대 경로

        InputStream imageStream = new FileInputStream(absolutePath + filename);
        byte[] imageByteArray = IOUtils.toByteArray(imageStream);
        imageStream.close();

        return imageByteArray;
        //return new UrlResource("file:" + absolutePath + filename);
    }
    */

    //기존의 이미지를 들고가야지..
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
    @PostMapping("/{postId}/comments")
    public String addComment(@PathVariable("postId") long postId, @RequestParam("comment-input") String comment){

        commentService.addComment(new CommentSaveDto(1L, postId, comment));
        return "redirect:/post/" + postId;
    }

    /*
    //댓글 수정창..
    @GetMapping("/{postId}/comments/{commentId}/update")
    public String updateCommentForm(@PathVariable("commentId") long commentId, @PathVariable("postId") long postId){
        return "post/"
    }
     */

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
