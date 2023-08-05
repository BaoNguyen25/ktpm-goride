package com.example.goride.services;

import com.example.goride.models.Booking;
import com.example.goride.repositories.BookingRepository;
import com.example.goride.repositories.UserRepository;
import com.example.goride.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DriverService {

    @Autowired
    private BookingRepository bookingRepository;

    public List<Booking> getBookings() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String driverId = ((UserDetailsImpl)principal).getId();
        return bookingRepository.findByDriverId(driverId);
    }

    public void acceptBooking(String bookingId) {
        Optional<Booking> bookingOptional = bookingRepository.findById(bookingId);
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String driverId = ((UserDetailsImpl)principal).getId();

        if (bookingOptional.isPresent()) {
            Booking booking = bookingOptional.get();
            booking.setDriverId(driverId);
            bookingRepository.save(booking);
        } else {
            throw new RuntimeException("Booking not found with ID: " + bookingId);
        }
    }

}
