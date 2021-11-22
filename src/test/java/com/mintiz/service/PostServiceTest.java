package com.mintiz.service;

import com.mintiz.domain.Tag;
import com.mintiz.domain.User;
import com.mintiz.post.model.PostListResDto;
import com.mintiz.post.model.PostSaveDto;
import com.mintiz.post.model.PostUpdateDto;
import com.mintiz.post.repository.PostRepository;
import com.mintiz.post.repository.TagPostRepository;
import com.mintiz.post.repository.TagRepository;
import com.mintiz.post.service.PostService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@SpringBootTest
@Transactional
public class PostServiceTest {

    @Autowired private PostService postService;
    @Autowired private PostRepository postRepository;
    @Autowired private EntityManager em;
    @Autowired private TagPostRepository tagPostRepository;
    @Autowired private TagRepository tagRepository;


    @Test
    @Rollback(false)
    public void addPost(){

        //given
        User userA = createUser();

        PostSaveDto postSaveDto = new PostSaveDto();
        postSaveDto.setContent("내용1");
        postSaveDto.setLocation("서울1");
        postSaveDto.setTagName("일상");

        //when
        Long postId = postService.savePost(userA.getId(), postSaveDto);

        //then
        Assertions.assertEquals(postSaveDto.getContent(), postRepository.findById(postId).get().getContent());
    }

    @Test
    public void findPostAll(){
        //given
        createTag();
        User userA = createUser();
        PostSaveDto postSaveDto = createPost("123내용", "서울", "후기");
        Long postId = postService.savePost(userA.getId(), postSaveDto);

        User userB = createUser2();
        PostSaveDto postSaveDto1 = createPost("123내용123", "부산", "일상");
        Long postId1 = postService.savePost(userB.getId(), postSaveDto);

        //when
        //List<Post> postAll = postService.findPostAll();

        //then
        //Assertions.assertEquals(2, postAll.size());

    }


    @Test
    public void findPostByTag(){
        //given
        createTag();
        User userA = createUser();
        PostSaveDto postSaveDto = createPost("123내용", "서울", "후기");
        Long postId = postService.savePost(userA.getId(), postSaveDto);

        User userB = createUser2();
        PostSaveDto postSaveDto1 = createPost("123내용123", "부산","후기");
        Long postId1 = postService.savePost(userB.getId(), postSaveDto);

        //when
        List<PostListResDto> list = postService.findPostAllByTag("후기");

        //then
        Assertions.assertEquals(2, list.size());

    }

    @Test
    public void findPostByContent(){
        //given
        createTag();
        User userB = createUser();
        PostSaveDto postSaveDto = createPost("123내용", "서울","후기");
        Long postId = postService.savePost(userB.getId(), postSaveDto);

        PostSaveDto postSaveDto2 = createPost("내용12", "부산","일상");
        Long postId2 = postService.savePost(userB.getId(), postSaveDto2);

        //when
        String keyword = "내용";
        List<PostListResDto> postList = postService.searchPostByContent(keyword);

        //then
        Assertions.assertEquals(2, postList.size() );
    }


    /**
     * A collection with cascade="all-delete-orphan" was no longer referenced by the owning entity instance: com.mintiz.domain.Post.images
     **/

    @Test
    @Rollback(value = false)
    public void updatePost(){
        //given
        createTag();
        User userC = createUser();
        PostSaveDto postSaveDto = createPost("123내용", "서울", "일상");
        Long postId = postService.savePost(userC.getId(), postSaveDto);

        //when
        PostUpdateDto newPostUpdateDto = new PostUpdateDto();
        newPostUpdateDto.setContent("바보");
        newPostUpdateDto.setLocation("부산");
        newPostUpdateDto.setTagName("후기");

        postService.updatePost(postId, newPostUpdateDto);

        //then
        Assertions.assertEquals(newPostUpdateDto.getContent(), postRepository.findById(postId).get().getContent());   //내용 검증
        Assertions.assertEquals("후기", tagPostRepository.findByPostId(postId).getTag().getTag_name()); //태그 검증
    }

    /**
     * DataIntegrityViolationException : 데이터를 삽입하거나 업데이트하려는 시도가 무결성 제약 조건을 위반할 때 throw 되는 예외
     * Caused by: org.hibernate.exception.ConstraintViolationException
     * 아님 tag 에 미리 데이터를 생성해놓고(후기랑 추천 일상소통 3개니가)
     */
    @Test
    @Rollback(false)
    public void validateDuplicateTag() throws Exception {
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

    private PostSaveDto createPost(String content, String location, String tagName) {
        PostSaveDto postSaveDto = new PostSaveDto();
        postSaveDto.setContent(content);
        postSaveDto.setLocation(location);
        postSaveDto.setTagName(tagName);
        return postSaveDto;
    }

    private User createUser() {
        User userA = User.builder()
                .name("세미")
                .email("세미 이메일")
                .password("1234").build();
        em.persist(userA);
        return userA;
    }

    private User createUser2() {
        User userA = User.builder()
                .name("지민")
                .email("지민 이메일")
                .password("12341").build();
        em.persist(userA);
        return userA;
    }

    private void createTag(){
        Tag tag1 = Tag.builder().tag_name("후기").build();
        Tag tag2 = Tag.builder().tag_name("일상").build();

        em.persist(tag1);
        em.persist(tag2);
    }
}
