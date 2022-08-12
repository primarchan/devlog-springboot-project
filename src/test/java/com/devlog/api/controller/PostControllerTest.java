package com.devlog.api.controller;

import com.devlog.api.domain.Post;
import com.devlog.api.repository.PostRepository;
import com.devlog.api.request.PostCreate;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
class PostControllerTest {
    @Autowired
    private  MockMvc mockMvc;

    @Autowired
    private PostRepository postRepository;

    @BeforeEach
    void clean() {
        postRepository.deleteAll();
    }

    @Test
    @DisplayName("/posts 요청 시 Hello World를 출력한다.")
    void test1() throws Exception {
        // given
        PostCreate request = PostCreate.builder()
                .title("test1 제목입니다.")
                .content("test1 내용입니다.")
                .build();

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(request);

        // expected
        mockMvc.perform(post("/posts")
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(content().string(""))
                .andDo(print());
    }

    @Test
    @DisplayName("/posts 요청 시 title 값은 필수다.")
    void test2() throws Exception {
        // given
        PostCreate request = PostCreate.builder()
                .title(null)
                .content("test2 내용입니다.")
                .build();

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(request);

        // expected
        mockMvc.perform(post("/posts")
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.message").value("잘못된 요청입니다."))
                .andExpect(jsonPath("$.validation.title").value("title 을 입력해주세요."))
                .andDo(print());
    }

    @Test
    @DisplayName("/posts 요청 시 DB 에 값이 저장된다. (게시글 등록/저장)")
    void test3() throws Exception {
        // given
        PostCreate request = PostCreate.builder()
                .title("test3 제목입니다.")
                .content("test3 내용입니다.")
                .build();

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(request);

        // when
        mockMvc.perform(post("/posts")
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andDo(print());

        // then
        assertEquals(1L, postRepository.count());

        Post post = postRepository.findAll().get(0);
        assertEquals("test3 제목입니다.", post.getTitle());
        assertEquals("test3 내용입니다.", post.getContent());
    }

    @Test
    @DisplayName("게시글 단건 조회")
    void test4() throws Exception {
        // given
        Post post = Post.builder()
                .title("test4 제목입니다.")
                .content("test4 내용입니다.")
                .build();
        postRepository.save(post);

        // expected(when + then)
        mockMvc.perform(get("/posts/{postId}", post.getId())
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(post.getId()))
                .andExpect(jsonPath("$.title").value("test4 제목입니다."))
                .andExpect(jsonPath("$.content").value("test4 내용입니다."))
                .andDo(print());
    }

    @Test
    @DisplayName("게시글 전체 조회")
    void test5() throws Exception {
        // given
        Post post1 = postRepository.save(Post.builder()
                        .title("test5 제목입니다.(1)")
                        .content("test5 내용입니다.(1)")
                        .build()
        );

        Post post2 = postRepository.save(Post.builder()
                .title("test5 제목입니다.(2)")
                .content("test5 내용입니다.(2)")
                .build()
        );

        // expected(when + then)
        mockMvc.perform(get("/posts")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", Matchers.is(2)))
                .andExpect(jsonPath("$[0].id").value(post1.getId()))
                .andExpect(jsonPath("$[0].title").value("test5 제목입니다.(1)"))
                .andExpect(jsonPath("$[0].content").value("test5 내용입니다.(1)"))
                .andExpect(jsonPath("$[1].id").value(post2.getId()))
                .andExpect(jsonPath("$[1].title").value("test5 제목입니다.(2)"))
                .andExpect(jsonPath("$[1].content").value("test5 내용입니다.(2)"))
                .andDo(print());
    }

}