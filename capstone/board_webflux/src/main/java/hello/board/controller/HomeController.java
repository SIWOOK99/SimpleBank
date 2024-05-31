package hello.board.controller;

import hello.board.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
@Slf4j
public class HomeController {

//    @GetMapping("/")
//    public String home() {
//        log.info("home controller");
//        return "home";
//    }

    @GetMapping("/")
    public String loginHome(@SessionAttribute(name = SessionConst.LOGIN_USER, required = false) User loginUser, Model model) {

        log.info("home controller");

        if (loginUser == null) {
            log.info("main home");
            return "home";
        }

        log.info("login home");
        model.addAttribute("user", loginUser);
        return "loginHome";
    }
}
