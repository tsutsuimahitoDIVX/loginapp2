package in.techcamp.loginapp;

import jakarta.persistence.EntityNotFoundException;
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
import java.util.Optional;

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

//    @PostMapping ("/account/{accountId}/memos/{memoId}/edit")
//    public String editMemo(Authentication authentication,
//                           @PathVariable("accountId") Integer accountId,
//                           @PathVariable("memoId") Integer memoId,
//                           @RequestParam("text") String newText) {
//        // 現在認証されているユーザー名を取得
//        String username = authentication.getName();
//
//        // 該当のメモとアカウントを取得
//        Memo memo = memoRepository.findById(memoId);
//        Account account = accountRepository.findById(accountId);
//
//        // ユーザーチェック
//        if (account.getUsername.equals(username)) {
//            // メモを更新
//            memo.setText(newText);
//            memoRepository.save(memo);
//        }
//        else {
//            // エラーメッセージを設定したり、エラーページにリダイレクトしたりします。
//        }
//
//        // 更新後のページにリダイレクト
//        return "redirect:/account/{accountId}/memos/{memoId}";
//    }

    @GetMapping("/account/{accountId}/memos/{memoId}/edit")
    public String edit(@PathVariable Integer accountId, @PathVariable Integer memoId, Model model) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found: " + accountId));
        Memo memo = memoRepository.findById(memoId)
                .orElseThrow(() -> new IllegalArgumentException("Memo not found: " + memoId));

        model.addAttribute("account", account);
        model.addAttribute("memo", memo);

        return "memo/edit"; // メモの編集画面へ遷移
    }

@PostMapping("/account/{accountId}/memos/{memoId}/edits")
public String editMemo(Authentication authentication,
                       @PathVariable("accountId") Integer accountId,
                       @PathVariable("memoId") Integer memoId,
                       @RequestParam("text") String newText) {
    // 現在認証されているユーザー名を取得
    String username = authentication.getName();

    // 該当のメモとアカウントを取得
    Memo memo = memoRepository.findById(memoId).orElseThrow(() -> new EntityNotFoundException("Memo not found: " + memoId)); // Optionalが持つ実際の値を取得、値が存在しない場合には指定した例外をスローする
    Account account = accountRepository.findById(accountId).orElseThrow(() -> new EntityNotFoundException("Memo not found: " + accountId));

    // ユーザーチェック
    if (account.getUsername().equals(username)) {
        // メモを更新
        memo.setText(newText);
        memoRepository.save(memo);
    }
    else {
        // エラーメッセージを設定したり、エラーページにリダイレクトしたりします。
    }

    // 更新後のページにリダイレクト

    return "redirect:/memos";
}


}
