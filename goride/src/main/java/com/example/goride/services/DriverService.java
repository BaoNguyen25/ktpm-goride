package com.example.goride.services;

import com.example.goride.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DriverService {

    @Autowired
    private UserRepository userRepository;

}
