package africa.xLogistics.services;

import africa.xLogistics.data.models.Address;
import africa.xLogistics.data.models.Booking;
import africa.xLogistics.data.models.Customer;
import africa.xLogistics.data.models.User;
import africa.xLogistics.data.repositories.BookingRepository;
import africa.xLogistics.data.repositories.UserRepository;
import africa.xLogistics.dtos.requests.AddMoneyToWalletRequest;
import africa.xLogistics.dtos.requests.BookingRequest;
import africa.xLogistics.dtos.requests.LoginRequest;
import africa.xLogistics.dtos.requests.RegisterRequest;
import africa.xLogistics.exceptions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LogisticsServiceImplTest {
    @Autowired
    LogisticsServiceImpl logisticsService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    BookingRepository bookingRepository;



    @AfterEach
    void cleanup() {
        userRepository.deleteAll();
        bookingRepository.deleteAll();;

    }

    @Test
    void testThatMyUserCanRegister() {
        RegisterRequest registerRequest = checkRequest("username","password","emailAddress","phoneNumber",checkAddress("123","magodo","Lagos","Nigeria","Benita street"));
        logisticsService.register(registerRequest);
        assertEquals(1, userRepository.count());
    }

    @Test
    void test_That_If_My_User_Register_And_Register_Again_With_The_Same_Username_Exception_Will_Be_Thrown() {
        RegisterRequest registerRequest = checkRequest("username","password","emailAddress","phoneNumber",checkAddress("123","magodo","Lagos","Nigeria","Benita street"));
        logisticsService.register(registerRequest);
        assertEquals(1, userRepository.count());
        assertThrows(UserExistException.class, () -> logisticsService.register(registerRequest));
    }

    @Test
    public void registerUser_loginWithRightDetails_foundUserIsUnlockedTest(){
        RegisterRequest registerRequest = checkRequest("username","password","emailAddress","phoneNumber",checkAddress("123","magodo","Lagos","Nigeria","Benita street"));
        LoginRequest loginRequest = new LoginRequest();
        logisticsService.register(registerRequest);


        boolean isLogin = logisticsService.findAccountBelongingTo("username").isLoggedIn();
        assertFalse(isLogin);

        loginRequest.setPassword("password");
        loginRequest.setUsername("username");
        logisticsService.login(loginRequest);
        isLogin = logisticsService.findAccountBelongingTo("username").isLoggedIn();
        assertTrue(isLogin);
    }

    @Test
    public void registerUser_loginWithWrongUsername_Throws_An_Exception(){
        RegisterRequest registerRequest = checkRequest("username","password","emailAddress","phoneNumber",checkAddress("123","magodo","Lagos","Nigeria","Benita street"));
        LoginRequest loginRequest = new LoginRequest();
        logisticsService.register(registerRequest);


        loginRequest.setPassword("password");
        loginRequest.setUsername("firstname name");

        assertThrows(InvalidDetailsException.class,()-> logisticsService.login(loginRequest));
    }


    @Test
    public void registerUser_loginWithWrongPassword_Throws_An_Exception(){
        RegisterRequest registerRequest = checkRequest("username","password","emailAddress","phoneNumber",checkAddress("123","magodo","Lagos","Nigeria","Benita street"));
        LoginRequest loginRequest = new LoginRequest();
        logisticsService.register(registerRequest);


        loginRequest.setPassword("pass");
        loginRequest.setUsername("firstname lastname");

        assertThrows(InvalidDetailsException.class,()-> logisticsService.login(loginRequest));
    }

    @Test
    public void registerUser_loginWithWrongPassword_AndUsername_Throws_An_Exception(){
        RegisterRequest registerRequest = checkRequest("username","password","emailAddress","phoneNumber",checkAddress("123","magodo","Lagos","Nigeria","Benita street"));
        LoginRequest loginRequest = new LoginRequest();
        logisticsService.register(registerRequest);

        loginRequest.setPassword("pin");
        loginRequest.setUsername("username");

        assertThrows(InvalidDetailsException.class,()-> logisticsService.login(loginRequest));
    }

    @Test
    void registerUser_loginWithCorrectDetails_Add_Money_To_Wallet() {
        RegisterRequest registerRequest = checkRequest("username", "password", "emailAddress", "phoneNumber", checkAddress("123", "magodo", "Lagos", "Nigeria", "Benita street"));
        logisticsService.register(registerRequest);

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setPassword("password");
        loginRequest.setUsername("username");
        logisticsService.login(loginRequest);

        User user = logisticsService.findAccountBelongingTo("username");

        AddMoneyToWalletRequest addMoneyToWalletRequest = new AddMoneyToWalletRequest();
        addMoneyToWalletRequest.setUserId(user.getId());
        addMoneyToWalletRequest.setAmount(BigDecimal.valueOf(1000));

        logisticsService.addMoneyToWallet(addMoneyToWalletRequest);

        assertEquals(BigDecimal.valueOf(1000),userRepository.findUserById(user.getId()).getWallet().getBalance());
    }

    @Test
    void registerUser_loginWithCorrectDetails_Add_Money_That_Is_Less_Than_One_To_Wallet_Throws_An_Exception() {
        RegisterRequest registerRequest = checkRequest("username", "password", "emailAddress", "phoneNumber", checkAddress("123", "magodo", "Lagos", "Nigeria", "Benita street"));
        logisticsService.register(registerRequest);

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setPassword("password");
        loginRequest.setUsername("username");
        logisticsService.login(loginRequest);

        User user = logisticsService.findAccountBelongingTo("username");

        AddMoneyToWalletRequest addMoneyToWalletRequest = new AddMoneyToWalletRequest();
        addMoneyToWalletRequest.setUserId(user.getId());
        addMoneyToWalletRequest.setAmount(BigDecimal.valueOf(-1000));


        assertThrows(InvalidDepositAmountException.class, () -> logisticsService.addMoneyToWallet(addMoneyToWalletRequest));

    }



    @Test
    void registerUser_loginWithCorrectDetails_Add_Money_To_Wallet_Deduct_Money_From_Wallet() {
        RegisterRequest registerRequest = checkRequest("username", "password", "emailAddress", "phoneNumber", checkAddress("123", "magodo", "Lagos", "Nigeria", "Benita street"));
        logisticsService.register(registerRequest);

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setPassword("password");
        loginRequest.setUsername("username");
        logisticsService.login(loginRequest);

        User user = logisticsService.findAccountBelongingTo("username");

        AddMoneyToWalletRequest addMoneyToWalletRequest = new AddMoneyToWalletRequest();
        addMoneyToWalletRequest.setUserId(user.getId());
        addMoneyToWalletRequest.setAmount(BigDecimal.valueOf(1000));


        logisticsService.addMoneyToWallet(addMoneyToWalletRequest);
        logisticsService.deductMoneyFromWallet(user.getId(),BigDecimal.valueOf(900));


        assertEquals(BigDecimal.valueOf(100),userRepository.findUserById(user.getId()).getWallet().getBalance());
    }

    @Test
    void registerUser_loginWithCorrectDetails_Add_Money_To_Wallet_Deduct_Money_From_Wallet_And_Again_Deduct_Money_More_Than_Balance_Throws_Error() {
        RegisterRequest registerRequest = checkRequest("username", "password", "emailAddress", "phoneNumber", checkAddress("123", "magodo", "Lagos", "Nigeria", "Benita street"));
        logisticsService.register(registerRequest);

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setPassword("password");
        loginRequest.setUsername("username");
        logisticsService.login(loginRequest);

        User user = logisticsService.findAccountBelongingTo("username");

        AddMoneyToWalletRequest addMoneyToWalletRequest = new AddMoneyToWalletRequest();
        addMoneyToWalletRequest.setUserId(user.getId());
        addMoneyToWalletRequest.setAmount(BigDecimal.valueOf(1000));


        logisticsService.addMoneyToWallet(addMoneyToWalletRequest);
        logisticsService.deductMoneyFromWallet(user.getId(),BigDecimal.valueOf(900));

        assertThrows(InsufficientFundsError.class,()->logisticsService.deductMoneyFromWallet(user.getId(),BigDecimal.valueOf(1000)));
    }

    @Test
    void registerUser_loginWithCorrectDetails_Add_Money_To_Wallet_Deduct_Money_From_Wallet_That_Is_Less_Than_One() {
        RegisterRequest registerRequest = checkRequest("username", "password", "emailAddress", "phoneNumber", checkAddress("123", "magodo", "Lagos", "Nigeria", "Benita street"));
        logisticsService.register(registerRequest);

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setPassword("password");
        loginRequest.setUsername("username");
        logisticsService.login(loginRequest);

        User user = logisticsService.findAccountBelongingTo("username");

        AddMoneyToWalletRequest addMoneyToWalletRequest = new AddMoneyToWalletRequest();
        addMoneyToWalletRequest.setUserId(user.getId());
        addMoneyToWalletRequest.setAmount(BigDecimal.valueOf(1000));


        logisticsService.addMoneyToWallet(addMoneyToWalletRequest);



        assertThrows(LowAmountError.class,()->  logisticsService.deductMoneyFromWallet(user.getId(),BigDecimal.valueOf(0)));
    }


    @Test void registerUser_loginWithCorrectDetails_bookService(){
        RegisterRequest registerRequest = checkRequest("username","password","emailAddress","phoneNumber",checkAddress("123","magodo","Lagos","Nigeria","Benita street"));
        logisticsService.register(registerRequest);

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setPassword("password");
        loginRequest.setUsername("username");
        logisticsService.login(loginRequest);

        User user = new User();
        user.setPhoneNumber("090");
        user.setId(logisticsService.findAccountBelongingTo("username").getId());
        user.setUsername("divine");
        user.setAddress(checkAddress("123","magodo","Lagos","Nigeria","Benita street"));

        Customer customer = new Customer();
        customer.setEmail("my@gmail.com");
        customer.setPhoneNumber("090");
        customer.setName("seer");
        customer.setHomeAddress(checkAddress("67","ikeja","lagos","Nigeria","7 tunde"));

        AddMoneyToWalletRequest addMoneyToWalletRequest = new AddMoneyToWalletRequest();
        addMoneyToWalletRequest.setUserId(user.getId());
        addMoneyToWalletRequest.setAmount(BigDecimal.valueOf(1000));

        logisticsService.addMoneyToWallet(addMoneyToWalletRequest);
        BookingRequest bookingRequest = new BookingRequest();
        bookingRequest.setBookingCost(BigDecimal.valueOf(1000));
        bookingRequest.setParcelName("dress");
        bookingRequest.setSenderInfo(user);
        bookingRequest.setReceiverInfo(customer);

        logisticsService.bookService(bookingRequest);

        assertEquals(1,bookingRepository.count());

    }


    @Test void registerUser_loginWithCorrectDetails_bookService_Twice(){
        RegisterRequest registerRequest = checkRequest("username","password","emailAddress","phoneNumber",checkAddress("123","magodo","Lagos","Nigeria","Benita street"));
        logisticsService.register(registerRequest);

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setPassword("password");
        loginRequest.setUsername("username");
        logisticsService.login(loginRequest);

        User user = new User();
        user.setPhoneNumber("090");
        user.setId(logisticsService.findAccountBelongingTo("username").getId());
        user.setUsername("divine");
        user.setAddress(checkAddress("123","magodo","Lagos","Nigeria","Benita street"));

        Customer customer = new Customer();
        customer.setEmail("my@gmail.com");
        customer.setPhoneNumber("090");
        customer.setName("seer");
        customer.setHomeAddress(checkAddress("67","ikeja","lagos","Nigeria","7 tunde"));

        AddMoneyToWalletRequest addMoneyToWalletRequest = new AddMoneyToWalletRequest();
        addMoneyToWalletRequest.setUserId(user.getId());
        addMoneyToWalletRequest.setAmount(BigDecimal.valueOf(10000));


        logisticsService.addMoneyToWallet(addMoneyToWalletRequest);
        BookingRequest bookingRequest = new BookingRequest();
        bookingRequest.setBookingCost(BigDecimal.valueOf(1000));
        bookingRequest.setParcelName("dress");
        bookingRequest.setSenderInfo(user);
        bookingRequest.setReceiverInfo(customer);

        Booking booking  = logisticsService.bookService(bookingRequest);
        logisticsService.bookService(bookingRequest);

        assertEquals(2,bookingRepository.count());
        assertTrue(booking.isBooked());


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