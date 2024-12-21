package com.moulaMeriame.User_microservice.event;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserCreatedEvent {
    private String id;
    private String username;
    private String email;
    private String password;
}
