package com.altran.pet_monitor.domain.events;

import com.altran.pet_monitor.domain.environments.Environment;

public class EnvironmentConditionsOutOfBounds implements DomainEvent<Environment, EnvironmentConditionsOutOfBounds> {

    private EventContext context;

    private Environment environment;

    private String message;

    EnvironmentConditionsOutOfBounds(EventContext context,
                                     Environment environment,
                                     String message) {
        this.context = context;
        this.environment = environment;
        this.message = message;
    }

    @Override
    public EventContext context() { return context; }

    @Override
    public Class<EnvironmentConditionsOutOfBounds> eventClass() { return EnvironmentConditionsOutOfBounds.class; }

    @Override
    public String message() {
        return message;
    }

    @Override
    public Environment additionalData() { return environment; }

}
