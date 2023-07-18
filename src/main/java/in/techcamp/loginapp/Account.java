package in.techcamp.loginapp;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.apache.ibatis.annotations.One;

import java.util.List;

@Entity
@Data
@Table(name = "account")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "username")
    @NotBlank(message = "ユーザー名は必須です")
    private String username;

    @Column(name  = "password")
    @NotBlank(message = "パスワードは必須です")
    private String password;

    @OneToMany(mappedBy = "account")
    private List<Memo> memos;
}
