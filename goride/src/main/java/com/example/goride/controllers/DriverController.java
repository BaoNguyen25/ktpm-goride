package com.example.goride.controllers;


import com.example.goride.models.Booking;
import com.example.goride.models.Location;
import com.example.goride.services.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/driver")
@PreAuthorize("hasRole('DRIVER')")
public class DriverController {

    @Autowired
    private DriverService driverService;

    @GetMapping("/booking")
    public ResponseEntity<List<Booking>> getBookings() {
        List<Booking> bookingList = driverService.getBookings();
        return ResponseEntity.ok(bookingList);
    }

    @PatchMapping("/booking/{bookingId}")
    public ResponseEntity<String> acceptBooking(@PathVariable String bookingId) {
        driverService.acceptBooking(bookingId);
        return ResponseEntity.ok("Accepted booking successfully");
    }

    @PatchMapping("/location")
    public ResponseEntity<String> updateLocation(@RequestBody Location location) {
        driverService.updateLocation(location);
        return ResponseEntity.ok("Updated location successfully");
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<?> getUser(@PathVariable String id) {
        return ResponseEntity.ok(driverService.getUserById(id));
    }
}
