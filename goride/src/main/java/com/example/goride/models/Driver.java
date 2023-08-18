package com.example.goride.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "users")
public class Driver extends User {
    private boolean available;
    private VehicleType vehicleType;
    private String silencePlate;

    public Driver() {
        super();
    }

    public Driver(String username, String email, String password, boolean available, VehicleType vehicleType, String silencePlate) {
        super(username, email, password);
        this.available = available;
        this.vehicleType = vehicleType;
        this.silencePlate = silencePlate;
    }
}
