package in.techcamp.loginapp;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "message")
    private String message;

    @ManyToOne
    private Account account;

    @ManyToOne
    private Memo memo;
}
