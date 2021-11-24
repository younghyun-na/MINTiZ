package com.mintiz.bookmark;

import com.mintiz.domain.BookmarkedPost;
import com.mintiz.domain.Post;
import com.mintiz.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookmarkRepository extends JpaRepository<BookmarkedPost, Long> {
    Optional<BookmarkedPost> findByUserAndPost(User user, Post post);
    List<BookmarkedPost> findByUser(User user);
    void deleteByUserAndPost(User user, Post post);
}
