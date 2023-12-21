package africa.xLogistics.dtos.requests;

import africa.xLogistics.data.models.Receiver;
import africa.xLogistics.data.models.Sender;
import africa.xLogistics.data.models.User;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class BookingRequest {
    private BigDecimal bookingCost = BigDecimal.ZERO;
    private Sender senderInfo;
    private Receiver receiverInfo;
    private String userId;
    private String parcelName;
}
