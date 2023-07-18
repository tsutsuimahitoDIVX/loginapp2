package in.techcamp.loginapp;

import lombok.Data;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Integer> {
    Account findByUsername(String username);

//    uniqueバリデーション実装検討
//    Boolean existsByUsername(String username);
}
