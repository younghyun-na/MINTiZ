package com.mintiz.domain.service;

import com.mintiz.domain.*;
import com.mintiz.domain.dto.PostSaveDto;
import com.mintiz.domain.dto.PostListResponseDto;
import com.mintiz.domain.dto.PostUpdateDto;
import com.mintiz.domain.repository.PostRepository;
import com.mintiz.domain.repository.TagPostRepository;
import com.mintiz.domain.repository.TagRepository;
import com.mintiz.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.logging.FileHandler;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final TagRepository tagRepository;
    private final TagPostRepository tagPostRepository;
    private final ImageHandler imageHandler;

    /**
     * 게시글 작성 : 태그, 댓글 고려
     * 전달 받야할 정보 : 유저정보, 내용, 태그, 위치, 이미지 파일
     */
    @Transactional
    public Long savePost(Long userId, PostSaveDto postSaveDto, List<String> tagNames, List<MultipartFile> files) throws Exception {

        Optional<User> findUser = userRepository.findById(userId);
        User user = findUser.get();

        postSaveDto.setUser(user);
        Post post = postSaveDto.toEntity();

        List<ImageFile> images = imageHandler.parseFileInfo(files);
        if(!images.isEmpty()){
            for (ImageFile image : images) {
                post.addImageFile(image);        //post 엔티티에 이미지 추가
            }
        }

        postRepository.save(post);

        //TagPost 테이블에 post_id에 맞춰서 tag 추가
        saveTagPost(tagNames, post);
        return post.getId();
    }

    /**
     * 게시글 수정 : 변경 감지(merge X)
     * 수정 내역 : 글 내용 + 태그(location 도) + 이미지 수정
     */
    @Transactional
    public void updatePost(Long postId, PostUpdateDto postUpdateDto, List<String> tagNames){ //후기
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("일치하는 게시글이 없습니다. postId= " + postId));
        //1.Post 수정
        post.updatePost(postUpdateDto);

        //2.Tag 수정(문제 발생)
        List<TagPost> tagPostList = tagPostRepository.findByPostId(postId);
        for(TagPost tagPost : tagPostList){
            tagPostRepository.delete(tagPost);
        }
        saveTagPost(tagNames, post);
    }

    @Transactional
    public void saveTagPost(List<String> tagNames, Post post) {
        for (String name : tagNames) {
            //태그가 존재하면 그대로 가져오고, 없으면 저장 후 반환
            Tag tag = tagRepository.findByName(name).orElseGet(() -> getTag(name));
            tagPostRepository.save(new TagPost(tag, post));
        }
    }

    private Tag getTag(String name) {
        Tag tag = Tag.builder().tag_name(name).build();
        tagRepository.save(tag);
        return tag;
    }

    /**
     * 게시글 개별 조회
     */
    public PostListResponseDto findPost(Long postId) {
        Post post = postRepository.findById(postId)
                    .orElseThrow(() ->
                        new IllegalArgumentException("해당 게시물이 없습니다. id= " + postId));

        return new PostListResponseDto(post);
    }

    /**
     * 게시글 조회: 전체 조회
     */
    public List<Post> findPostAll(){
        return postRepository.findList();
    }

    /**
     * 게시글 검색 : 내용으로 검색
     * postListResponseDto : 글 내용 + 이미지 + 글 쓴이 + 작성 날짜 + 위치 + 태그
     */
    public List<PostListResponseDto> searchPostByContent(String keyword){
        return postRepository.findByContent(keyword)
                .stream()
                .map(PostListResponseDto::new)
                .collect(Collectors.toList());
    }

    /**
     * 게시글 전체 조회 : 후기 / 추천 / 일상 선택(Tag 로 구분)
     * 태그 복수 선택해서 검색 불가능
     */

    public List<PostListResponseDto> findPostAllByTag(String tagName){
        return postRepository.findByTag(tagName)
                .stream()
                .map(PostListResponseDto::new)
                .collect(Collectors.toList());
    }

    /**
     * 게시글 삭제
     */
    @Transactional
    public void deletePost(Long postId){
        Post post = postRepository.findById(postId).orElseThrow(()->
                new IllegalArgumentException("존재하지 않는 게시글 입니다 + id=" + postId));
        postRepository.delete(post);
    }

}
