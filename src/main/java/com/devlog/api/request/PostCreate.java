package com.devlog.api.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
@ToString
public class PostCreate {

    @NotBlank(message = "title 을 입력해주세요.")
    private String title;

    @NotBlank(message = "content 를 입력해주세요.")
    private String content;
}
