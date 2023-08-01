package com.example.goride.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Message {
    private String senderID;
    private String receiverID;
    private String content;

}
