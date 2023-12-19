package africa.xLogistics.data.repositories;

import africa.xLogistics.data.models.Booking;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class BookingRepositoryTest {
    @Autowired
    BookingRepository bookingRepository;

    @Test void save_One_Count_Is_One(){
        Booking booking = new Booking();
        bookingRepository.save(booking);
        assertEquals(1,bookingRepository.count());
    }
}
