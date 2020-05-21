package com.altran.pet_monitor;

import com.altran.pet_monitor.domain.EventPublisher;
import com.altran.pet_monitor.infra.spring.events.SpringContext;

public abstract class Context {

    public abstract EventPublisher eventPublisher();

    public static Context instance() {
        return SpringContext.instance();
    }
}
