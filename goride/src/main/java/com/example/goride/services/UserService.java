package com.example.goride.services;

import com.example.goride.models.Booking;
import com.example.goride.payload.request.BookingRequest;
import com.example.goride.repositories.BookingRepository;
import com.example.goride.repositories.UserRepository;
import com.example.goride.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookingRepository bookingRepository;

    public List<Booking> getAllBooking() {
        return bookingRepository.findAll();
    }

    @Transactional
    public void bookRide(BookingRequest bookingRequest) {
        double price = calculatePrice(bookingRequest);
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userId = ((UserDetailsImpl)principal).getId();

        Booking savedBooking = new Booking(userId,null, bookingRequest.getSourceLocation(), bookingRequest.getDestinationLocation(), price, LocalDateTime.now());
        bookingRepository.save(savedBooking);
    }

    public double calculatePrice(BookingRequest bookingRequest) {
        double distanceInKm = calculateDistance(bookingRequest);
        return distanceInKm*10000;
    }

    public double calculateDistance(BookingRequest bookingRequest) {
        final int R = 6371;

        double lat1Rad = Math.toRadians(bookingRequest.getSourceLocation().getLatitude());
        double lon1Rad = Math.toRadians(bookingRequest.getSourceLocation().getLongitude());
        double lat2Rad = Math.toRadians(bookingRequest.getDestinationLocation().getLatitude());
        double lon2Rad = Math.toRadians(bookingRequest.getDestinationLocation().getLongitude());

        double dLat = lat2Rad - lat1Rad;
        double dLon = lon2Rad - lon1Rad;

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(lat1Rad) * Math.cos(lat2Rad) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return R * c;
    }


}
