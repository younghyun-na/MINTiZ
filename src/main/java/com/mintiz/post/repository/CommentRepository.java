package com.mintiz.post.repository;

import com.mintiz.domain.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CommentRepository {

    private final EntityManager em;

    public void save(Comment comment){
        em.persist(comment);
    }

    public Optional<Comment> findById(long commentId){
        Comment comment = em.find(Comment.class, commentId);
        return Optional.ofNullable(comment);

    }

    //댓글이 존재하지 않는 경우 체크(optional)
    public Optional<List<Comment>> findCommentsByPostId(Long postId) {
        List<Comment> commentList = em.createQuery("select c from Comment c " +
                "where c.post.id = :postId", Comment.class)
                .setParameter("postId", postId)
                .getResultList();

        return Optional.ofNullable(commentList);
    }

    @Modifying
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateComment(String content, long commentId){
        em.createQuery("update Comment c set c.content = :content " +
                "where c.id = :commentId")
                .setParameter("content", content)
                .setParameter("commentId", commentId)
                .executeUpdate();
        em.clear();
    }


    public void deleteComment(Comment comment){
        em.remove(comment);
    }


}
