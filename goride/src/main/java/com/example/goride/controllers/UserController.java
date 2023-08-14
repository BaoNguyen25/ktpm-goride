package com.example.goride.controllers;


import com.example.goride.models.Booking;
import com.example.goride.models.User;
import com.example.goride.payload.request.BookingRequest;
import com.example.goride.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/user")
@PreAuthorize("hasRole('USER')")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/booking")
    public ResponseEntity<List<User>> bookRide(@RequestBody BookingRequest bookingRequest) {
        try {
            List<User> drivers = userService.bookRide(bookingRequest);
            return ResponseEntity.status(HttpStatus.OK).body(drivers);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/booking")
    public ResponseEntity<List<Booking>> getBookings() {
        try {
            List<Booking> bookingList = userService.getBookings();
            return ResponseEntity.ok(bookingList);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/booking/nearby")
    public ResponseEntity<?> getDriversNearby() {
        try {
            List<User> listDrivers = userService.getDriversNearBy();
            return ResponseEntity.ok(listDrivers);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

}
