package com.mintiz.post.repository;

import com.mintiz.domain.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TagRepository {

    private final EntityManager em;

    public void save(Tag tag){
        em.persist(tag);
    }

    /**
     getSingleResult()에서 No entity found for query 에러 발생
     :EmptyResultDataAccessException => getSingleResult() 문제
     */
    public Optional<Tag> findByName(String name){
         Tag tag = em.createQuery("select t from Tag t where t.tag_name = :name", Tag.class)
                .setParameter("name", name)
                .getResultList()
                .stream()
                .findFirst()
                .orElse(null);

        return Optional.ofNullable(tag);
    }

    public List<Tag> findTagList(){
        return em.createQuery("select t from Tag t", Tag.class)
                .getResultList();
    }

}
