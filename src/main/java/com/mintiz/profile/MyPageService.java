package com.mintiz.profile;

import com.mintiz.bookmark.BookmarkRepository;
import com.mintiz.domain.BookmarkedPost;
import com.mintiz.domain.Post;
import com.mintiz.domain.User;
import com.mintiz.post.repository.PostRepository;
import com.mintiz.profile.model.getProfileRes;
import com.mintiz.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MyPageService {
    private final BookmarkRepository bookmarkRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    //bookmark
    public List<getProfileRes> getBookmarks(Long userId){
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("없는 회원입니다."));
        List<BookmarkedPost> posts = bookmarkRepository.findByUser(user);
        List<getProfileRes> bookmarks = new ArrayList<>();
        for (BookmarkedPost bookmark : posts) {
            getProfileRes getProfileRes = new getProfileRes();
            getProfileRes.setPostId(bookmark.getPost().getId());
            getProfileRes.setImage(bookmark.getPost().getImages().get(0));
        }
        return bookmarks;
    }

}
