package com.altran.pet_monitor.infra.spring.events;

import com.altran.pet_monitor.Context;
import com.altran.pet_monitor.domain.EventPublisher;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class SpringContext extends Context implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (SpringContext.applicationContext == null) {
            SpringContext.applicationContext = applicationContext;
        }
    }

    private Object getBean(Class beanClass) {
        return applicationContext.getBean(beanClass);
    }

    @Override
    public EventPublisher eventPublisher() {
        return (EventPublisher) getBean(EventPublisherSpring.class);
    }

    public static Context instance() {
        return applicationContext.getBean(SpringContext.class);
    }
}
