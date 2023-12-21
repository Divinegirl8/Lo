package africa.xLogistics.data.models;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class Sender {
    private String name;
    private String phoneNumber;
    private String emailAddress;
    private Address address;
}
