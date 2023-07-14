package in.techcamp.loginapp;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AppController {
    @GetMapping("/")
    public String showIndex(){
        return "index";
    }

//    @GetMapping("/login")
//    public String getLogin() {
//        return "login";
//    }
}
