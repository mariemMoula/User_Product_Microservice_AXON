package com.moulaMeriame.User_microservice.controllers;

import com.moulaMeriame.User_microservice.command.CreateUserCommand;
import com.moulaMeriame.User_microservice.command.DeleteUserCommand;
import com.moulaMeriame.User_microservice.command.UpdateUserCommand;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/users/commands")
public class UserCommandController {

    @Autowired
    private CommandGateway commandGateway;

    @PostMapping
    public String createUser(@RequestBody CreateUserCommand command) {
        command.setId(UUID.randomUUID().toString());
        commandGateway.send(command);
        return "User created with ID: " + command.getId();
    }

    @PutMapping
    public String updateUser(@RequestBody UpdateUserCommand command) {
        commandGateway.send(command);
        return "User updated: " + command.getId();
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable String id) {
        commandGateway.send(new DeleteUserCommand(id));
        return "User deleted: " + id;
    }
}