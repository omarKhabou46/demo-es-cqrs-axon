package com.enset.escqrs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = {
        "com.enset.escqrs",
        "org.axonframework.eventsourcing.eventstore.jpa",
        "org.axonframework.eventhandling.tokenstore.jpa",
        "org.axonframework.modelling.saga.repository.jpa"
})
public class EsCqrsApplication {

    public static void main(String[] args) {
        SpringApplication.run(EsCqrsApplication.class, args);
    }

}
