package com.altran.pet_monitor;

import com.altran.pet_monitor.domain.EventPublisher;
import com.altran.pet_monitor.domain.devices.PhysicalDevice;
import com.altran.pet_monitor.infra.spring.events.SpringContext;

public abstract class Context {

    private static Class CONTEXT_IMPLEMENTATION = SpringContext.class;

    private static Context instance;

    protected abstract Object getBean(Class beanClass);

    public static Context instance() {
        if (instance == null) {
            instance = SpringContext.instance();
        }
        return instance;
    }

    public static EventPublisher eventPublisher() {
        return (EventPublisher) instance().getBean(EventPublisher.class);
    }

    public static PhysicalDevice physicalDevice() {
        return (PhysicalDevice) instance().getBean(PhysicalDevice.class);
    }
}
