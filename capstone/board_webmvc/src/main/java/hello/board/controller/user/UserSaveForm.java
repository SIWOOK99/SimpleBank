package hello.board.controller.user;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;


//회원가입시 날라오는 데이터들을 저장하는 클래
@Getter @Setter
public class UserSaveForm {

    @NotBlank(message = "아이디는 필수 입력 값입니다.")
    private String loginId;

    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    private String password;

    @NotBlank(message = "이름은 필수 입력 값입니다.")
    private String name;

    private Integer age;
}
