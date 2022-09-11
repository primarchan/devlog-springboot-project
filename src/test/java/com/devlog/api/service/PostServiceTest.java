package com.devlog.api.service;

import com.devlog.api.domain.Post;
import com.devlog.api.repository.PostRepository;
import com.devlog.api.request.PostCreate;
import com.devlog.api.request.PostSearch;
import com.devlog.api.response.PostResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
    @DisplayName("글 1페이지 내림차순 조회")
    void Test3() {
        // given
        List<Post> requestPosts = IntStream.range(0, 20)
                        .mapToObj(i -> {
                            return Post.builder()
                                    .title("데브로그 제목 " + i)
                                    .content("서울 " + i)
                                    .build();
                        }).collect(Collectors.toList());
        postRepository.saveAll(requestPosts);

        // Pageable pageable = PageRequest.of(0, 5, DESC, "id");
        // Pageable pageable = PageRequest.of(0, 5);

        PostSearch postSearch = PostSearch.builder()
                .page(1)
                // .size(10)
                .build();

        // when
        List<PostResponse> posts = postService.getList(postSearch);


        // then
        // assertEquals(2L, posts.size());
        assertEquals(10l, posts.size());
        assertEquals("데브로그 제목 19", posts.get(0).getTitle());
//        assertEquals("데브로그 제목 26", posts.get(4).getTitle());
    }

}