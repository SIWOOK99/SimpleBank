package hello.board.controller.board;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter @Setter
public class PostForm {

    @NotBlank(message = "제목을 입력해주세요.")
    private String title;

    @NotNull
    private String content;
}
