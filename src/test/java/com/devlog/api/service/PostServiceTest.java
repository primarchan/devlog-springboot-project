package com.devlog.api.service;

import com.devlog.api.domain.Post;
import com.devlog.api.exception.PostNotFound;
import com.devlog.api.repository.PostRepository;
import com.devlog.api.request.PostCreate;
import com.devlog.api.request.PostEdit;
import com.devlog.api.request.PostSearch;
import com.devlog.api.response.PostResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
    @DisplayName("게시글 작성")
    void test1() {
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
    @DisplayName("게시글 1개 조회")
    void test2() {
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
    @DisplayName("게시글 1페이지 내림차순 조회")
    void test3() {
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

    @Test
    @DisplayName("게시글 제목 수정")
    void test4() {
        // given
        Post post = Post.builder()
                .title("제목")
                .content("내용입니다.")
                .build();

        postRepository.save(post);

        PostEdit postEdit = PostEdit.builder()
                .title("수정된 제목")
                .content("내용입니다.")
                .build();

        // when
        postService.edit(post.getId(), postEdit);

        // then
        Post changedPost = postRepository.findById(post.getId())
                .orElseThrow(() -> new RuntimeException("존재하지 않는 게시글입니다. id: " + post.getId()));

        assertEquals("수정된 제목", changedPost.getTitle());
        assertEquals("내용입니다.", changedPost.getContent());
    }

    @Test
    @DisplayName("게시글 내용 수정")
    void test5() {
        // given
        Post post = Post.builder()
                .title("제목")
                .content("내용입니다.")
                .build();

        postRepository.save(post);

        PostEdit postEdit = PostEdit.builder()
                .title("제목")
                .content("수정된 내용입니다.")
                .build();

        // when
        postService.edit(post.getId(), postEdit);

        // then
        Post changedPost = postRepository.findById(post.getId())
                .orElseThrow(() -> new RuntimeException("존재하지 않는 게시글입니다. id: " + post.getId()));

        assertEquals("제목", changedPost.getTitle());
        assertEquals("수정된 내용입니다.", changedPost.getContent());
    }

    @Test
    @DisplayName("게시글 삭제")
    void test6() {
        // given
        Post post = Post.builder()
                .title("제목")
                .content("내용입니다.")
                .build();

        postRepository.save(post);

        // when
        postService.delete(post.getId());

        // then
        assertEquals(0, postRepository.count());
    }

    @Test
    @DisplayName("게시글 1개 조회 - (예외처리)존재하지 않는 게시글")
    void test7() {
        // given
        Post post = Post.builder()
                .title("test7 제목")
                .content("test7 내용입니다.")
                .build();
        postRepository.save(post);

        // expected
        assertThrows(PostNotFound.class, () -> {
            postService.get(post.getId() + 1L);
        });
    }

    @Test
    @DisplayName("게시글 수정 - (예외처리)존재하지 않는 게시글")
    void test8() {
        // given
        Post post = Post.builder()
                .title("test8 제목")
                .content("test8 내용입니다.")
                .build();
        postRepository.save(post);

        PostEdit postEdit = PostEdit.builder()
                .title("test8 제목")
                .content("test8 수정된 내용입니다.")
                .build();


        // expected
        assertThrows(PostNotFound.class, () -> {
            postService.edit(post.getId() + 1L, postEdit);
        });
    }

    @Test
    @DisplayName("게시글 삭제 - (예외처리)존재하지 않는 게시글")
    void test9() {
        // given
        Post post = Post.builder()
                .title("test9 제목")
                .content("test9 내용입니다.")
                .build();
        postRepository.save(post);

        // expected
        assertThrows(PostNotFound.class, () -> {
            postService.delete(post.getId() + 1L);
        });
    }

}