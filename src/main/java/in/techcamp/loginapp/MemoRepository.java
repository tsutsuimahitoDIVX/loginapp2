package in.techcamp.loginapp;

import org.springframework.data.jpa.repository.JpaRepository;
public interface MemoRepository extends JpaRepository<Memo, Integer> {
}
