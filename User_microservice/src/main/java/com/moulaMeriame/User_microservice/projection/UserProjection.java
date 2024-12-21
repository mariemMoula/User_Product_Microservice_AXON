package com.moulaMeriame.User_microservice.projection;

import com.moulaMeriame.User_microservice.entities.User;
import com.moulaMeriame.User_microservice.event.UserCreatedEvent;
import com.moulaMeriame.User_microservice.event.UserDeletedEvent;
import com.moulaMeriame.User_microservice.event.UserUpdatedEvent;
import com.moulaMeriame.User_microservice.query.FindUserByIdQuery;
import com.moulaMeriame.User_microservice.query.FindUserByUsernameQuery;
import com.moulaMeriame.User_microservice.repositories.UserRepository;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserProjection {
    @Autowired
    private UserRepository userRepository;

    @QueryHandler
    public User handle(FindUserByIdQuery query) {
        return userRepository.findById(query.getId()).orElse(null);
    }

    @QueryHandler
    public User handle(FindUserByUsernameQuery query) {
        return userRepository.findByUsername(query.getUsername());
    }
    @EventHandler
    public void on(UserCreatedEvent event) {
        User user = new User();
        user.setId(event.getId());
        user.setUsername(event.getUsername());
        user.setEmail(event.getEmail());
        user.setPassword(event.getPassword());
        userRepository.save(user);
    }

    @EventHandler
    public void on(UserUpdatedEvent event) {
        Optional<User> optionalUser = userRepository.findById(event.getId());
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setUsername(event.getUsername());
            user.setEmail(event.getEmail());
            userRepository.save(user);
        }
    }

    @EventHandler
    public void on(UserDeletedEvent event) {
        userRepository.deleteById(event.getId());
    }

}
