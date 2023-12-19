package africa.xLogistics.data.repositories;


import africa.xLogistics.data.models.Booking;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BookingRepository  extends MongoRepository<Booking,String> {
    Booking findBookingByBookingId(String id);
}
