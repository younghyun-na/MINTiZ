package com.mintiz.domain.repository;

import com.mintiz.domain.ImageFile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ImageRepository {

    private final EntityManager em;

    public void save(ImageFile imageFile){
        em.persist(imageFile);
    }

    public ImageFile findById(Long imageId){
        return em.find(ImageFile.class, imageId);
    }

    public List<ImageFile> findImageByPostId(Long postId){
        return em.createQuery("select i from ImageFile i where i.post.id = :postId", ImageFile.class)
                .setParameter("postId", postId)
                .getResultList();
    }

    public void delete(ImageFile imageFile){
        em.remove(imageFile);
    }
}
