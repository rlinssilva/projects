package com.altran.pet_monitor.domain.events;

public class EnvironmentConditionsOutOfBounds implements DomainEvent {

    private String message;

    EnvironmentConditionsOutOfBounds(String message) {
        this.message = message;
    }

    @Override
    public String message() {
        return message;
    }
}
