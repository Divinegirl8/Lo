package africa.xLogistics.services;

import africa.xLogistics.data.models.Address;
import africa.xLogistics.data.repositories.UserRepository;
import africa.xLogistics.dtos.requests.RegisterRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LogisticsServiceImplTest {
    @Autowired
    LogisticsServiceImpl logisticsService;
    @Autowired
    UserRepository userRepository;



    @AfterEach
    void cleanup() {
        userRepository.deleteAll();

    }

    @Test
    void testThatMyUserCanRegister() {
        RegisterRequest registerRequest = checkRequest("username","password","emailAddress","phoneNumber",checkAddress("123","magodo","Lagos","Nigeria","Benita street"));
        System.out.println(logisticsService.register(registerRequest));
        assertEquals(1, userRepository.count());
    }


    private static RegisterRequest checkRequest(String username, String password, String emailAddress, String phoneNumber, Address homeAddress){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername(username);
        registerRequest.setPassword(password);
        registerRequest.setEmailAddress(emailAddress);
        registerRequest.setPhoneNumber(phoneNumber);
        registerRequest.setAddress(homeAddress);
        return registerRequest;
    }

    private static Address checkAddress(String postalCode,String city, String state,String country, String street){
        Address address = new Address();
        address.setPostalCode(postalCode);
        address.setCity(city);
        address.setState(state);
        address.setCountry(country);
        address.setStreetName(street);
        return address;
    }

}