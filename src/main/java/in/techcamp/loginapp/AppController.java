package in.techcamp.loginapp;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.constraints.Past;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private CommentRepository commentRepository;


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

    @PostMapping("/account/{accountId}/memos/{memoId}/delete")
    public String deleteMemo(Authentication authentication,
                           @PathVariable("accountId") Integer accountId,
                           @PathVariable("memoId") Integer memoId)
                          {
        // 現在認証されているユーザー名を取得
        String username = authentication.getName();

        // 該当のメモとアカウントを取得
        Memo memo = memoRepository.findById(memoId).orElseThrow(() -> new EntityNotFoundException("Memo not found: " + memoId)); // Optionalが持つ実際の値を取得、値が存在しない場合には指定した例外をスローする
        Account account = accountRepository.findById(accountId).orElseThrow(() -> new EntityNotFoundException("Memo not found: " + accountId));

        // ユーザーチェック
        if (account.getUsername().equals(username)) {
            // メモを削除

            memoRepository.deleteById(memoId);
        }
        else {
            // エラーメッセージを設定したり、エラーページにリダイレクトしたりします。
        }

        // 更新後のページにリダイレクト

        return "redirect:/memos";
    }

    @GetMapping("/memo/{memoId}")
    public String showMemoDetail(@PathVariable("memoId") Integer memoId, @ModelAttribute("comment")Comment comment, Model model) {
        Memo memo = memoRepository.findById(memoId).orElseThrow(() -> new EntityNotFoundException("Memo not found: " + memoId));
        List<Comment> comments = commentRepository.findByMemo_id(memoId);
        model.addAttribute("memo", memo);
        model.addAttribute("comments",comments);
        return "memoDetail";
    }

    @PostMapping("/memos/{memoId}/message")
    public String postComment(Authentication authentication,
                              Comment comment,
                              @PathVariable("memoId") Integer memoId
                              ) {
        String username = authentication.getName();
        Account account = accountRepository.findByUsername(username);

        Memo memo = memoRepository.findById(memoId).orElseThrow(() -> new EntityNotFoundException("Memo not found: " + memoId));

        comment.setAccount(account);
        comment.setMemo(memo);

        commentRepository.save(comment);

        return "redirect:/memo/" + memoId;
    }


    @GetMapping("memo/{memoId}/comment/{commentId}/edit")
    public String commentEdit(@PathVariable("memoId")Integer memoId,
                              @PathVariable("commentId")Integer commentId,
                              Model model) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new EntityNotFoundException("Memo not found: " + commentId));
        Memo memo = memoRepository.findById(memoId).orElseThrow(() -> new EntityNotFoundException("Memo not found: " + memoId));
        model.addAttribute(comment);
        model.addAttribute(memo);

        return "comment/memoEdit";
    }

    @PostMapping("memo/{memoId}/comment/{commentId}/update")
    public String commentUpdate(@PathVariable("memoId")Integer memoId,
                                @PathVariable("commentId")Integer commentId,
                                @RequestParam("message") String newMessage,
                                Authentication authentication) {
        String username = authentication.getName();
        Account account = accountRepository.findByUsername(username);
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new EntityNotFoundException("Memo not found: " + commentId));

        if (account.getUsername().equals(username)) {
            // メモを更新
            comment.setMessage(newMessage);
            commentRepository.save(comment);
        }
        else {
            // エラーメッセージを設定したり、エラーページにリダイレクトしたりします。
        }

        // 更新後のページにリダイレクト

        return "redirect:/memo/" + memoId;
    }

    @PostMapping("memo/{memoId}/comment/{commentId}/delete")
    public String commentDelete(@PathVariable("memoId")Integer memoId,
                                @PathVariable("commentId")Integer commentId,
                                Authentication authentication){
        String username = authentication.getName();
        Account account = accountRepository.findByUsername(username);

        if (account.getUsername().equals(username)) {

            commentRepository.deleteById(commentId);
        }
        else {
            // エラーメッセージを設定したり、エラーページにリダイレクトしたりします。
        }

        return "redirect:/memo/" + memoId;
    }





}
