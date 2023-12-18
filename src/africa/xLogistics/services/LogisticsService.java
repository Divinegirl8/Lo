package africa.xLogistics.services;

import africa.xLogistics.data.models.Booking;
import africa.xLogistics.data.models.User;
import africa.xLogistics.dtos.requests.AddMoneyToWalletRequest;
import africa.xLogistics.dtos.requests.BookingRequest;
import africa.xLogistics.dtos.requests.LoginRequest;
import africa.xLogistics.dtos.requests.RegisterRequest;

import java.math.BigDecimal;

public interface LogisticsService {
    User register(RegisterRequest registerRequest);
    void login(LoginRequest loginRequest);
    User findAccountBelongingTo(String name);

  void addMoneyToWallet(AddMoneyToWalletRequest addMoneyToWalletRequest);

  void deductMoneyFromWallet(String userId,BigDecimal amount);

  Booking bookService(BookingRequest bookingRequest);


}
