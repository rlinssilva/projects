package com.altran.pet_monitor.infra.spring.events;

import com.altran.pet_monitor.Context;
import com.altran.pet_monitor.domain.EventPublisher;
import com.altran.pet_monitor.domain.devices.PhysicalDevice;
import com.altran.pet_monitor.util.Constants;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.HashMap;
import java.util.Map;

public class SpringContext extends Context implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    private static Map<Class,Class> implementationMap = initializeImplMap();

    private static Map<Class, Class> initializeImplMap() {
        Map<Class,Class> ret = new HashMap<>();
        ret.put(Context.class,SpringContext.class);
        ret.put(EventPublisher.class, EventPublisherSpring.class);
        ret.put(PhysicalDevice.class, null);
        return ret;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (SpringContext.applicationContext == null) {
            SpringContext.applicationContext = applicationContext;
        }
    }

    public Object getBean(Class beanClass) {

        if (!implementationMap.containsKey(beanClass)){
            throw new IllegalArgumentException(Constants.INVALID_INPUT_PARAMETERS);
        }
        return applicationContext.getBean(beanClass);
    }
}
