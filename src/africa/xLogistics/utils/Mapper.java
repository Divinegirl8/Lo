package africa.xLogistics.utils;

import africa.xLogistics.data.models.Address;
import africa.xLogistics.data.models.Booking;
import africa.xLogistics.data.models.Customer;
import africa.xLogistics.data.models.User;
import africa.xLogistics.dtos.requests.BookingRequest;
import africa.xLogistics.dtos.requests.RegisterRequest;

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

        User user = new User();
        user.setUsername(bookingRequest.getSenderInfo().getUsername());
        user.setAddress(bookingRequest.getSenderInfo().getAddress());
        user.setId(bookingRequest.getSenderInfo().getId());
        user.setPhoneNumber(bookingRequest.getSenderInfo().getPhoneNumber());
        booking.setSenderInfo(user);

        booking.setParcelName(bookingRequest.getParcelName());
        booking.setDateTime(dateTime);

        Customer customer = new Customer();
        customer.setName(bookingRequest.getReceiverInfo().getName());
        customer.setEmail(bookingRequest.getReceiverInfo().getEmail());
        customer.setHomeAddress(bookingRequest.getReceiverInfo().getHomeAddress());
        customer.setPhoneNumber(bookingRequest.getReceiverInfo().getPhoneNumber());
        booking.setReceiverInfo(customer);

        return booking;

    }
}
