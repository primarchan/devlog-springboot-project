package com.devlog.api.domain;

import com.devlog.api.request.PostEdit;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Lob
    private String content;

    @Builder
    public Post(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void edit(String title, String content) {
        this.title = title;
        this.content = content;
    }

//    public PostEditor.PostEditorBuilder toEditor() {
//        return PostEditor.builder()
//                .title(title)
//                .content(content);
//    }


//    public void edit(PostEditor postEditor) {
//        title = postEditor.getTitle();
//        content = postEditor.getContent();
//    }

}
