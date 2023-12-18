package africa.xLogistics.controllers;

import africa.xLogistics.dtos.requests.AddMoneyToWalletRequest;
import africa.xLogistics.dtos.requests.LoginRequest;
import africa.xLogistics.dtos.requests.RegisterRequest;
import africa.xLogistics.dtos.responses.ApiResponse;
import africa.xLogistics.dtos.responses.LoginResponse;
import africa.xLogistics.dtos.responses.RegisterResponse;
import africa.xLogistics.dtos.responses.WalletResponse;
import africa.xLogistics.exceptions.InvalidDepositAmountException;
import africa.xLogistics.exceptions.InvalidDetailsException;
import africa.xLogistics.exceptions.UserNotFoundException;

import africa.xLogistics.services.LogisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class LogisticController {
    @Autowired
    private LogisticsService logisticsService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest){
        RegisterResponse registerResponse = new RegisterResponse();

        try {
            logisticsService.register(registerRequest);
            registerResponse.setMessage("Account created successfully");
            return new ResponseEntity<>(new ApiResponse(true, registerResponse), HttpStatus.CREATED);
        }
        catch (UserNotFoundException ex){
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
        }catch (InvalidDetailsException e){
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
    }catch (InvalidDepositAmountException e){
        walletResponse.setMessage(e.getMessage());
        return new ResponseEntity<>(new ApiResponse(false,walletResponse),HttpStatus.BAD_REQUEST);
    }
}


}
