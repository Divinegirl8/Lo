package africa.xLogistics.controllers;

import africa.xLogistics.dtos.requests.RegisterRequest;
import africa.xLogistics.dtos.responses.ApiResponse;
import africa.xLogistics.dtos.responses.RegisterResponse;
import africa.xLogistics.exceptions.DiaryAppException;

import africa.xLogistics.services.LogisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class LogisticController {
    @Autowired
    private LogisticsService diaryService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest){
        RegisterResponse registerResponse = new RegisterResponse();

        try {
            diaryService.register(registerRequest);
            registerResponse.setMessage("Account created successfully");
            return new ResponseEntity<>(new ApiResponse(true, registerResponse), HttpStatus.CREATED);
        }
        catch (DiaryAppException ex){
            registerResponse.setMessage(ex.getMessage());
            return new ResponseEntity<>(new ApiResponse(false, registerResponse), HttpStatus.BAD_REQUEST);
        }
    }


}
