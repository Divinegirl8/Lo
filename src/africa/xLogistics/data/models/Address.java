package africa.xLogistics.data.models;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class Address {
    @Id
    private String postalCode;
    private String streetName;
    private String city;
    private String country;
    private String state;
}
