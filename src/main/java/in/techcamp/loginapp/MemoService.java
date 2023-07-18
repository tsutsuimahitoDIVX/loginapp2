package in.techcamp.loginapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemoService {

    @Autowired
    private MemoRepository memoRepository;

    public void createNewMemo(Memo memo){
        memoRepository.save(memo);
    }
}
