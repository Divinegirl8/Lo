package africa.xLogistics.data.models;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
public class User {
    @Id
    private String id;
    private String username;
    private String password;
    private String emailAddress;
    private String phoneNumber;
    private Wallet wallet;
    private Address address;
    private boolean isLoggedIn;


}
