package africa.xLogistics.controllers;

import africa.xLogistics.data.models.*;
import africa.xLogistics.dtos.requests.*;
import africa.xLogistics.dtos.responses.*;

import africa.xLogistics.services.LogisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
public class LogisticController {
    @Autowired
    private LogisticsService logisticsService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest){
        RegisterResponse registerResponse = new RegisterResponse();

        try {
           User user =  logisticsService.register(registerRequest);
            registerResponse.setMessage("Account created successfully , user id is " + user.getId());
            return new ResponseEntity<>(new ApiResponse(true, registerResponse), HttpStatus.CREATED);
        }
        catch (Exception ex){
            registerResponse.setMessage(ex.getMessage());
            return new ResponseEntity<>(new ApiResponse(false, registerResponse), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest){
        LoginResponse loginResponse = new LoginResponse();

        try {
            logisticsService.login(loginRequest);
            loginResponse.setMessage("login successfully");
            return new ResponseEntity<>(new ApiResponse(true, loginResponse), HttpStatus.CREATED);
        }catch (Exception e){
            loginResponse.setMessage(e.getMessage());
            return new ResponseEntity<>(new ApiResponse(false,loginResponse),HttpStatus.BAD_REQUEST);
        }
    }

@PostMapping("/wallet")
    public ResponseEntity<?> addMoneyToWallet(@RequestBody AddMoneyToWalletRequest addMoneyToWalletRequest){
    WalletResponse walletResponse = new WalletResponse();

    try {
        logisticsService.addMoneyToWallet(addMoneyToWalletRequest);
        walletResponse.setMessage("deposited " + addMoneyToWalletRequest.getAmount() + " successfully");
        return new ResponseEntity<>(new ApiResponse(true,walletResponse),HttpStatus.ACCEPTED);
    }catch (Exception e){
        walletResponse.setMessage(e.getMessage());
        return new ResponseEntity<>(new ApiResponse(false,walletResponse),HttpStatus.BAD_REQUEST);
    }
}

    @PostMapping("/receiverInfo")
    public ResponseEntity<?> addReceiverInfo(ReceiverRequest receiverRequest){
        ReceiverResponse receiverResponse = new ReceiverResponse();

        try {
            Receiver receiver = logisticsService.addReceiverInfo(receiverRequest);
            receiverResponse.setMessage("Receiver information added, Receiver id is " + receiver.getId());
            return new ResponseEntity<>(new ApiResponse(true, receiverResponse),HttpStatus.ACCEPTED);
        }catch (Exception e){
            receiverResponse.setMessage(e.getMessage());
            return new ResponseEntity<>(new ApiResponse(false, receiverResponse),HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/senderInfo")
    public  ResponseEntity<?> addSenderInfo(SenderRequest senderRequest){
        SenderResponse senderResponse = new SenderResponse();

        try {
            Sender sender = logisticsService.addSenderInfo(senderRequest);
            senderResponse.setMessage("Sender Information added, Sender id is " + sender.getId());
            return new ResponseEntity<>(new ApiResponse(true,senderResponse),HttpStatus.CREATED);
        }catch (Exception exception){
            senderResponse.setMessage(exception.getMessage());
            return new ResponseEntity<>(new ApiResponse(false,senderResponse),HttpStatus.BAD_REQUEST);
        }
    }


@PostMapping("/book")
    public ResponseEntity<?> bookService(@RequestBody BookingRequest bookingRequest){
    BookServiceResponse bookServiceResponse = new BookServiceResponse();

    try {
       Booking booking = logisticsService.bookService(bookingRequest);
        bookServiceResponse.setMessage("booked successfully, your booking id is " + booking.getBookingId());
        return new ResponseEntity<>(new ApiResponse(true,bookServiceResponse),HttpStatus.ACCEPTED);
    }catch (Exception e){
        bookServiceResponse.setMessage(e.getMessage());
        return new ResponseEntity<>(new ApiResponse(false,bookServiceResponse),HttpStatus.BAD_REQUEST);
    }
}

@PostMapping("/review")
    public ResponseEntity<?> addReview(@RequestBody ReviewRequest reviewRequest){
        ReviewResponse reviewResponse = new ReviewResponse();

        try {
           Review review = logisticsService.addReview(reviewRequest);
            reviewResponse.setMessage("successful, review id is "+ review.getReviewId());
            return  new ResponseEntity<>(new ApiResponse(true,reviewResponse),HttpStatus.CREATED);
        } catch (Exception e){
            reviewResponse.setMessage(e.getMessage());
            return new ResponseEntity<>(new ApiResponse(false,reviewResponse),HttpStatus.BAD_REQUEST);
        }
}

@GetMapping("/balance/{userId}")
    public ResponseEntity<?> checkBalance(@PathVariable("userId") String userId){
        CheckBalanceResponse checkBalanceResponse = new CheckBalanceResponse();

        try {
           BigDecimal balance = logisticsService.checkWalletBalance(userId);
            checkBalanceResponse.setMessage("Balance is " + balance);
            return new ResponseEntity<>(new ApiResponse(true,checkBalanceResponse),HttpStatus.ACCEPTED);
        }catch (Exception e){
            checkBalanceResponse.setMessage(e.getMessage());
            return new ResponseEntity<>(new ApiResponse(false,checkBalanceResponse), HttpStatus.BAD_REQUEST);
        }
}



}
