package com.devlog.api.repository;

import com.devlog.api.domain.Post;
import com.devlog.api.request.PostSearch;

import java.util.List;

public interface PostRepositoryCustom {

    List<Post> getList(PostSearch postSearch);
}
