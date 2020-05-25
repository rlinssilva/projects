package com.altran.pet_monitor.domain.events;

import com.altran.pet_monitor.domain.environments.Environment;

public class EnvironmentConditionsUpdated implements DomainEvent<Environment, EnvironmentConditionsUpdated> {

    private EventContext context;

    private Environment environment;

    private String message;

    EnvironmentConditionsUpdated(
            EventContext context,
            Environment environment,
            String message) {

        this.context = context;
        this.environment = environment;
        this.message = message;
    }

    @Override
    public EventContext context() {
        return context;
    }

    @Override
    public Class<EnvironmentConditionsUpdated> eventClass() {
        return EnvironmentConditionsUpdated.class;
    }

    @Override
    public String message() {
        return message;
    }

    @Override
    public Environment additionalData() {
        return environment;
    }
}
