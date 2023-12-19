package africa.xLogistics.data.repositories;

import africa.xLogistics.data.models.Review;
import africa.xLogistics.data.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ReviewRepository extends MongoRepository<Review,String> {
    Review findReviewByReviewId(String reviewId);

}
