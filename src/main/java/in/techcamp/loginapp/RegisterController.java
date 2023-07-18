package in.techcamp.loginapp;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RegisterController {
    @Autowired
    private UserService userService;

    @PostMapping ("/register")
    public String register(@ModelAttribute("account") @Valid AccountInput account, BindingResult result){
        if (result.hasErrors()){
            return"register";
        }

        userService.registerNewUser(account);  // UserServiceのregisterNewUserメソッドを呼び出す
        return "redirect:/";
    }
}