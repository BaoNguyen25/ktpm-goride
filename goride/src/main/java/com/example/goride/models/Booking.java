package com.example.goride.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Document("bookings")
public class Booking {

    @Id
    private String id;
    private String userId;
    private String driverId;
    private Location sourceLocation;
    private Location destinationLocation;
    private double price;
    private LocalDateTime dateTime;

    public Booking(String userId, String driverId, Location sourceLocation, Location destinationLocation, double price, LocalDateTime dateTime) {
        this.userId = userId;
        this.driverId = driverId;
        this.sourceLocation = sourceLocation;
        this.destinationLocation = destinationLocation;
        this.price = price;
        this.dateTime = dateTime;
    }
}
