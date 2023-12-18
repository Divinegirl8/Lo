package africa.xLogistics.services;

import africa.xLogistics.data.models.User;
import africa.xLogistics.dtos.requests.RegisterRequest;

public interface LogisticsService {
    User register(RegisterRequest registerRequest);


}
