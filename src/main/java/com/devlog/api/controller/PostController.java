package com.devlog.api.controller;

import com.devlog.api.request.PostCreate;
import com.devlog.api.response.PostResponse;
import com.devlog.api.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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
     * @param postId
     * @return PostResponse
     */
    @GetMapping("/posts/{postId}")
    public PostResponse get(@PathVariable Long postId) {
        return postService.get(postId);
    }

    @GetMapping("/posts")
    public List<PostResponse> getList() {
        return postService.getList();
    }




}
