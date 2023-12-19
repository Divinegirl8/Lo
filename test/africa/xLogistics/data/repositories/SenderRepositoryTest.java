package africa.xLogistics.data.repositories;

import africa.xLogistics.data.models.Booking;
import africa.xLogistics.data.models.Sender;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class SenderRepositoryTest {
    @Autowired
    SenderRepository senderRepository;

    @Test
    void save_One_Count_Is_One(){
        Sender sender = new Sender();
        senderRepository.save(sender);
        assertEquals(1,senderRepository.count());
    }
}
