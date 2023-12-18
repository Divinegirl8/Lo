package africa.xLogistics.data.repositories;

import africa.xLogistics.data.models.Address;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AddressRepository extends MongoRepository<Address , String> {
}
