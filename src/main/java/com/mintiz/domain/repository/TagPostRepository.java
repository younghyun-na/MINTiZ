package com.mintiz.domain.repository;

import com.mintiz.domain.TagPost;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class TagPostRepository {

    private final EntityManager em;

    public void save(TagPost tagPost){
        em.persist(tagPost);
    }

    public List<TagPost> findByPostId(Long postId) {
        return em.createQuery("select p from TagPost p " +
                "where p.post.id = :postId", TagPost.class)
                .setParameter("postId", postId)
                .getResultList();
    }

    public void delete(TagPost tagPost){
        em.remove(tagPost);
    }
}
