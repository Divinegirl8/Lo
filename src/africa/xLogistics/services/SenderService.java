package africa.xLogistics.services;

import africa.xLogistics.data.models.Address;
import africa.xLogistics.data.models.Sender;

import java.util.List;

public interface SenderService {
    Sender senderInfo(String senderId,String name, String phoneNumber, Address address,String emailAddress);
    List<Sender> findAll();
}
