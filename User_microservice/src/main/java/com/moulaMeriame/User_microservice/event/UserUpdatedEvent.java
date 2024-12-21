package com.moulaMeriame.User_microservice.event;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserUpdatedEvent {
    private String id;
    private String username;
    private String email;
}
