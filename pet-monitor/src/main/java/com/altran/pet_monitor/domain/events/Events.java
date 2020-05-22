package com.altran.pet_monitor.domain.events;

import java.lang.reflect.InvocationTargetException;

public class Events {

    public static <T extends DomainEvent> T newInstance(Class<T> eventClass, String eventMessage) {
        try {
            return eventClass.getDeclaredConstructor(String.class).newInstance(eventMessage);
        } catch (InstantiationException | IllegalAccessException |
                InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

}
