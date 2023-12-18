package africa.xLogistics.services;

import africa.xLogistics.data.models.User;
import africa.xLogistics.data.models.Wallet;
import africa.xLogistics.data.repositories.UserRepository;
import africa.xLogistics.dtos.requests.RegisterRequest;
import africa.xLogistics.exceptions.UserExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static africa.xLogistics.utils.Mapper.registerMap;

@Service
public class LogisticsServiceImpl implements LogisticsService {
    @Autowired
    private UserRepository repository ;


    @Override
    public User register(RegisterRequest registerRequest) {
        if(userExist(registerRequest.getUsername())) throw new UserExistException(registerRequest.getUsername()+ " already exist");
       User user = registerMap(String.valueOf(repository.count() + 1),registerRequest);

        Wallet wallet = new Wallet();
        user.setWallet(wallet);

        repository.save(user);
        return user;
    }


    private boolean userExist(String username) {
        User foundUser = repository.findUserByUsername(username);
        return foundUser != null;
    }
}
