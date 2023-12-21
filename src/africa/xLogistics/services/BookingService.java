package africa.xLogistics.services;

import africa.xLogistics.data.models.Booking;
import africa.xLogistics.data.models.Receiver;
import africa.xLogistics.data.models.Sender;
import africa.xLogistics.dtos.requests.BookingRequest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface BookingService {
    Booking book(Sender senderInfo, Receiver receiverInfo, String userId, String parcelName, String bookingId, LocalDateTime dateTime);
    List<Booking> findAll();
}
