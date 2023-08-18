package com.example.goride.services;

import com.example.goride.models.Booking;
import com.example.goride.models.ERole;
import com.example.goride.models.Role;
import com.example.goride.models.User;
import com.example.goride.payload.request.BookingRequest;
import com.example.goride.repositories.BookingRepository;
import com.example.goride.repositories.RoleRepository;
import com.example.goride.repositories.UserRepository;
import com.example.goride.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    public List<Booking> getBookings() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userId = ((UserDetailsImpl)principal).getId();
        return bookingRepository.findByUserId(userId);
    }

    @Transactional
    public List<User> bookRide(BookingRequest bookingRequest) {
        int price = (int) calculatePrice(bookingRequest);
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userId = ((UserDetailsImpl)principal).getId();

        Booking savedBooking = new Booking(userId,null, bookingRequest.getSourceLocation(), bookingRequest.getDestinationLocation(), price, LocalDateTime.now());
        bookingRepository.save(savedBooking);
        return getDriversNearBy(bookingRequest);
    }

    public double calculatePrice(BookingRequest bookingRequest) {
        double distanceInKm = calculateDistance(
                bookingRequest.getSourceLocation().getLatitude(),
                bookingRequest.getSourceLocation().getLongitude(),
                bookingRequest.getDestinationLocation().getLatitude(),
                bookingRequest.getDestinationLocation().getLongitude());
        return distanceInKm*10000;
    }

    public double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371;

        double lat1Rad = Math.toRadians(lat1);
        double lon1Rad = Math.toRadians(lon1);
        double lat2Rad = Math.toRadians(lat2);
        double lon2Rad = Math.toRadians(lon2);

        double dLat = lat2Rad - lat1Rad;
        double dLon = lon2Rad - lon1Rad;

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(lat1Rad) * Math.cos(lat2Rad) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return R * c;
    }


    public List<User> getDriversNearBy(BookingRequest bookingRequest) {
       double latUser = bookingRequest.getSourceLocation().getLatitude();
       double lonUser = bookingRequest.getSourceLocation().getLongitude();
       double maxDistanceKm = 2.0;

       Role driverRole = roleRepository.findByName(ERole.ROLE_DRIVER)
               .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
       List<User> drivers = userRepository.findByRolesContains(driverRole);

       List<User> nearByDrivers = drivers.stream()
               .filter(driver -> {
                    double latDriver = driver.getLocation().getLatitude();
                    double lonDriver = driver.getLocation().getLongitude();
                    double distance = calculateDistance(latUser, lonUser, latDriver, lonDriver);
                    return distance <= maxDistanceKm;
               })
               .collect(Collectors.toList());


       return nearByDrivers;

    }

    public User getDriverById (String id) {
        Optional<User> driverOptional = userRepository.findById(id);
        if (driverOptional.isPresent()) {
            return driverOptional.get();
        } else {
            throw new RuntimeException("Driver not found with ID: " + id);
        }
    }

}
