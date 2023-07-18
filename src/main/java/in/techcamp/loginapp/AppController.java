package in.techcamp.loginapp;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AppController {
    @GetMapping("/")
    public String showIndex(){
        return "index";
    }

    @GetMapping("/registerPage")
    public String showRegister(@ModelAttribute("account") Account account) {
        return "register";
    }

    @GetMapping("/session")
    public String getSessionId(HttpServletRequest request) {
        return request.getSession().getId();
    }
}
