package com.moulaMeriame.User_microservice.aggregate;

import com.moulaMeriame.User_microservice.command.CreateUserCommand;
import com.moulaMeriame.User_microservice.command.DeleteUserCommand;
import com.moulaMeriame.User_microservice.command.UpdateUserCommand;
import com.moulaMeriame.User_microservice.event.UserCreatedEvent;
import com.moulaMeriame.User_microservice.event.UserDeletedEvent;
import com.moulaMeriame.User_microservice.event.UserUpdatedEvent;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Aggregate
@NoArgsConstructor
@Data
public class UserAggregate {
    @AggregateIdentifier
    private String id;
    private String username;
    private String email;
    private String password;

    @CommandHandler
    public UserAggregate(CreateUserCommand command) {
        apply(new UserCreatedEvent(command.getId(), command.getUsername(), command.getEmail(), command.getPassword()));
    }

    @EventSourcingHandler
    public void on(UserCreatedEvent event) {
        this.id = event.getId();
        this.username = event.getUsername();
        this.email = event.getEmail();
        this.password = event.getPassword();
    }

    @CommandHandler
    public void handle(UpdateUserCommand command) {
        apply(new UserUpdatedEvent(command.getId(), command.getUsername(), command.getEmail()));
    }

    @EventSourcingHandler
    public void on(UserUpdatedEvent event) {
        this.username = event.getUsername();
        this.email = event.getEmail();
    }

    @CommandHandler
    public void handle(DeleteUserCommand command) {
        apply(new UserDeletedEvent(command.getId()));
    }

    @EventSourcingHandler
    public void on(UserDeletedEvent event) {
        this.id = null;
        this.username = null;
        this.email = null;
        this.password = null;
    }
}
