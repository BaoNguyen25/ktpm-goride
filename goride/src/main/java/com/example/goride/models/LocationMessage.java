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
    private String message;
}
