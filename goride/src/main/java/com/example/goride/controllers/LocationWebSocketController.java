package com.example.goride.controllers;

import com.example.goride.models.LocationMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class LocationWebSocketController {

    @MessageMapping("/sendLocation")
    @SendTo("/topic/location")
    public LocationMessage sendLocation(LocationMessage message) {
        return message;
    }
}