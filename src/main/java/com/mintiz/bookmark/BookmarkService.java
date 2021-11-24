package com.mintiz.bookmark;

import com.mintiz.domain.BookmarkedPost;
import com.mintiz.domain.Post;
import com.mintiz.domain.User;
import com.mintiz.post.repository.PostRepository;
import com.mintiz.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BookmarkService {
    private final BookmarkRepository bookmarkRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    /**
     * 북마크
     */
    @Transactional
    public boolean addBookmark(Long userId, Long postId) {
        Post post = postRepository.findById(postId).orElseThrow();
        User user = userRepository.findById(userId).orElseThrow();

        //중복 좋아요 방지
        if(isNotAlreadyBookmark(user, post)) {
            BookmarkedPost bookmarkedPost = toEntity(user, post);
            bookmarkRepository.save(bookmarkedPost);
            return true;
        }
        return false;
    }

    /**
     * 북마크 취소
     */
    @Transactional
    public boolean cancelBookmark(Long userId, Long postId) {
        Post post = postRepository.findById(postId).orElseThrow();
        User user = userRepository.findById(userId).orElseThrow();

        bookmarkRepository.deleteByUserAndPost(user,post);
        return true;
    }


    private BookmarkedPost toEntity(User user, Post post){
        return BookmarkedPost.builder()
                .user(user)
                .post(post)
                .build();
    }
    /**
     * 사용자가 북마크한 글 목록 조회
     */
    public List<BookmarkedPost> getBookmarkByUser(User user){
        return bookmarkRepository.findByUser(user);
    }

    /**
     * 북마크 했는지 확인
     */
    private boolean isNotAlreadyBookmark(User user, Post post) {
        return bookmarkRepository.findByUserAndPost(user, post).isEmpty();
    }
}
