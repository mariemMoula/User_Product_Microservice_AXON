package com.moulaMeriame.User_microservice.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@AllArgsConstructor
public class UpdateUserCommand {
    @TargetAggregateIdentifier
    private String id;
    private String username;
    private String email;
}