package com.mintiz.post.service;

import com.mintiz.bookmark.BookmarkRepository;
import com.mintiz.domain.*;
import com.mintiz.post.model.PostListResDto;
import com.mintiz.post.model.PostResDto;
import com.mintiz.post.model.PostSaveDto;
import com.mintiz.post.model.PostUpdateDto;
import com.mintiz.post.repository.ImageRepository;
import com.mintiz.post.repository.PostRepository;
import com.mintiz.post.repository.TagPostRepository;
import com.mintiz.post.repository.TagRepository;
import com.mintiz.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final TagRepository tagRepository;
    private final TagPostRepository tagPostRepository;
    private final ImageHandler imageHandler;
    private final ImageRepository imageRepository;
    private final FileStore fileStore;
    private final BookmarkRepository bookmarkRepository;

    /**
     * 게시글 작성
     * 전달 받야할 정보 : 유저정보, 내용, 태그, 위치, 이미지 파일
     */
    @Transactional
    public Long savePost(Long userId, PostSaveDto postSaveDto){

        Optional<User> findUser = userRepository.findById(userId);
        postSaveDto.setUser(findUser.get());

        Post post = postSaveDto.toEntity();

        try{
            List<ImageFile> images = fileStore.storeFiles(postSaveDto.getImages());
            if(!images.isEmpty()){
                for (ImageFile image : images) {
                    post.addImageFile(image);
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        postRepository.save(post);

        //TagPost 테이블에 post_id에 맞춰서 tag 추가
        saveTagPost(postSaveDto.getTagName(), post);
        return post.getId();
    }

    @Transactional
    public void saveTagPost(String tagName, Post post) {
        Tag tag = tagRepository.findByName(tagName).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 태그입니다."));
        tagPostRepository.save(new TagPost(tag, post));
    }

    /**
     * 게시글 개별 조회 + 태그도
     */
    public PostResDto findPost(Long postId) {
        Post post = postRepository.findById(postId)
                    .orElseThrow(() ->
                        new IllegalArgumentException("해당 게시물이 없습니다. id= " + postId));
        String tagName = tagPostRepository.findByPostId(postId).getTag().getTag_name();

        PostResDto postResDto = new PostResDto(post, tagName);
        postResDto.setWriteDate(getFormattedTime(post.getCreatedAt()));

        //이미지 처리
        List<ImageFile> imageList = imageRepository.findImageByPostId(post.getId()).orElseThrow(
                () -> new IllegalStateException("게시글에 이미지가 존재하지 않습니다."));
        postResDto.setImageFiles(imageList);

        return postResDto;
    }

    private String getFormattedTime(LocalDateTime localDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return localDateTime.format(formatter);
    }


    /**
     * 게시글 조회: 전체 조회
     */

    public List<PostListResDto> findPostAll(User user){
        List<PostListResDto> postListResDtos = new ArrayList<>();
        List<Post> list = postRepository.findList();
        bookmarkCheckList(user, postListResDtos, list);
        return postListResDtos;
    }

    private void toDto(List<PostListResDto> postListResDtos, Post post, boolean b) {

        List<ImageFile> imageList = imageRepository.findImageByPostId(post.getId()).orElseThrow(
                () -> new IllegalStateException("게시글에 이미지가 존재하지 않습니다."));

        PostListResDto postListResDto = PostListResDto.builder()
                .post(post)
                .check(b)
                .image(imageList.get(0))       //썸네일
                .updatedTime(getLocalTimeDiff(post.getUpdatedTime())).build();
        postListResDtos.add(postListResDto);
    }

    /**
     * 북마크 했는지 확인
     */
    private boolean isAlreadyBookmark(User user, Post post) {
        return bookmarkRepository.findByUserAndPost(user, post).isPresent();
    }

    /**
     * 게시글 검색 : 내용으로 검색
     * postListResponseDto : 글 내용 + 이미지 + 글 쓴이 + 작성 날짜 + 위치 + 태그
     */

    public List<PostListResDto> searchPostByContent(String keyword, User user){
        List<PostListResDto> postListResDtos = new ArrayList<>();
        List<Post> list = postRepository.findByContent(keyword);
        bookmarkCheckList(user, postListResDtos, list);
        return postListResDtos;
    }

    /**
     * 게시글 전체 조회 : 후기 / 일상 선택(Tag 로 구분)
     */


    public List<PostListResDto> findPostAllByTag(String tagName, User user){

        List<PostListResDto> postListResDtos = new ArrayList<>();
        List<Post> list = postRepository.findByTag(tagName);
        bookmarkCheckList(user, postListResDtos, list);
        return postListResDtos;

    }

    private void bookmarkCheckList(User user, List<PostListResDto> postListResDtos, List<Post> list) {
        for (Post post : list) {
            if (isAlreadyBookmark(user, post)) { // 북마크 했다면
                toDto(postListResDtos, post, true);
            } else {
                toDto(postListResDtos, post, false);
            }

        }
    }

    private String getLocalTimeDiff(LocalDateTime postTime){
        LocalDateTime currentTime = LocalDateTime.now();  //현재 시간
        Duration duration= Duration.between(postTime, currentTime);
        long seconds = duration.toSeconds();

        if(seconds < 60){
            return seconds + " 초 전";             //현재 시간과 초 차이가 <60 이면 초
        }else if(seconds >= 60 && seconds < 3600){
            return  duration.toMinutes() + " 분 전";      //현재 시간과 초 차이가 60<= x  <= 3600(1시간)이면 분
        }else if(seconds >= 3600 && seconds < 86400){
            return duration.toHours() + " 시간 전";      //현재 시간과 초 차이가 3600 이상이면 시간
        }

        return duration.toDays() + " 일 전";            //나머지는 일
    }

    /**
     * 게시글 수정
     */
    @Transactional
    public void updatePost(Long postId, PostUpdateDto postUpdateDto){ //후기
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("일치하는 게시글이 없습니다. postId= " + postId));
        try{
            List<ImageFile> imageFileList = fileStore.storeFiles(postUpdateDto.getNewImages());     //1.이미지 수정
            post.getImages().clear();

            for(ImageFile imageFile : imageFileList){
                post.addImageFile(imageFile);
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        post.updatePost(postUpdateDto);         //2.post 수정

        TagPost tagPost = tagPostRepository.findByPostId(postId);
        tagPost.updateTagPost(tagRepository.findByName(postUpdateDto.getTagName()).get());  //3.tag 수정
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
