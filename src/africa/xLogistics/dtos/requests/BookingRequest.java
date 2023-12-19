package africa.xLogistics.dtos.requests;

import africa.xLogistics.data.models.User;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class BookingRequest {
    private BigDecimal bookingCost = BigDecimal.ZERO;
    private String senderId;
    private String receiverId;
    private String userId;
    private String parcelName;
}
