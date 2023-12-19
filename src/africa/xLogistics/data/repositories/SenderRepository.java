package africa.xLogistics.data.repositories;

import africa.xLogistics.data.models.Sender;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SenderRepository extends MongoRepository<Sender, String> {
    Sender findSenderById(String senderId);
}
