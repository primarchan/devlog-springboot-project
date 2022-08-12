package com.devlog.api.service;

import com.devlog.api.domain.Post;
import com.devlog.api.repository.PostRepository;
import com.devlog.api.request.PostCreate;
import com.devlog.api.response.PostResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class PostServiceTest {

    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    @BeforeEach
    void clean() {
        postRepository.deleteAll();
    }

    @Test
    @DisplayName("글 작성")
    void Test1() {
        // given
        PostCreate postCreate = PostCreate.builder()
                .title("test1 제목입니다.")
                .content("test1 내용입니다.")
                .build();

        // when
        postService.write(postCreate);

        // then
        assertEquals(1L, postRepository.count());
        Post post = postRepository.findAll().get(0);
        assertEquals("test1 제목입니다.", post.getTitle());
        assertEquals("test1 내용입니다.", post.getContent());

    }

    @Test
    @DisplayName("글 1개 조회")
    void Test2() {
        // given
        Post requestPost = Post.builder()
                .title("test2 제목입니다.")
                .content("test2 내용입니다.")
                .build();
        postRepository.save(requestPost);

        // when
        PostResponse response = postService.get(requestPost.getId());

        // then
        assertNotNull(response);
        assertEquals(1L, postRepository.count());
        assertEquals("test2 제목입니다.", response.getTitle());
        assertEquals("test2 내용입니다.", response.getContent());
    }

    @Test
    @DisplayName("글 전체 조회")
    void Test3() {
        // given
        postRepository.saveAll(List.of(
                Post.builder()
                        .title("test3 제목입니다.(1)")
                        .content("test3 내용입니다.(1)")
                        .build(),
                Post.builder()
                        .title("test3 제목입니다.(2)")
                        .content("test3 내용입니다.(2)")
                        .build()
        ));

        // when
        List<PostResponse> posts = postService.getList();

        // then
        assertEquals(2L, posts.size());
    }


}