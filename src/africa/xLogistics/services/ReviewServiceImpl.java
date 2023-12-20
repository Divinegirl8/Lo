package africa.xLogistics.services;

import africa.xLogistics.data.models.Review;
import africa.xLogistics.data.repositories.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService{
    @Autowired
    ReviewRepository reviewRepository;
    @Override
    public Review userReview(String reviewId,String userId, String bookingId, String comment) {
        Review review = new Review();

        review.setReviewId(reviewId);
        review.setUserId(userId);
        review.setBookingId(bookingId);
        review.setComment(comment);

        reviewRepository.save(review);

        return review;
    }

    @Override
    public List<Review> findAll() {
      return  reviewRepository.findAll();
    }
}
