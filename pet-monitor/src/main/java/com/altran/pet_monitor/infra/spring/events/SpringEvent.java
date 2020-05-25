package com.altran.pet_monitor.infra.spring.events;

import com.altran.pet_monitor.domain.events.DomainEvent;
import com.altran.pet_monitor.domain.events.EventContext;
import org.springframework.context.ApplicationEvent;

public class SpringEvent extends ApplicationEvent implements DomainEvent {

    private final DomainEvent event;

    public SpringEvent(DomainEvent event){
        super(event.message());
        this.event = event;
    }

    @Override
    public EventContext context() { return event.context(); }

    @Override
    public Class<DomainEvent> eventClass() { return (Class<DomainEvent>) event.getClass(); }

    @Override
    public String message() {
        return event.message();
    }

    @Override
    public Object additionalData() { return event.additionalData(); }

}
