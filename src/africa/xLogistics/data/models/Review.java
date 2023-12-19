package africa.xLogistics.data.models;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class Review {
    @Id
    private String reviewId;
    private String userId;
    private String bookingId;
    private String comment;
}
