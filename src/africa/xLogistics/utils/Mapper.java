package africa.xLogistics.utils;

import africa.xLogistics.data.models.Address;
import africa.xLogistics.data.models.User;
import africa.xLogistics.dtos.requests.RegisterRequest;

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
