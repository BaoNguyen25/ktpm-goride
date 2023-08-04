package com.example.goride.controllers;


import com.example.goride.models.Booking;
import com.example.goride.payload.request.BookingRequest;
import com.example.goride.security.services.UserDetailsImpl;
import com.example.goride.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/booking")
    public void bookRide(@RequestBody BookingRequest bookingRequest){
        userService.bookRide(bookingRequest);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/booking")
    public List<Booking> getAllBooking(){
        return userService.getAllBooking();
    }


}
