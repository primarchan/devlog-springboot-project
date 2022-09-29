package com.devlog.api.request;

import com.devlog.api.exception.InvalidRequest;
import lombok.Builder;
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

    @Builder
    public PostCreate(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void validate() {
        if (title.contains("규정에 맞지 않는 내용")) {
            throw new InvalidRequest("title", "제목에 규정에 맞지 않는 내용을 포함할 수 없습니다.");
        }
    }
}
