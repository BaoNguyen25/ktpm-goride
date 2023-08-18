package com.example.goride.controllers;


import com.example.goride.models.Booking;
import com.example.goride.models.Driver;
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
    public ResponseEntity<List<Driver>> bookRide(@RequestBody BookingRequest bookingRequest) {
        List<Driver> drivers = userService.bookRide(bookingRequest);
        return ResponseEntity.status(HttpStatus.OK).body(drivers);
    }

    @GetMapping("/booking")
    public ResponseEntity<List<Booking>> getBookings() {
        List<Booking> bookingList = userService.getBookings();
        return ResponseEntity.ok(bookingList);
    }

    @GetMapping("/driver/{id}")
    public ResponseEntity<?> getDriver(@PathVariable String id) {
        return ResponseEntity.ok(userService.getDriverById(id));
    }
}
