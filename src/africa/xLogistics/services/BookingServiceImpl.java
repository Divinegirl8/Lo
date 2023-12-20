package africa.xLogistics.services;

import africa.xLogistics.data.models.*;
import africa.xLogistics.data.repositories.BookingRepository;
import africa.xLogistics.data.repositories.ReceiverRepository;
import africa.xLogistics.data.repositories.SenderRepository;
import africa.xLogistics.data.repositories.UserRepository;
import africa.xLogistics.dtos.requests.BookingRequest;
import africa.xLogistics.exceptions.*;
import africa.xLogistics.services.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ReceiverRepository receiverRepository;

    @Autowired
    SenderRepository senderRepository;

    @Autowired
    BookingRepository bookingRepository;

    @Override
    public Booking book(BigDecimal bookingCost, String senderId, String receiverId, String userId,
                        String parcelName, String bookingId, LocalDateTime dateTime) {


        Booking booking = new Booking();
        booking.setBookingId(bookingId);
        booking.setParcelName(parcelName);
        booking.setDateTime(dateTime);

        Sender sender = new Sender();
        sender.setId(senderId);

        booking.setSenderInfo(sender);

        Receiver receiver = new Receiver();
        receiver.setId(receiverId);
        booking.setReceiverInfo(receiver);

        booking.setBooked(true);

        bookingRepository.save(booking);
        return booking;
    }


    @Override
    public List<Booking> findAll() {
        return bookingRepository.findAll();
    }
}
