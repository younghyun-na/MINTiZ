package com.mintiz.domain.repository;

import com.mintiz.domain.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PostRepository {

    private final EntityManager em;

    public void save(Post post){
        em.persist(post);
    }

    public Optional<Post> findById(Long id){
        Post post = em.find(Post.class, id);
        return Optional.ofNullable(post);
    }

    public List<Post> findByContent(String keyword){
        List<Post> postList = em.createQuery("select p from Post p " +
                "where p.content like CONCAT('%',:keyword,'%') ", Post.class)
                .setParameter("keyword", keyword)
                .getResultList();

        return postList;
    }

    public List<Post> findByTag(String tagName){
        return em.createQuery("select p from Post p left join p.tagPosts t where t.tag.tag_name = :tagName")
                .setParameter("tagName", tagName)
                .getResultList();
    }


    public List<Post> findList(){
        return em.createQuery("select p from Post p", Post.class)
                .getResultList();
    }

    public void delete(Post post){
        em.remove(post);
    }

}
