package com.mintiz.domain.repository;

import com.mintiz.domain.Tag;
import com.mintiz.domain.TagPost;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class TagPostRepository {

    private final EntityManager em;

    public void save(TagPost tagPost){
        em.persist(tagPost);
    }

    public TagPost findByPostId(Long postId) {
        return em.createQuery("select p from TagPost p " +
                "where p.post.id = :postId", TagPost.class)
                .setParameter("postId", postId)
                .getSingleResult();
    }

    @Modifying
    @Transactional
    public void updateTagPost(TagPost tagPost, Tag tag){
        em.createQuery("update TagPost p set p.tag = :tag where p.id = :tagPostId")
                .setParameter("tag", tag)
                .setParameter("tagPostId", tagPost.getId())
                .executeUpdate();
        em.clear();
    }

    @Modifying
    @Transactional
    public void delete(TagPost tagPost){
        em.remove(tagPost);
    }
}
