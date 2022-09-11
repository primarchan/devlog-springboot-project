package com.devlog.api.service;

import com.devlog.api.domain.Post;
import com.devlog.api.repository.PostRepository;
import com.devlog.api.request.PostCreate;
import com.devlog.api.request.PostSearch;
import com.devlog.api.response.PostResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public void write(PostCreate postCreate) {
        Post post = Post.builder()
                .title(postCreate.getTitle())
                .content(postCreate.getContent())
                .build();

        postRepository.save(post);
    }

    public PostResponse get(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 글입니다."));

        return PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .build();
    }

    public List<PostResponse> getList(PostSearch postSearch) {
        /*
        return postRepository.getList(1).stream()
                // .map(post -> new PostResponse(post))
                .map(PostResponse::new)
                .collect(Collectors.toList());

         */
        return postRepository.getList(postSearch).stream()
                .map(PostResponse::new)
                .collect(Collectors.toList());
    }
}
