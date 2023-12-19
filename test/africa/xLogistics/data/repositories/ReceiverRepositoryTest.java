package africa.xLogistics.data.repositories;

import africa.xLogistics.data.models.Booking;
import africa.xLogistics.data.models.Receiver;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ReceiverRepositoryTest {
    @Autowired
    ReceiverRepository receiverRepository;
    @Test
    void save_One_Count_Is_One(){
        Receiver receiver = new Receiver();
        receiverRepository.save(receiver);
        assertEquals(1,receiverRepository.count());
    }
}
