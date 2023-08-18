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
        try {
            List<Booking> bookingList = driverService.getBookings();
            return ResponseEntity.ok(bookingList);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PatchMapping("/booking/{bookingId}")
    public ResponseEntity<String> acceptBooking(@PathVariable String bookingId) {
        try {
            driverService.acceptBooking(bookingId);
            return ResponseEntity.ok("Accepted booking successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/status")
    public ResponseEntity<String> updateStatus(@RequestBody boolean status) {
        try {
            driverService.updateStatus(status);
            return ResponseEntity.ok("Updated status successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<?> getUser(@PathVariable String id) {
        try {
            return ResponseEntity.ok(driverService.getUserById(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
        }
    }
}
