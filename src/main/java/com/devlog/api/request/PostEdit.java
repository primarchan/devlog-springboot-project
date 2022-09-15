package com.devlog.api.request;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Getter
@Builder
@ToString
public class PostEdit {

    @NotBlank(message = "title 을 입력해주세요.")
    private String title;

    @NotBlank(message = "content 를 입력해주세요.")
    private String content;

    @Builder
    public PostEdit(String title, String content) {
        this.title = title;
        this.content = content;
    }


}
