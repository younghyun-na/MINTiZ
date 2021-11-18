package com.mintiz.service;

import com.mintiz.domain.Tag;
import com.mintiz.domain.User;
import com.mintiz.post.model.CommentResDto;
import com.mintiz.post.model.CommentSaveDto;
import com.mintiz.post.model.CommentUpdateReqDto;
import com.mintiz.post.model.PostSaveDto;
import com.mintiz.post.repository.CommentRepository;
import com.mintiz.post.service.CommentService;
import com.mintiz.post.service.PostService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
public class CommentServiceTest {

    @Autowired CommentService commentService;
    @Autowired CommentRepository commentRepository;
    @Autowired PostService postService;
    @Autowired EntityManager em;

    @Test
    @Rollback(value = false)
    public void addComment(){
        //given
        createTag();
        User user = createUser();
        PostSaveDto post = createPost("콘텐트", "서울");
        Long postId = postService.savePost(user.getId(), post, "후기");
        CommentSaveDto comment = new CommentSaveDto(user.getId(), postId, "댓글 1");
        //when
        long commentId = commentService.addComment(comment);

        //then
        Assertions.assertEquals("댓글 1", commentRepository.findById(commentId).get().getContent());
    }

    @Test
    @Rollback(value = false)
    public void updateComment(){
        //given
        createTag();
        User user = createUser();
        PostSaveDto post = createPost("콘텐트", "서울");
        Long postId = postService.savePost(user.getId(), post, "후기");
        CommentSaveDto comment = new CommentSaveDto(user.getId(), postId, "댓글 1");
        long commentId = commentService.addComment(comment);

        //when
        CommentUpdateReqDto dto = CommentUpdateReqDto.builder()
                .commentId(commentId)
                .updateContent("댓글 1 수정본")
                .build();
        commentService.updateComment(dto);

        //then
        Assertions.assertEquals("댓글 1 수정본", commentRepository.findById(commentId).get().getContent());

    }

    @Test
    @Rollback(value = false)
    public void showAllCommentByPostId(){
        //given
        createTag();
        User user = createUser();
        PostSaveDto post = createPost("콘텐트", "서울");
        Long postId = postService.savePost(user.getId(), post, "후기");
        CommentSaveDto comment = new CommentSaveDto(user.getId(), postId, "댓글 1");
        long commentId = commentService.addComment(comment);

        //when
        List<CommentResDto> commentList = commentService.getCommentListByPost(postId);

        //then
        Assertions.assertEquals(1, commentList.size());

    }

    @Test
    public void deleteComment(){
        //given
        createTag();
        User user = createUser();
        PostSaveDto post = createPost("콘텐트", "서울");
        Long postId = postService.savePost(user.getId(), post, "후기");
        CommentSaveDto comment = new CommentSaveDto(user.getId(), postId, "댓글 1");
        long commentId = commentService.addComment(comment);

        //when
        commentService.deleteComment(commentId);

        //then
        IllegalStateException ex = assertThrows(IllegalStateException.class, ()->{
            commentService.deleteComment(commentId);
        });
        Assertions.assertEquals("삭제하려는 댓글이 존재하지 않습니다.", ex.getMessage());
    }


    private PostSaveDto createPost(String content, String location) {
        PostSaveDto postSaveDto = new PostSaveDto();
        postSaveDto.setContent(content);
        postSaveDto.setLocation(location);
        return postSaveDto;
    }

    private User createUser() {
        User userA = new User();
        userA.setName("세미");
        userA.setEmail("세미 이메일");
        userA.setPassword("1234");
        em.persist(userA);
        return userA;
    }

    private void createTag(){
        Tag tag1 = Tag.builder().tag_name("후기").build();
        Tag tag2 = Tag.builder().tag_name("일상").build();
        //tagRepository.save(tag1);
        //tagRepository.save(tag2);
        em.persist(tag1);
        em.persist(tag2);
    }

}
