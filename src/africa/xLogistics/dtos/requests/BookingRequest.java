package africa.xLogistics.dtos.requests;

import africa.xLogistics.data.models.Customer;
import africa.xLogistics.data.models.User;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class BookingRequest {
    private BigDecimal bookingCost = BigDecimal.ZERO;
    private User senderInfo;
    private Customer receiverInfo;
    private String parcelName;
}
