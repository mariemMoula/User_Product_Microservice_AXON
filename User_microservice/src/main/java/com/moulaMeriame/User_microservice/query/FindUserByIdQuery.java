package com.moulaMeriame.User_microservice.query;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FindUserByIdQuery {
    private String id;
}
