package com.example.goride.controllers;

import com.example.goride.models.*;
import com.example.goride.payload.request.DriverSignupRequest;
import com.example.goride.payload.request.LoginRequest;
import com.example.goride.payload.request.SignupRequest;
import com.example.goride.payload.response.JwtResponse;
import com.example.goride.payload.response.MessageResponse;
import com.example.goride.repositories.DriverRepository;
import com.example.goride.repositories.RoleRepository;
import com.example.goride.repositories.UserRepository;
import com.example.goride.security.jwt.JwtUtils;
import com.example.goride.security.services.UserDetailsImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private DriverRepository driverRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles));
    }

    @PostMapping("/user/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        // Create new user's account
        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));

        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("Error: User role is not found."));
        roles.add(userRole);

        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    @PostMapping("/driver/signup")
    public ResponseEntity<?> registerDriver(@Valid @RequestBody DriverSignupRequest request) {
        if (driverRepository.existsByUsername(request.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        if (driverRepository.existsByEmail(request.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        Driver driver = new Driver(request.getUsername(),
                request.getEmail(),
                encoder.encode(request.getPassword()),
                request.isAvailable(),
                request.getVehicleType(),
                request.getSilencePlate());

        Set<Role> roles = new HashSet<>();
        Role driverRole = roleRepository.findByName(ERole.ROLE_DRIVER)
                .orElseThrow(() -> new RuntimeException("Error: Driver role is not found."));
        roles.add(driverRole);

        driver.setRoles(roles);
        driverRepository.save(driver);

        return ResponseEntity.ok(new MessageResponse("Driver registered successfully!"));
    }

    @GetMapping("/token/{token}")
    public ResponseEntity<String> isExpiredToken(@PathVariable String token) {

        boolean isExpiredToken = jwtUtils.isTokenExpired(token);
        if (isExpiredToken)  {
           return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token is invalid");
        }
        return ResponseEntity.status(HttpStatus.OK).body("Token is valid");
    }

}