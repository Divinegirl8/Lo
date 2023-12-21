package africa.xLogistics.services;

import africa.xLogistics.data.models.*;
import africa.xLogistics.dtos.requests.*;

import java.math.BigDecimal;
import java.util.List;

public interface LogisticsService {
    User register(RegisterRequest registerRequest);
    User login(LoginRequest loginRequest);
    User findAccountBelongingTo(String name);

  void addMoneyToWallet(AddMoneyToWalletRequest addMoneyToWalletRequest);

  void deductMoneyFromWallet(String userId,BigDecimal amount);

  Booking bookService(BookingRequest bookingRequest);

  Review addReview(ReviewRequest reviewRequest);
  BigDecimal checkWalletBalance(String userId);

  List<Booking> findListOfBookingOf(String userid);





}
