package com.devlog.api.service;

import com.devlog.api.domain.Post;
import com.devlog.api.domain.PostEditor;
import com.devlog.api.exception.PostNotFound;
import com.devlog.api.repository.PostRepository;
import com.devlog.api.request.PostCreate;
import com.devlog.api.request.PostEdit;
import com.devlog.api.request.PostSearch;
import com.devlog.api.response.PostResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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
        Post post = postRepository.findById(id)
                .orElseThrow(PostNotFound::new);

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

    @Transactional
    public void edit(Long id, PostEdit postEdit) {
        Post post = postRepository.findById(id)
                .orElseThrow(PostNotFound::new);

//        PostEditor.PostEditorBuilder editorBuilder = post.toEditor();

//        PostEditor postEditor = editorBuilder.title(postEdit.getTitle())
//                .content(postEdit.getContent())
//                .build();

//        post.edit(postEditor);

        post.edit(postEdit.getTitle(), postEdit.getContent());
    }

    public void delete(Long id) {
        Post post  = postRepository.findById(id)
                .orElseThrow(PostNotFound::new);

        postRepository.delete(post);
    }

}
