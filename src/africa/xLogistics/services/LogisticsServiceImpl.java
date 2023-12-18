package africa.xLogistics.services;

import africa.xLogistics.data.models.Booking;
import africa.xLogistics.data.models.User;
import africa.xLogistics.data.models.Wallet;
import africa.xLogistics.data.repositories.BookingRepository;
import africa.xLogistics.data.repositories.UserRepository;
import africa.xLogistics.dtos.requests.AddMoneyToWalletRequest;
import africa.xLogistics.dtos.requests.BookingRequest;
import africa.xLogistics.dtos.requests.LoginRequest;
import africa.xLogistics.dtos.requests.RegisterRequest;
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


    @Override
    public User register(RegisterRequest registerRequest) {
        if(userExist(registerRequest.getUsername())) throw new UserExistException(registerRequest.getUsername()+ " already exist");
       User user = registerMap("UID" + repository.count() + 1,registerRequest);

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
        BigDecimal bookingCost = bookingRequest.getBookingCost();
        deductMoneyFromWallet(bookingRequest.getSenderInfo().getId(),bookingCost);
        Booking booking = bookMap("BID" + bookingRepository.count() + 1, LocalDateTime.now(),bookingRequest);
        booking.setBooked(true);
        bookingRepository.save(booking);
        return booking;
    }

    private void validateDepositAmount(BigDecimal amount){
        if (amount.compareTo(BigDecimal.ZERO) <= 0) throw new InvalidDepositAmountException("Error!!!, the amount you are trying to deposit must be greater than 0 \nTry again");
    }

    private void validateSufficientFund(BigDecimal balance,BigDecimal amount){
        if (balance.compareTo(amount)  < 0) throw new InsufficientFundsError("Error!!! your account balance is lower than the money you are trying to withdraw\nTry Again");
    }

    private void validateLowBalance(BigDecimal amount){
        if (amount.compareTo(BigDecimal.ZERO) <= 0)  throw new LowAmountError("Error!!! The amount you are trying to withdraw must be greater than 0\nTry again");
    }


    private boolean userExist(String username) {
        User foundUser = repository.findUserByUsername(username);
        return foundUser != null;
    }
}
