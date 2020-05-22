package com.altran.pet_monitor.domain;

import com.altran.pet_monitor.domain.events.DomainEvent;

public abstract class EventPublisher {

    public abstract void send (DomainEvent event);

}
