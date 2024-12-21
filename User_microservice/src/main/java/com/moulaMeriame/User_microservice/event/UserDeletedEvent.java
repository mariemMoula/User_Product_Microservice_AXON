package com.moulaMeriame.User_microservice.event;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDeletedEvent {
    private String id;
}