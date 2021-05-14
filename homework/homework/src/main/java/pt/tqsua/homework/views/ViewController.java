package pt.tqsua.homework.views;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class ViewController {

    @Autowired
    private ServletWebServerApplicationContext webServerAppCtxt;

    @RequestMapping("/")
    public String homepage(Model model) {
        model.addAttribute("runningPort", webServerAppCtxt.getWebServer().getPort());
        System.out.println("/");;
        return "index";
    }

}
