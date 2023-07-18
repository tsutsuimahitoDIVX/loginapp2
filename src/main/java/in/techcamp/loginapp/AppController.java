package in.techcamp.loginapp;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AppController {

    @Autowired
    private MemoService memoService;

    @Autowired
    private AccountRepository accountRepository;


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

    @GetMapping("/memo")
    public String getMemo(@ModelAttribute("memo") Memo memo){
        return"memo";
    }

    @PostMapping("/memoPost")
    public String postMemo(@ModelAttribute("memo")Memo memo, BindingResult result, Authentication authentication){
        User user = (User) authentication.getPrincipal();
        String username = user.getUsername();
        Account account = accountRepository.findByUsername(username);
        memo.setAccount(account);
        memoService.createNewMemo(memo);
        return "redirect:/";
    }
}
