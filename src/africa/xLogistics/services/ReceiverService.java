package africa.xLogistics.services;

import africa.xLogistics.data.models.Address;
import africa.xLogistics.data.models.Receiver;

import java.util.List;

public interface ReceiverService {
    Receiver receiverInfo(String receiverId,String name, String phoneNumber, Address address,String emailAddress);
    List<Receiver> findAll();
}
