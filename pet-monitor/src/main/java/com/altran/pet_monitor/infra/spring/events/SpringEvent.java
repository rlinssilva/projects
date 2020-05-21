package com.altran.pet_monitor.infra.spring.events;

import com.altran.pet_monitor.domain.DomainEvent;
import org.springframework.context.ApplicationEvent;

public class SpringEvent extends ApplicationEvent implements DomainEvent {

    private final DomainEvent event;

    public SpringEvent(DomainEvent event){
        super(event.message());
        this.event = event;
    }
    
    @Override
    public String message() {
        return event.message();
    }

}
