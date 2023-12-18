package africa.xLogistics.data.models;

import lombok.Data;

@Data
public class Customer {
    private String name;
    private  String phoneNumber;
    private String email;
    private Address homeAddress;

}
