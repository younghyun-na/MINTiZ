package com.mintiz.profile;

import com.mintiz.domain.Post;
import com.mintiz.domain.User;
import com.mintiz.post.repository.PostRepository;
import com.mintiz.profile.model.WritingRes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MyPageService {

    private final PostRepository postRepository;

    public List<WritingRes> getWritingByUser(User user){
        List<Post> byUser = postRepository.findByUser(user.getId());
        return byUser.stream()
                .map( u -> new WritingRes(u.getId(), u.getImages().get(0)))
                .collect(Collectors.toList());
    }

}
