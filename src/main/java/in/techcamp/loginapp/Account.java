package in.techcamp.loginapp;

import jakarta.persistence.*;
import lombok.Data;
@Entity
@Data
@Table(name = "account")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Column(name = "user_name")
    private String userName;

    @Column(name  = "password")
    private String password;
}
