package com.mintiz.post.service;

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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
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
    private final ImageRepository imageRepository;

    /**
     * 그니까 Controller => service 로는 id/dto 전달, service에서 영속성 컨텍스트 관련 처리
     * 게시글 작성 : 태그, 댓글 고려
     * 전달 받야할 정보 : 유저정보, 내용, 태그, 위치, 이미지 파일
     *List<MultipartFile> files
     */
    @Transactional
    public Long savePost(Long userId, PostSaveDto postSaveDto){

        Optional<User> findUser = userRepository.findById(userId);
        postSaveDto.setUser(findUser.get());

        Post post = postSaveDto.toEntity();

        try{
            List<ImageFile> images = imageHandler.parseFileInfo(postSaveDto.getImages());  //imageFile 엔티티로 변경
            if(!images.isEmpty()){
                for (ImageFile image : images) {
                    post.addImageFile(image);        //post 엔티티에 이미지 추가
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
     * 게시글 수정 : 변경 감지(merge X)
     * 수정 내역 : 글 내용 + 태그(location 도) + 이미지 수정
     * api 자체를 /postId/?tag = 후기 & region = 서울
     */
    @Transactional
    public void updatePost(Long postId, PostUpdateDto postUpdateDto, String tagName){ //후기
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("일치하는 게시글이 없습니다. postId= " + postId));

        post.updatePost(postUpdateDto);         //1.post 수정(자동 update)

        TagPost tagPost = tagPostRepository.findByPostId(postId);
        tagPostRepository.updateTagPost(tagPost, tagRepository.findByName(tagName).get());      //2.tag 수정
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
        postResDto.setImageFiles(imageRepository.findImageByPostId(postId));

        return postResDto;
    }

    private String getFormattedTime(LocalDateTime localDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return localDateTime.format(formatter);
    }


    /**
     * 게시글 조회: 전체 조회
     */

    public List<PostListResDto> findPostAll(){
        return postRepository.findList()
                .stream()
                .map((x) -> new PostListResDto(x, getLocalTimeDiff(x.getUpdatedTime())))
                .collect(Collectors.toList());
    }

    /**
     * 게시글 검색 : 내용으로 검색
     * postListResponseDto : 글 내용 + 이미지 + 글 쓴이 + 작성 날짜 + 위치 + 태그
     */
    public List<PostListResDto> searchPostByContent(String keyword){
        return postRepository.findByContent(keyword)
                .stream()
                .map((x) -> new PostListResDto(x, getLocalTimeDiff(x.getUpdatedTime())))
                .collect(Collectors.toList());
    }

    /**
     * 게시글 전체 조회 : 후기 / 일상 선택(Tag 로 구분)
     */

    public List<PostListResDto> findPostAllByTag(String tagName){
        return postRepository.findByTag(tagName)
                .stream()
                .map((x) -> new PostListResDto(x, getLocalTimeDiff(x.getUpdatedTime())))
                .collect(Collectors.toList());
    }

    /**
     * 먼저 Duration 클래스는 두 시간 사이의 간격을 초나 나노 초 단위로 나타냄
     * public static Duration between(Temporal startInclusive, Temporal endExclusive) {
     *    ...
     *    try{
     *        nanos = endExclusive.getLong(NANO_OF_SECOND) - startInclusive.getLong(NANO_OF_SECOND);   //반대로 뺴줌
     *    }
     *
     *
     */
    private String getLocalTimeDiff(LocalDateTime postTime){
        LocalDateTime currentTime = LocalDateTime.now();  //현재 날짜 그대로, 시간만 자정으로 초기화
        Duration duration= Duration.between(postTime, currentTime);
        long seconds = duration.toSeconds();     //-값이 나옴..

        if(seconds < 60){
            return seconds + " 초 전";             //현재 시간과 초 차이가 <60 이면 초
        }else if(seconds >= 60 && seconds < 3600){
            return  duration.toMinutes() + " 분 전";      //현재 시간과 초 차이가 60<= x  <= 3600(1시간)이면 분
        }else if(seconds >= 3600 && seconds < 86400){
            return duration.toHours() + " 시간 전";      //현재 시간과 초 차이가 3600 이상이면 시간? 일?
        }

        return duration.toDays() + " 일 전";
    }
    /**
     * 게시글 삭제
     */
    @Transactional
    public void deletePost(Long postId){
        Post post = postRepository.findById(postId).orElseThrow(()->
                new IllegalArgumentException("존재하지 않는 게시글 입니다 + id=" + postId));
        postRepository.delete(post);         //post를 삭제
    }


}
