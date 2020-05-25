package com.altran.pet_monitor.domain.events;

public interface DomainEvent<T,T1 extends DomainEvent> {

    EventContext context();

    Class<T1> eventClass();

    String message();

    T additionalData();

}
