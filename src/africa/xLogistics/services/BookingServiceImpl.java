package africa.xLogistics.services;

import africa.xLogistics.data.models.*;
import africa.xLogistics.data.repositories.BookingRepository;
import africa.xLogistics.data.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    BookingRepository bookingRepository;

    @Override
    public Booking book( Sender senderInfo, Receiver receiverInfo, String userId,
                        String parcelName, String bookingId, LocalDateTime dateTime) {



        Booking booking = new Booking();
        booking.setSenderInfo(senderInfo);
        booking.setReceiverInfo(receiverInfo);
        booking.setBookingId(bookingId);
        booking.setParcelName(parcelName);
        booking.setDateTime(dateTime);
        booking.setUserId(userId);

        booking.setBooked(true);

        bookingRepository.save(booking);
        return booking;
    }


    @Override
    public List<Booking> findAll() {
        return bookingRepository.findAll();
    }
}
