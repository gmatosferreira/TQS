package pt.tqsua.homework.views;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    private static final Logger log = LoggerFactory.getLogger(ViewController.class);

    @Autowired
    private ServletWebServerApplicationContext webServerAppCtxt;

    @GetMapping
    public String homepage(Model model) {
        model.addAttribute("runningPort", webServerAppCtxt.getWebServer().getPort());
        log.debug("/");
        return "index";
    }

}
