package pt.tqsua.homework.views;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class ViewController {

    @RequestMapping("/")
    public String homepage() {
        System.out.println("/");;
        return "index";
    }

}
