package africa.xLogistics.utils;

import africa.xLogistics.data.models.*;
import africa.xLogistics.dtos.requests.*;

import java.time.LocalDateTime;

public class Mapper {
    public static User registerMap(String userId,RegisterRequest registerRequest){
        User newUser = new User();
        newUser.setId(userId);
        newUser.setUsername(registerRequest.getUsername());
        newUser.setPassword(registerRequest.getPassword());
        newUser.setEmailAddress(registerRequest.getEmailAddress());
        newUser.setPhoneNumber(registerRequest.getPhoneNumber());

        Address address = registerRequest.getAddress();

        if (address != null){
            newUser.setAddress(address);
        }
        return newUser;
    }


}
