package com.mintiz.service;

import com.mintiz.domain.Tag;
import com.mintiz.domain.User;
import com.mintiz.domain.dto.PostListResponseDto;
import com.mintiz.domain.dto.PostSaveDto;
import com.mintiz.domain.dto.PostUpdateDto;
import com.mintiz.domain.repository.PostRepository;
import com.mintiz.domain.repository.TagPostRepository;
import com.mintiz.domain.repository.TagRepository;
import com.mintiz.domain.service.PostService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@Transactional
public class PostServiceTest {

    @Autowired private PostService postService;
    @Autowired private PostRepository postRepository;
    @Autowired private EntityManager em;
    @Autowired private TagPostRepository tagPostRepository;
    @Autowired private TagRepository tagRepository;

    /*
    @Test
    @Rollback(false)
    public void addPost(){

        //given
        User userA = createUser();

        PostSaveDto postSaveDto = new PostSaveDto();
        postSaveDto.setContent("내용");
        postSaveDto.setLocation("서울");
        //test 할 때 문제 발생 => postDto 에서 변환한 post 를 못가져와..ㅠㅠ

        List<String> tagNames = createTagNames();
        //when
        Long postId = postService.savePost(userA.getId(), postSaveDto, tagNames);
        Long postId2 = postService.savePost(userA.getId(), postSaveDto, tagNames);

        //then
        Assertions.assertEquals(postSaveDto.getContent(), postRepository.findById(postId).get().getContent());
        Assertions.assertEquals(2, tagPostRepository.findByPostId(postId).size());
    }


    @Test
    public void findPostByContent(){
        //given
        User userB = createUser();
        PostSaveDto postSaveDto = createPost("123내용", "서울");
        List<String> tagNames = createTagNames();
        Long postId = postService.savePost(userB.getId(), postSaveDto,tagNames);

        PostSaveDto postSaveDto2 = createPost("내용12", "부산");
        Long postId2 = postService.savePost(userB.getId(), postSaveDto2,tagNames);

        //when
        String keyword = "내용";
        List<PostListResponseDto> postList = postService.searchPostByContent(keyword);

        //then
        Assertions.assertEquals(2, postList.size() );
    }

    @Test
    @Rollback(false)
    /**
     * A collection with cascade="all-delete-orphan" was no longer referenced by the owning entity instance: com.mintiz.domain.Post.images
    **/
    /*
    public void updatePost(){
        //given
        User userC = createUser();
        PostSaveDto postSaveDto = createPost("123내용", "서울");
        List<String> tagNames = createTagNames();
        Long postId = postService.savePost(userC.getId(), postSaveDto, tagNames);

        //when
        PostUpdateDto newPostUpdateDto = new PostUpdateDto();
        newPostUpdateDto.setContent("바보");
        newPostUpdateDto.setLocation("부산");

        List<String> newTagNames = new ArrayList<>();
        newTagNames.add("후기");
        postService.updatePost(postId, newPostUpdateDto, newTagNames);

        //then
        Assertions.assertEquals(newPostUpdateDto.getContent(), postRepository.findById(postId).get().getContent());   //내용 검증
        Assertions.assertEquals(1, tagPostRepository.findByPostId(postId).size()); //태그 검증

    }
    */
    /**
     * DataIntegrityViolationException : 데이터를 삽입하거나 업데이트하려는 시도가 무결성 제약 조건을 위반할 때 throw 되는 예외
     * Caused by: org.hibernate.exception.ConstraintViolationException
     */
    @Test
    public void validateDuplicateTag() throws Exception{
        //give
        Tag tag1 = Tag.builder().tag_name("후기").build();

        Tag tag2 = Tag.builder().tag_name("후기").build();

        //when
        tagRepository.save(tag1);
        //then
        tagRepository.save(tag2);
        /**
         * unique = true
         * unique in @Column is used only if you let your JPA provider create the database for you - it will create the unique constraint on the specified column.
         * But if you already have the database, or you alter it once created, then unique doesn't have any effect.
         */
    }

    private PostSaveDto createPost(String content, String location) {
        PostSaveDto postSaveDto = new PostSaveDto();
        postSaveDto.setContent(content);
        postSaveDto.setLocation(location);
        return postSaveDto;
    }

    private User createUser() {
        User userA = new User();
        userA.setName("세미");
        userA.setEmail("세미 이메일");
        userA.setPassword("1234");
        em.persist(userA);
        return userA;
    }

    private List<String> createTagNames() {
        List<String> tagNames = new ArrayList<>();
        tagNames.add("후기");
        tagNames.add("추천");
        return tagNames;
    }
}
