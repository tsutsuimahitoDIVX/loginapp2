package in.techcamp.loginapp;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;// PasswordEncoderを@Autowiredします。

    public void registerNewUser(@Valid AccountInput accountInput) {
        String password = accountInput.getPassword();
        String encodedPassword = passwordEncoder.encode(password); // パスワードをハッシュ化

        Account account = new Account();
        account.setUsername(accountInput.getUsername());
        account.setPassword(encodedPassword);// ハッシュ化したパスワードをアカウントにセットします。

//        To Do uniqueバリデーションをつけるか否か検討
//        if (accountRepository.existsByUsername(account.getUsername())){
//            throw new RuntimeException("登録済みのユーザ名です。");
//        }

        accountRepository.save(account);
    }
}