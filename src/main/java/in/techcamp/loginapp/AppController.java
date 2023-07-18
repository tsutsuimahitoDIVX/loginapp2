package in.techcamp.loginapp;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class AppController {

    @Autowired
    private MemoService memoService;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private MemoRepository memoRepository;


    @GetMapping("/")
    public String showIndex(){
        return "index";
    }

    @GetMapping("/registerPage")
    public String showRegister(@ModelAttribute("account") Account account) {
        return "register";
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

    @GetMapping("/memos")
        public String getMemos(Model model) {
            List<Memo> memos = memoRepository.findAll();
            model.addAttribute("memos", memos);
            return "memos";
    }

    @GetMapping("/account/{accountId}/memos")
    public String getUserMemos(@PathVariable("accountId") Integer accountId, Model model) {
        Account account = accountRepository.findById(accountId).orElse(null);
        List<Memo> memos = memoRepository.findByAccount_Id(accountId);
        model.addAttribute("account", account);
        model.addAttribute("memos", memos);
        return "account/memos";
    }
}
