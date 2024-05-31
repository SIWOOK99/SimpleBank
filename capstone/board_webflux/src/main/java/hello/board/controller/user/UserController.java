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
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/signup")
    public String signupForm(@ModelAttribute("userForm") UserSaveForm userForm) {
        log.info("signupForm");
        return "users/signupForm";
    }

    @PostMapping("/signup")
    public String signup(@Valid @ModelAttribute("userForm") UserSaveForm userForm, BindingResult bindingResult) {

        log.info("signup");

        if (bindingResult.hasErrors()) {
            log.info("errors = {}", bindingResult);
            return "users/signupForm";
        }


        User user = User.builder()
                .loginId(userForm.getLoginId())
                .password(userForm.getPassword())
                .name(userForm.getName())
                .age(userForm.getAge()).build();

        userService.join(user);

        log.info("signup success");
        return "redirect:/";
    }
}
