package jpabook.jpashop;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {

    @GetMapping("hello") // hello url로 오면 HelloController 호출
    public String hello(Model model) { // model에 data를 실어서 controller를 통해 view로 넘김
        model.addAttribute("data", "hello!!"); // 키 값
        return "hello"; // return은 화면 이름, resources > templates > hello.html로 이동 - ${data}
    }
}
