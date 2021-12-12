package hello.hellospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    //GetMapping의 / 는 ? : 도메인 첫 번째 localhost8090으로 들어오면 아래 메서드가 호출된다.
    //그리고 /template/home.html이 호출됨
    @GetMapping("/")
    public String home() {
        return "home";
    }
}
