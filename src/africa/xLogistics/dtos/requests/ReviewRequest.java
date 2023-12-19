package africa.xLogistics.dtos.requests;

import lombok.Data;

@Data
public class ReviewRequest {
    private String userId;
    private String bookingId;
    private String comment;
}
