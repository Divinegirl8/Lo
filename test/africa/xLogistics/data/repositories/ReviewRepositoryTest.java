package africa.xLogistics.data.repositories;

import africa.xLogistics.data.models.Review;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ReviewRepositoryTest {
    @Autowired
    ReviewRepository reviewRepository;

    @Test void save_One_Count_Is_One(){
        Review review = new Review();
        reviewRepository.save(review);
        assertEquals(1,reviewRepository.count());
    }

}