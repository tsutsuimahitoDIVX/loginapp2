package in.techcamp.loginapp;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AccountInput {

    @NotBlank(message = "ユーザー名は必須です。")
    private String username;

    @Size(min = 4, max = 10, message = "パスワードは4文字以上、10文字以下で設定してください。")
    private String password;
}
