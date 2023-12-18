package africa.xLogistics.dtos.requests;

import africa.xLogistics.data.models.Address;
import lombok.*;

@Data
public class RegisterRequest {

    private String username;
    private String password;
    private String emailAddress;
    private String phoneNumber;
    private Address address;
}
