package com.devlog.api.controller;

import com.devlog.api.domain.Post;
import com.devlog.api.request.PostCreate;
import com.devlog.api.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    /**
     * 게시글 등록 API
     * @param request
     */
    @PostMapping("/posts")
    public void post(@RequestBody @Valid PostCreate request)  {
        postService.write(request);
    }

    /**
     * 게시글 단건 조회 API
     */
    @GetMapping("/posts/{postId}")
    public Post get(@PathVariable(name = "postId") Long id) {
        Post post = postService.get(id);
        return post;
    }

}
