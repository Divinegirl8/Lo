package africa.xLogistics.services;

import africa.xLogistics.data.models.Booking;
import africa.xLogistics.dtos.requests.BookingRequest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface BookingService {
    Booking book( BigDecimal bookingCost, String senderId,String receiverId,String userId,String parcelName,String bookingId, LocalDateTime dateTime);
    List<Booking> findAll();
}
