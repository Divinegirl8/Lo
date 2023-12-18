package africa.xLogistics.data.repositories;

import africa.xLogistics.data.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
    User findUserByUsername(String name);
    User findUserById(String userId);

}
