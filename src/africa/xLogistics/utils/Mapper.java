package africa.xLogistics.utils;

import africa.xLogistics.data.models.*;
import africa.xLogistics.dtos.requests.*;

import java.time.LocalDateTime;

public class Mapper {
    public static User registerMap(String userId,RegisterRequest registerRequest){
        User newUser = new User();
        newUser.setId(userId);
        newUser.setUsername(registerRequest.getUsername());
        newUser.setPassword(registerRequest.getPassword());
        newUser.setEmailAddress(registerRequest.getEmailAddress());
        newUser.setPhoneNumber(registerRequest.getPhoneNumber());

        Address address = registerRequest.getAddress();

        if (address != null){
            newUser.setAddress(address);
        }
        return newUser;
    }
    public static Booking bookMap(String bookingId, LocalDateTime dateTime, BookingRequest bookingRequest){
        Booking booking = new Booking();
        booking.setBookingId(bookingId);

        Sender sender = new Sender();
        sender.setId(bookingRequest.getSenderId());
        booking.setParcelName(bookingRequest.getParcelName());
        booking.setDateTime(dateTime);

        Receiver receiver = new Receiver();
        receiver.setId(bookingRequest.getReceiverId());

        User user = new User();
        user.setId(bookingRequest.getUserId());

        return booking;

    }
    public static Receiver mapReceiver(String receiverId, ReceiverRequest receiverRequest){
        Receiver receiver = new Receiver();

        receiver.setName(receiverRequest.getName());
        receiver.setEmail(receiverRequest.getEmailAddress());
        receiver.setId(receiverId);
        receiver.setPhoneNumber(receiverRequest.getPhoneNumber());

        Address address = receiverRequest.getAddress();

        if (address != null){
            receiver.setHomeAddress(address);
        }
      return receiver;

    }

    public static Sender mapSender(String senderId, SenderRequest senderRequest){
        Sender sender = new Sender();

        sender.setName(senderRequest.getName());
        sender.setId(senderId);
        sender.setEmailAddress(senderRequest.getEmailAddress());
        sender.setPhoneNumber(senderRequest.getPhoneNumber());

        Address address = senderRequest.getAddress();

        if (address != null){
            sender.setAddress(address);
        }
return sender;
    }

    public static Review mapReview(String reviewId, ReviewRequest reviewRequest){
        Review review = new Review();

        review.setReviewId(reviewId);
        review.setUserId(reviewRequest.getUserId());
        review.setBookingId(reviewRequest.getBookingId());
        review.setComment(reviewRequest.getComment());

        return review;
    }
}
