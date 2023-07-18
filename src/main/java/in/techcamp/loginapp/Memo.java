package in.techcamp.loginapp;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Memo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String text;

    @ManyToOne
    private Account account;
}
