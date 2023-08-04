package com.example.goride.payload.request;


import com.example.goride.models.Location;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookingRequest {
    private Location sourceLocation;
    private Location destinationLocation;
}
