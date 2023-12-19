package africa.xLogistics.data.repositories;

import africa.xLogistics.data.models.Receiver;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface ReceiverRepository extends MongoRepository<Receiver,String> {
    Receiver findReceiverById(String id);
}
