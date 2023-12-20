package africa.xLogistics.services;

import africa.xLogistics.data.models.*;
import africa.xLogistics.dtos.requests.*;

import java.math.BigDecimal;

public interface LogisticsService {
    User register(RegisterRequest registerRequest);
    User login(LoginRequest loginRequest);
    User findAccountBelongingTo(String name);

  void addMoneyToWallet(AddMoneyToWalletRequest addMoneyToWalletRequest);

  void deductMoneyFromWallet(String userId,BigDecimal amount);

  Booking bookService(BookingRequest bookingRequest);

  Receiver addReceiverInfo(ReceiverRequest receiverRequest);
  Sender addSenderInfo(SenderRequest senderRequest);

  Review addReview(ReviewRequest reviewRequest);
  BigDecimal checkWalletBalance(String userId);



}
