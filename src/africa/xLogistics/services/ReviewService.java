package africa.xLogistics.services;

import africa.xLogistics.data.models.Review;

import java.util.List;


public interface ReviewService {
    Review userReview(String reviewId,String userId,String bookingId,String comment);
    List<Review> findAll();
}
