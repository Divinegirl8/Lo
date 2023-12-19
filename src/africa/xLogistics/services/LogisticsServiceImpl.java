package africa.xLogistics.services;

import africa.xLogistics.data.models.*;
import africa.xLogistics.data.repositories.*;
import africa.xLogistics.dtos.requests.*;
import africa.xLogistics.exceptions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static africa.xLogistics.utils.Mapper.*;

@Service
public class LogisticsServiceImpl implements LogisticsService {
    @Autowired
    private UserRepository repository ;
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private ReceiverRepository receiverRepository;
    @Autowired
    private SenderRepository senderRepository;
    @Autowired
    private ReviewRepository reviewRepository;


    @Override
    public User register(RegisterRequest registerRequest) {
        if(userExist(registerRequest.getUsername())) throw new UserExistException(registerRequest.getUsername()+ " already exist");
       User user = registerMap("UID" + (repository.count() + 1),registerRequest);

        Wallet wallet = new Wallet();
        user.setWallet(wallet);

        repository.save(user);
        return user;
    }

    @Override
    public void login(LoginRequest loginRequest) {
        User user = repository.findUserByUsername(loginRequest.getUsername());

        if (!userExist(loginRequest.getUsername())) throw new InvalidDetailsException();
        if (!loginRequest.getPassword().equals(user.getPassword())) throw new InvalidDetailsException();
        user.setLoggedIn(true);
        repository.save(user);
    }

    @Override
    public User findAccountBelongingTo(String name) {
    User user = repository.findUserByUsername(name);

    if (user == null) throw new UserNotFoundException(name + " not found");

    return user;

    }

    @Override
    public void addMoneyToWallet(AddMoneyToWalletRequest addMoneyToWalletRequest) {
     User user = repository.findUserById(addMoneyToWalletRequest.getUserId());
        if (user == null) {
            throw new UserNotFoundException(addMoneyToWalletRequest.getUserId() + " not found");
        }

     if (!user.isLoggedIn()) {throw new NotLoginError("you must login to perform this action");}

        Wallet wallet = user.getWallet();
        BigDecimal balance = wallet.getBalance();

        validateDepositAmount(addMoneyToWalletRequest.getAmount());

        wallet.setBalance(balance.add(addMoneyToWalletRequest.getAmount()));
        repository.save(user);

    }

    @Override
    public void deductMoneyFromWallet(String userId, BigDecimal amount) {
        User user = repository.findUserById(userId);
        if (user == null) {
            throw new UserNotFoundException(userId + " not found");
        }

        Wallet wallet = user.getWallet();
        BigDecimal balance = wallet.getBalance();

        validateSufficientFund(balance,amount);
        validateLowBalance(amount);

        wallet.setBalance(balance.subtract(amount));
        repository.save(user);
    }

    @Override
    public Booking bookService(BookingRequest bookingRequest) {
        User user = repository.findUserById(bookingRequest.getUserId());
        if (!findReceiverId(bookingRequest.getReceiverId())) {
            throw new ReceiverIdNotFoundError(bookingRequest.getReceiverId() + " not found");
        }

        if (!findSenderId(bookingRequest.getSenderId())) {
            throw new SenderIdNotFoundError(bookingRequest.getSenderId() + " not found");
        }
        if (!user.isLoggedIn()) {throw new NotLoginError("you must login to perform this action");}

        BigDecimal bookingCost = bookingRequest.getBookingCost();
        deductMoneyFromWallet(bookingRequest.getUserId(), bookingCost);
        Booking booking = bookMap("BID" + (bookingRepository.count() + 1), LocalDateTime.now(),bookingRequest);
        booking.setBooked(true);

        bookingRepository.save(booking);
        return booking;
    }

    private boolean findReceiverId(String receiverId){
        Receiver receiver = receiverRepository.findReceiverById(receiverId);
        return receiver != null;
    }

    private boolean findSenderId(String senderId){
        Sender sender = senderRepository.findSenderById(senderId);

        return sender != null;
    }

    @Override
    public Receiver addReceiverInfo(ReceiverRequest receiverRequest) {

        Receiver receiver = mapReceiver("RID" + (receiverRepository.count() + 1), receiverRequest);
        receiverRepository.save(receiver);
        return receiver;
    }

    @Override
    public Sender addSenderInfo(SenderRequest senderRequest) {
        Sender sender = mapSender("SID" + (senderRepository.count() + 1),senderRequest);
        senderRepository.save(sender);
        return sender;
    }

    @Override
    public Review addReview(ReviewRequest reviewRequest) {
        User user = repository.findUserById(reviewRequest.getUserId());

        if (user == null) throw new UserNotFoundException( reviewRequest.getUserId() + " not found");

        if (!user.isLoggedIn()) {throw new NotLoginError("you must login to perform this action");}

        Booking booking = bookingRepository.findBookingByBookingId(reviewRequest.getBookingId());
        if (booking == null) throw new BookingIdNotFound(reviewRequest.getBookingId() + " not found");

        Review review = mapReview("RID" + (reviewRepository.count() + 1),reviewRequest);
        reviewRepository.save(review);
        return review;
    }

    @Override
    public BigDecimal checkWalletBalance(String userId) {
        User user = repository.findUserById(userId);
        if (user == null) throw new UserNotFoundException( userId + " not found");

        if (!user.isLoggedIn()) {throw new NotLoginError("you must login to perform this action");}

        return user.getWallet().getBalance();

    }

    private void validateDepositAmount(BigDecimal amount){
        if (amount.compareTo(BigDecimal.ZERO) <= 0) throw new InvalidDepositAmountException("Error!!!, the amount you are trying to deposit must be greater than 0 \nTry again");
    }

    private void validateSufficientFund(BigDecimal balance,BigDecimal amount){
        if (balance.compareTo(amount)  < 0) throw new InsufficientFundsError("Error!!! your wallet balance is lower than the money you are trying to withdraw");
    }

    private void validateLowBalance(BigDecimal amount){
        if (amount.compareTo(BigDecimal.ZERO) <= 0)  throw new LowAmountError("Error!!! The amount you are trying to withdraw must be greater than 0");
    }


    private boolean userExist(String username) {
        User foundUser = repository.findUserByUsername(username);
        return foundUser != null;
    }
}
