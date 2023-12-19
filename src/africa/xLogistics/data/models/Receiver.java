package africa.xLogistics.data.models;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class Receiver {
    @Id
    private String id;
    private String name;
    private  String phoneNumber;
    private String email;
    private Address homeAddress;

}
