package africa.xLogistics.data.models;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class Receiver {
    private String name;
    private  String phoneNumber;
    private String emailAddress;
    private Address homeAddress;
}
