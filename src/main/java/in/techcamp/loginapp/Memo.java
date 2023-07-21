package in.techcamp.loginapp;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Memo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String text;

    @ManyToOne
    private Account account;

    @OneToMany(mappedBy = "memo",cascade = CascadeType.REMOVE)
    private List<Comment> comments;
}
