package com.devlog.api.controller;

import com.devlog.api.request.PostCreate;
import com.devlog.api.request.PostEdit;
import com.devlog.api.request.PostSearch;
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
     * @apiNote 게시글 등록 API
     * @param request
     */
    @PostMapping("/posts")
    public void post(@RequestBody @Valid PostCreate request)  {
        postService.write(request);
    }

    /**
     * @apiNote 게시글 단건 조회 API
     * @param postId
     * @return PostResponse
     */
    @GetMapping("/posts/{postId}")
    public PostResponse get(@PathVariable Long postId) {
        return postService.get(postId);
    }

    /**
     * @apiNote 게시글 전체 조회 API (페이징 처리)
     * @param postSearch
     * @return
     */
    @GetMapping("/posts")
    public List<PostResponse> getList(@ModelAttribute PostSearch postSearch) {
        return postService.getList(postSearch);
    }

    /**
     * @apiNote 게시글 수정 API
     * @param postId
     * @param request
     */
    @PatchMapping("/posts/{postId}")
    public void edit(@PathVariable Long postId, @RequestBody @Valid PostEdit request) {
        postService.edit(postId, request);
    }

    /**
     * @apiNote 게시글 삭제 API
     * @param postId
     */
    @DeleteMapping("/posts/{postId}")
    public void delete(@PathVariable Long postId) {
        postService.delete(postId);
    }

}
