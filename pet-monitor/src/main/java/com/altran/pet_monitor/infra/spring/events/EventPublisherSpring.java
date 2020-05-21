package com.altran.pet_monitor.infra.spring.events;

import com.altran.pet_monitor.domain.DomainEvent;
import com.altran.pet_monitor.domain.EventPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class EventPublisherSpring extends EventPublisher {

    @Autowired
    private ApplicationEventPublisher publisher;

    @Override
    public void send(DomainEvent event) {
        SpringEvent springEvent = new SpringEvent(event);
        publisher.publishEvent(springEvent);
    }
}
