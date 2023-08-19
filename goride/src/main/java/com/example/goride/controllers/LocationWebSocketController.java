package com.example.goride.controllers;

import com.example.goride.GorideApplication;
import com.example.goride.models.LocationMessage;
import com.example.goride.models.Message;
import com.example.goride.models.User;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.stream.Location;


@Controller
public class LocationWebSocketController {
    private static final Logger logger = LoggerFactory.getLogger(LocationWebSocketController.class);
    private final SimpMessagingTemplate messagingTemplate;
    public LocationWebSocketController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/chat")
//    @SendTo("/topic/location")
    public void chat(Message message) {
        String recipientUserId = message.getReceiverID();

        String recipientTopic = "/topic/user/" + recipientUserId + "/chat";
        messagingTemplate.convertAndSend(recipientTopic, message);
    }

    @MessageMapping("/sendLocation")
    public void sendLocation(LocationMessage message) {
        System.out.println(message.getReceiverID());
        String recipientTopic = "/topic/driver/" + message.getReceiverID() + "/booking";

        messagingTemplate.convertAndSend(recipientTopic, message);
    }

    @MessageMapping("/accept")
    public void acceptBooking(LocationMessage message) {
        System.out.println(message.getReceiverID());
        String recipientTopic = "/topic/user/" + message.getReceiverID() + "/accept";

        messagingTemplate.convertAndSend(recipientTopic, message);
    }

    @MessageMapping("/pickup")
    public void acceptPickup(LocationMessage message) {
        System.out.println(message.getReceiverID());
        String recipientTopic = "/topic/user/" + message.getReceiverID() + "/pickup";

        messagingTemplate.convertAndSend(recipientTopic, message);
    }
}