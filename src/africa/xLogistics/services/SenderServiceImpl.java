package africa.xLogistics.services;

import africa.xLogistics.data.models.Address;
import africa.xLogistics.data.models.Sender;
import africa.xLogistics.data.repositories.SenderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SenderServiceImpl implements SenderService{
    @Autowired
    SenderRepository senderRepository;


    @Override
    public Sender senderInfo(String senderId,String name, String phoneNumber, Address address, String emailAddress) {
        Sender sender = new Sender();

        sender.setName(name);
        sender.setId(senderId);
        sender.setEmailAddress(emailAddress);
        sender.setPhoneNumber(phoneNumber);

        sender.setAddress(address);
       senderRepository.save(sender);
       return sender;
    }

    @Override
    public List<Sender> findAll() {
        return senderRepository.findAll();
    }
}
