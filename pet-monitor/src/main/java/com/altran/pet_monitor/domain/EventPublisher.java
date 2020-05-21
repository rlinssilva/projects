package com.altran.pet_monitor.domain;

public abstract class EventPublisher {

    public abstract void send (DomainEvent event);

}
