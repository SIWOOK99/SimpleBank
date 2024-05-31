package hello.board.controller.user;

import hello.board.domain.User;
import hello.board.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;


@Controller
//log 관련 어노테이션
@Slf4j
//controller는 service와 domain을 사용해서 동작, html 매핑도해줌
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    //서버의 데이터를 조회하거나 얻어오는 Get Method(사용자의 입장에서)
    @GetMapping("/signup")
    /*
    view -> model 의 구조에서
    만약 여기서 userSaveForm 클래스인 userForm객체를 @Modelattribute 하지 않으면, view에서 페이지를 띄울때
    어떤 데이터가 필요한지 몰라서 view에서 렌더링때
    ID : [ 실제 사용자가 Id 입력할 박스 ]
    PW : [ PW 박스 ]
    대신
    ??? : [ 실제 사용자가 Id 입력할 박스 ]
    ??? : [ PW 박스 ]
    즉 ID,PW 라는 데이터 자체를 모르게됨, 그걸위한 addAttribute
    그리고 그냥 바로 html리턴해서 렌더링해서 보여주게됨
    */
    public String signupForm(@ModelAttribute("userForm") UserSaveForm userForm) {
        log.info("signupForm");
        return "users/signupForm";
    }

    //실제로 서버에 데이터를 보내는 Post(보낸다) Method(사용자의 입장에서)
    @PostMapping("/signup")
    /*
    @ModelAttribute("userForm") UserSaveForm userForm
    ->
    UserSaveForm userForm - 객체생성
    Model.addAttribute(userForm) - 모델에 userForm 객체를 넣는다
     */
    public String signup(@Valid @ModelAttribute("userForm") UserSaveForm userForm, BindingResult bindingResult) {

        log.info("signup");

        if (bindingResult.hasErrors()) {
            log.info("errors = {}", bindingResult);
            return "users/signupForm";
        }

        //user를 domain을 만들고, 그걸 service에 넘김
        User user = User.builder()
                .loginId(userForm.getLoginId())
                .password(userForm.getPassword())
                .name(userForm.getName())
                .age(userForm.getAge()).build();

        //실제로 여기서는 repository.join을 사용해서 db에 저장(실제론 em)->db
        //flow를 정리해보면
        /*
        1. 사용자가 get 요청으로 signup 페이지를 요청한다,
        2. @GetMapping("/signup")어노테이션이 붙은 함수가 동작하고, 이때 파라미터로 @ModelAttribute로 model에 미리 정한 UserSaveForm(id,pw,age,이름 등등)이라는 클래스의 객체를 넣어줘서 데이터를 정한다.
        3. view에서 thymeleaf로 받은 페이지를 렌더링할때 model을 참조해서 데이터를 받아오고 (html에서 렌더링 = front역할) 사용자가 어떤 박스에 어떤 값을 넣어야할지 정확하게 알게해준다.
         2번의 @ModelAttribute 과정이 있기때문에 정확한 정보를 전달가능하다.
         view <- model <- controller
            front   @ModelAttribute

        4. 사용자가 데이터를 박스에 쓰고, 그 값을 [제출] 버튼으로 Post 요청을 한다.
        5. @PostMapping이 동작하고 받은 데이터를
            @ModelAttribute("userForm") UserSaveForm userForm 코드가 동작하고
            이 코드는 2줄로 나눠보면
            UserSaveForm userForm - post 요청으로 받은 데이터로 초기화한(생성자) 객체를 생성
            Model.addAttribute(userForm) - 모델에 요청받은 데이터로 초기화한 객체 userForm을 추가한다 -> 추후에 view에서 쓰기위함
        6. 우리는 userForm 객체에서 getter로 User Domain 객체를 생성할수있고
        7. User user 객체를 parameter로 서비스에 넘겨서 userService.join(user)으로 실행 시켜준다
        8. 이 service는 내부 로직 ( ex)중복 검사, 비밀번호가 특수문자 포함 검사)을 구현하고 repository.save(user)로 넘겨준다
        9. repository 에서는 이걸 em.persist(user) 실제 디비에 저장되기 전 단계인 영속성 컨텍스트에 저장시켜준다

        그래서 Controller -> service -> repository -> (영속성) -> 실제 디비의 순서이고
        controller service repository는 각각 다음 단계로 넘길때 domain을 인자로 넘기므로
        많이 봤던 그 아키텍쳐 사진이 나오는것이다.

                            --- 스프링 mvc 아키텍쳐 ---

               Controller -> service -> repository -> (영속성) -> 실제 디비
                   ↘            ↓           ↙
                              domain(entity들의 집합)


         */

        userService.join(user);

        log.info("signup success");
        return "redirect:/";
    }
}
