package com.example.goride.controllers;


import com.example.goride.models.Booking;
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
public class UserController {

    @Autowired
    private UserService userService;

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/booking")
    public ResponseEntity<String> bookRide(@RequestBody BookingRequest bookingRequest) {
        try {
            userService.bookRide(bookingRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body("Ride booked successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while booking the ride");
        }
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/booking")
    public ResponseEntity<List<Booking>> getBookings() {
        try {
            List<Booking> bookingList = userService.getBookings();
            return ResponseEntity.ok(bookingList);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

}
