package africa.xLogistics.services;

import africa.xLogistics.data.models.*;
import africa.xLogistics.data.repositories.*;
import africa.xLogistics.dtos.requests.*;
import africa.xLogistics.exceptions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    ReviewRepository reviewRepository;



    @AfterEach
    void cleanup() {
        userRepository.deleteAll();
        bookingRepository.deleteAll();
        reviewRepository.deleteAll();

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
    void registerUser_Add_Money_To_Wallet_Without_Login_Throws_An_Exception() {
        RegisterRequest registerRequest = checkRequest("username", "password", "emailAddress", "phoneNumber", checkAddress("123", "magodo", "Lagos", "Nigeria", "Benita street"));
        logisticsService.register(registerRequest);


        User user = logisticsService.findAccountBelongingTo("username");

        AddMoneyToWalletRequest addMoneyToWalletRequest = new AddMoneyToWalletRequest();
        addMoneyToWalletRequest.setUserId(user.getId());
        addMoneyToWalletRequest.setAmount(BigDecimal.valueOf(1000));

        assertThrows(NotLoginError.class,() -> logisticsService.addMoneyToWallet(addMoneyToWalletRequest));


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





        Sender sender = new Sender();
        sender.setName("name");
        sender.setPhoneNumber("090");
        sender.setEmailAddress("iam@");
        sender.setAddress(checkAddress("123","magodo","Lagos","Nigeria","Benita street"));


        Receiver receiver = new Receiver();
        receiver.setName("name");
        receiver.setEmailAddress("1am@");
        receiver.setHomeAddress(checkAddress("123","magodo","Lagos","Nigeria","Benita street"));
        receiver.setPhoneNumber("090");

        AddMoneyToWalletRequest addMoneyToWalletRequest = new AddMoneyToWalletRequest();
        addMoneyToWalletRequest.setUserId("UID1");
        addMoneyToWalletRequest.setAmount(BigDecimal.valueOf(1000));

        logisticsService.addMoneyToWallet(addMoneyToWalletRequest);
        BookingRequest bookingRequest = new BookingRequest();
        bookingRequest.setBookingCost(BigDecimal.valueOf(1000));
        bookingRequest.setParcelName("dress");
        bookingRequest.setSenderInfo(sender);
        bookingRequest.setReceiverInfo(receiver);
        bookingRequest.setUserId("UID1");

        logisticsService.bookService(bookingRequest);

        assertEquals(1,bookingRepository.count());

    }


    @Test void registerUser_bookService_Without_Login_Throws_Error(){
        RegisterRequest registerRequest = checkRequest("username","password","emailAddress","phoneNumber",checkAddress("123","magodo","Lagos","Nigeria","Benita street"));
        logisticsService.register(registerRequest);


        User user = new User();
        user.setPhoneNumber("090");
        user.setId(logisticsService.findAccountBelongingTo("username").getId());
        user.setUsername("divine");
        user.setAddress(checkAddress("123","magodo","Lagos","Nigeria","Benita street"));

        Sender sender = new Sender();
        sender.setName("name");
        sender.setPhoneNumber("090");
        sender.setEmailAddress("iam@");
        sender.setAddress(checkAddress("123","magodo","Lagos","Nigeria","Benita street"));


        Receiver receiver = new Receiver();
        receiver.setName("name");
        receiver.setEmailAddress("1am@");
        receiver.setHomeAddress(checkAddress("123","magodo","Lagos","Nigeria","Benita street"));
        receiver.setPhoneNumber("090");




        BookingRequest bookingRequest = new BookingRequest();
        bookingRequest.setBookingCost(BigDecimal.valueOf(1000));
        bookingRequest.setParcelName("dress");
        bookingRequest.setSenderInfo(sender);
        bookingRequest.setReceiverInfo(receiver);
        bookingRequest.setUserId(user.getId());

       assertThrows(NotLoginError.class,()-> logisticsService.bookService(bookingRequest));


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

        Sender sender = new Sender();
        sender.setName("name");
        sender.setPhoneNumber("090");
        sender.setEmailAddress("iam@");
        sender.setAddress(checkAddress("123","magodo","Lagos","Nigeria","Benita street"));


        Receiver receiver = new Receiver();
        receiver.setName("name");
        receiver.setEmailAddress("1am@");
        receiver.setHomeAddress(checkAddress("123","magodo","Lagos","Nigeria","Benita street"));
        receiver.setPhoneNumber("090");




        AddMoneyToWalletRequest addMoneyToWalletRequest = new AddMoneyToWalletRequest();
        addMoneyToWalletRequest.setUserId(user.getId());
        addMoneyToWalletRequest.setAmount(BigDecimal.valueOf(10000));


        logisticsService.addMoneyToWallet(addMoneyToWalletRequest);
        BookingRequest bookingRequest = new BookingRequest();
        bookingRequest.setBookingCost(BigDecimal.valueOf(1000));
        bookingRequest.setParcelName("dress");
        bookingRequest.setSenderInfo(sender);
        bookingRequest.setReceiverInfo(receiver);
        bookingRequest.setUserId("UID1");

        Booking booking  = logisticsService.bookService(bookingRequest);
        logisticsService.bookService(bookingRequest);

        assertEquals(2,bookingRepository.count());
        assertTrue(booking.isBooked());


    }

    @Test void registerUser_loginWithCorrectDetails_bookService_Add_Review_After_Delivery(){
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

        Sender sender = new Sender();
        sender.setName("name");
        sender.setPhoneNumber("090");
        sender.setEmailAddress("iam@");
        sender.setAddress(checkAddress("123","magodo","Lagos","Nigeria","Benita street"));


        Receiver receiver = new Receiver();
        receiver.setName("name");
        receiver.setEmailAddress("1am@");
        receiver.setHomeAddress(checkAddress("123","magodo","Lagos","Nigeria","Benita street"));
        receiver.setPhoneNumber("090");



        AddMoneyToWalletRequest addMoneyToWalletRequest = new AddMoneyToWalletRequest();
        addMoneyToWalletRequest.setUserId(user.getId());
        addMoneyToWalletRequest.setAmount(BigDecimal.valueOf(1000));

        logisticsService.addMoneyToWallet(addMoneyToWalletRequest);
        BookingRequest bookingRequest = new BookingRequest();
        bookingRequest.setBookingCost(BigDecimal.valueOf(1000));
        bookingRequest.setParcelName("dress");
        bookingRequest.setSenderInfo(sender);
        bookingRequest.setReceiverInfo(receiver);
        bookingRequest.setUserId(user.getId());

        System.out.println(logisticsService.bookService(bookingRequest));

        assertEquals(1,bookingRepository.count());

        ReviewRequest reviewRequest = new ReviewRequest();
        reviewRequest.setUserId("UID1");
        reviewRequest.setBookingId("BID1");
        reviewRequest.setComment("Thanks, order received");

        logisticsService.addReview(reviewRequest);
        assertEquals(1,reviewRepository.count());

    }



    @Test void registerUser_loginWithCorrectDetails_bookService_Add_Review_With_Wrong_Userid_Throws_An_Exception(){
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

        Sender sender = new Sender();
        sender.setName("name");
        sender.setPhoneNumber("090");
        sender.setEmailAddress("iam@");
        sender.setAddress(checkAddress("123","magodo","Lagos","Nigeria","Benita street"));


        Receiver receiver = new Receiver();
        receiver.setName("name");
        receiver.setEmailAddress("1am@");
        receiver.setHomeAddress(checkAddress("123","magodo","Lagos","Nigeria","Benita street"));
        receiver.setPhoneNumber("090");



        AddMoneyToWalletRequest addMoneyToWalletRequest = new AddMoneyToWalletRequest();
        addMoneyToWalletRequest.setUserId(user.getId());
        addMoneyToWalletRequest.setAmount(BigDecimal.valueOf(1000));

        logisticsService.addMoneyToWallet(addMoneyToWalletRequest);
        BookingRequest bookingRequest = new BookingRequest();
        bookingRequest.setBookingCost(BigDecimal.valueOf(1000));
        bookingRequest.setParcelName("dress");
        bookingRequest.setSenderInfo(sender);
        bookingRequest.setReceiverInfo(receiver);
        bookingRequest.setUserId(user.getId());

        Booking booking =  logisticsService.bookService(bookingRequest);

        assertEquals(1,bookingRepository.count());

        ReviewRequest reviewRequest = new ReviewRequest();
        reviewRequest.setUserId("UID9");
        reviewRequest.setBookingId(booking.getBookingId());
        reviewRequest.setComment("Thanks, order received");

      assertThrows(UserNotFoundException.class,()->logisticsService.addReview(reviewRequest));


    }


    @Test void registerUser_loginWithCorrectDetails_bookService_Add_Review_With_Wrong_BookingId_Throws_An_Exception(){
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
        Sender sender = new Sender();
        sender.setName("name");
        sender.setPhoneNumber("090");
        sender.setEmailAddress("iam@");
        sender.setAddress(checkAddress("123","magodo","Lagos","Nigeria","Benita street"));


        Receiver receiver = new Receiver();
        receiver.setName("name");
        receiver.setEmailAddress("1am@");
        receiver.setHomeAddress(checkAddress("123","magodo","Lagos","Nigeria","Benita street"));
        receiver.setPhoneNumber("090");




        AddMoneyToWalletRequest addMoneyToWalletRequest = new AddMoneyToWalletRequest();
        addMoneyToWalletRequest.setUserId(user.getId());
        addMoneyToWalletRequest.setAmount(BigDecimal.valueOf(1000));

        logisticsService.addMoneyToWallet(addMoneyToWalletRequest);
        BookingRequest bookingRequest = new BookingRequest();
        bookingRequest.setBookingCost(BigDecimal.valueOf(1000));
        bookingRequest.setParcelName("dress");
        bookingRequest.setSenderInfo(sender);
        bookingRequest.setReceiverInfo(receiver);
        bookingRequest.setUserId("UID1");

        logisticsService.bookService(bookingRequest);

        assertEquals(1,bookingRepository.count());

        ReviewRequest reviewRequest = new ReviewRequest();
        reviewRequest.setUserId("UID1");
        reviewRequest.setBookingId("BID09");
        reviewRequest.setComment("Thanks, order received");

        assertThrows(BookingIdNotFound.class,()->logisticsService.addReview(reviewRequest));

    }


    @Test void registerUser_loginWithCorrectDetails_bookService_CheckWalletBalance(){
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

        Sender sender = new Sender();
        sender.setName("name");
        sender.setPhoneNumber("090");
        sender.setEmailAddress("iam@");
        sender.setAddress(checkAddress("123","magodo","Lagos","Nigeria","Benita street"));


        Receiver receiver = new Receiver();
        receiver.setName("name");
        receiver.setEmailAddress("1am@");
        receiver.setHomeAddress(checkAddress("123","magodo","Lagos","Nigeria","Benita street"));
        receiver.setPhoneNumber("090");




        AddMoneyToWalletRequest addMoneyToWalletRequest = new AddMoneyToWalletRequest();
        addMoneyToWalletRequest.setUserId(user.getId());
        addMoneyToWalletRequest.setAmount(BigDecimal.valueOf(1000));

        logisticsService.addMoneyToWallet(addMoneyToWalletRequest);
        BookingRequest bookingRequest = new BookingRequest();
        bookingRequest.setBookingCost(BigDecimal.valueOf(1000));
        bookingRequest.setParcelName("dress");
        bookingRequest.setSenderInfo(sender);
        bookingRequest.setReceiverInfo(receiver);
        bookingRequest.setUserId(user.getId());

        logisticsService.bookService(bookingRequest);

        assertEquals(1,bookingRepository.count());

        assertEquals(BigDecimal.valueOf(0),logisticsService.checkWalletBalance("UID1"));

    }


    @Test void registerUser_loginWithCorrectDetails_bookService_CheckWalletBalance_With_Wrong_UserID_Throws_Exception(){
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
        Sender sender = new Sender();
        sender.setName("name");
        sender.setPhoneNumber("090");
        sender.setEmailAddress("iam@");
        sender.setAddress(checkAddress("123","magodo","Lagos","Nigeria","Benita street"));


        Receiver receiver = new Receiver();
        receiver.setName("name");
        receiver.setEmailAddress("1am@");
        receiver.setHomeAddress(checkAddress("123","magodo","Lagos","Nigeria","Benita street"));
        receiver.setPhoneNumber("090");



        AddMoneyToWalletRequest addMoneyToWalletRequest = new AddMoneyToWalletRequest();
        addMoneyToWalletRequest.setUserId(user.getId());
        addMoneyToWalletRequest.setAmount(BigDecimal.valueOf(1000));

        logisticsService.addMoneyToWallet(addMoneyToWalletRequest);
        BookingRequest bookingRequest = new BookingRequest();
        bookingRequest.setBookingCost(BigDecimal.valueOf(1000));
        bookingRequest.setParcelName("dress");
        bookingRequest.setSenderInfo(sender);
        bookingRequest.setReceiverInfo(receiver);
        bookingRequest.setUserId(user.getId());

        logisticsService.bookService(bookingRequest);

        assertEquals(1,bookingRepository.count());

        assertThrows(UserNotFoundException.class,() -> logisticsService.checkWalletBalance("UID10"));

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