package com.example.goride.services;

import com.example.goride.models.Booking;
import com.example.goride.models.Location;
import com.example.goride.models.User;
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

    @Autowired
    private UserRepository userRepository;
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

    public void updateLocation(Location location) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String driverId = ((UserDetailsImpl)principal).getId();

        Optional<User> driverOptional = userRepository.findById(driverId);
        if (driverOptional.isPresent()) {
            User driver = driverOptional.get();
            driver.setLocation(location);
            userRepository.save(driver);
        } else {
            throw new RuntimeException("Driver not found with ID: " + driverId);
        }
    }

    public User getUserById (String id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            return userOptional.get();
        } else {
            throw new RuntimeException("User not found with ID: " + id);
        }
    }

    public void updateStatus(boolean status) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String driverId = ((UserDetailsImpl)principal).getId();

        Optional<User> driverOptional = userRepository.findById(driverId);
        if (driverOptional.isPresent()) {
            User driver = driverOptional.get();
            driver.setAvailable(status);
            userRepository.save(driver);
        } else {
            throw new RuntimeException("Driver not found with ID: " + driverId);
        }
    }
}
