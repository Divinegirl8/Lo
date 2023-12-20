package africa.xLogistics.services;

import africa.xLogistics.data.models.Address;
import africa.xLogistics.data.models.Receiver;
import africa.xLogistics.data.repositories.ReceiverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReceiverServiceImpl implements ReceiverService{

    @Autowired
    ReceiverRepository receiverRepository;
    @Override
    public Receiver receiverInfo(String receiverId,String name, String phoneNumber, Address address, String emailAddress) {
        Receiver receiver = new Receiver();

        receiver.setName(name);
        receiver.setEmail(emailAddress);
        receiver.setId(receiverId);
        receiver.setPhoneNumber(phoneNumber);
        receiver.setHomeAddress(address);

        receiverRepository.save(receiver);
        return receiver;
    }

    @Override
    public List<Receiver> findAll() {
        return receiverRepository.findAll();
    }
}
