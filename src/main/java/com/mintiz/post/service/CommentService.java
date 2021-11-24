package com.mintiz.post.service;

import com.mintiz.domain.Comment;
import com.mintiz.domain.Post;
import com.mintiz.domain.User;
import com.mintiz.post.model.CommentResDto;
import com.mintiz.post.model.CommentSaveDto;
import com.mintiz.post.model.CommentUpdateReqDto;
import com.mintiz.post.repository.CommentRepository;
import com.mintiz.post.repository.PostRepository;
import com.mintiz.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    /**
     * 댓글 추가
     * */
    @Transactional
    public long addComment(CommentSaveDto commentSaveDto){
        //jwt => userId
        User user = userRepository.getById(commentSaveDto.getUserId());
        Post post = postRepository.findById(commentSaveDto.getPostId()).get();

        Comment comment = Comment.builder()
                .content(commentSaveDto.getContent())
                .user(user)
                .post(post).build();

        commentRepository.save(comment);
        return comment.getId();
    }

    /**
     * 댓글 수정
     */
    @Transactional
    public void updateComment(CommentUpdateReqDto commentUpdateReqDto){
        //방식 1
        Comment comment = commentRepository.findById(commentUpdateReqDto.getCommentId()).orElseThrow(
                () -> new IllegalArgumentException("수정하려는 댓글이 존재하지 않습니다.")
        );
        comment.updateComment(commentUpdateReqDto);

        //방식 2
        //commentRepository.updateComment(commentUpdateReqDto.getUpdateContent(), commentUpdateReqDto.getCommentId());
    }


    /**
     * 댓글 리스트 조회
     */
    public List<CommentResDto> getCommentListByPost(long postId){
        return commentRepository.findCommentsByPostId(postId).orElseThrow(
                () -> new IllegalArgumentException("작성된 댓글이 없습니다."))
                .stream()
                .map((x) -> new CommentResDto(x, getFormattedTime(x.getUpdatedTime())))
                .collect(Collectors.toList());
    }

    private String getFormattedTime(LocalDateTime localDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return localDateTime.format(formatter);
    }

    @Transactional
    public void deleteComment(long commentId){
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new IllegalStateException("삭제하려는 댓글이 존재하지 않습니다.")
        );
        commentRepository.deleteComment(comment);
    }

}
