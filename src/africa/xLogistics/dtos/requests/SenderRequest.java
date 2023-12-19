package africa.xLogistics.dtos.requests;

import africa.xLogistics.data.models.Address;
import lombok.Data;

@Data
public class SenderRequest {
    private String name;
    private String phoneNumber;
    private Address address;
    private String emailAddress;
}
