package com.altran.pet_monitor.infra.spring.events;

import com.altran.pet_monitor.domain.Context;
import com.altran.pet_monitor.domain.EventPublisher;
import com.altran.pet_monitor.unit.domain.environments.DeviceReads;
import com.altran.pet_monitor.unit.domain.environments.Environments;
import com.altran.pet_monitor.domain.pets.Pets;
import com.altran.pet_monitor.domain.pets.Species;
import com.altran.pet_monitor.infra.spring.repositories.DeviceReadsSpring;
import com.altran.pet_monitor.infra.spring.repositories.EnvironmentsSpring;
import com.altran.pet_monitor.infra.spring.repositories.PetsSpring;
import com.altran.pet_monitor.infra.spring.repositories.SpeciesSpring;
import com.altran.pet_monitor.util.Constants;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class SpringContext extends Context implements ApplicationContextAware {

    private static SpringContext instance;

    private static ApplicationContext applicationContext;

    private static Map<Class,Class> implementationMap = initializeImplMap();

    private static Map<Class, Class> initializeImplMap() {
        Map<Class,Class> ret = new HashMap<>();
        ret.put(Context.class,SpringContext.class);
        ret.put(EventPublisher.class, EventPublisherSpring.class);
        ret.put(Environments.class, EnvironmentsSpring.class);
        ret.put(DeviceReads.class, DeviceReadsSpring.class);
        ret.put(Pets.class, PetsSpring.class);
        ret.put(Species.class, SpeciesSpring.class);
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

        return applicationContext.getBean(
                        implementationMap.get(beanClass));
    }

    public static Context instance() {
        if (applicationContext == null) {
            return null;
        }
        if (instance == null) {
            instance = (SpringContext) applicationContext.getBean(SpringContext.class);
        }
        return instance;
    }
}
