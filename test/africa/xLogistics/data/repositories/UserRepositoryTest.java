package africa.xLogistics.data.repositories;

import africa.xLogistics.data.models.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class UserRepositoryTest {
    @Autowired
    UserRepository userRepository;

    @Test
    void Saved_One_count_Is_One(){
        User user = new User();
        userRepository.save(user);
        assertEquals(1,userRepository.count());
    }
}
