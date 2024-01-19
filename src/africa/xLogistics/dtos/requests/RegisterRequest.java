package africa.xLogistics.dtos.requests;

import africa.xLogistics.data.models.Address;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
public class RegisterRequest {
    @NotBlank(message = "field must not be blank")
    private String username;
    @NotBlank(message = "field must not be blank")
    private String password;
    @NotBlank(message = "field must not be blank")
    private String emailAddress;
    @NotBlank(message = "field must not be blank")
    private String phoneNumber;
    @NotBlank(message = "field must not be blank")
    private Address address;
}
