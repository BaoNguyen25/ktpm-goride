package com.example.goride.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LocationMessage {
    private String senderID;
    private String receiverID;
    private double latitude;
    private double longitude;
    private double desLat;
    private double desLng;
    private String message;
    private String bookingId;
}
