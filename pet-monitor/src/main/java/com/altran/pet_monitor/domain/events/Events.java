package com.altran.pet_monitor.domain.events;

import java.lang.reflect.InvocationTargetException;

public class Events {

    public static <T1 extends DomainEvent, T2 extends Object> T1 newInstance(
            Class<T1> eventClass, EventContext context, String eventMessage,
            T2 additionalData) {

        try {

            return eventClass

                    .getDeclaredConstructor(
                            EventContext.class,
                            additionalData.getClass(),
                            String.class)

                    .newInstance(
                            context,
                            additionalData,
                            eventMessage);

        } catch (InstantiationException |
                IllegalAccessException |
                InvocationTargetException |
                NoSuchMethodException e) {

            throw new RuntimeException(e);

        }

    }

}
