package com.altran.pet_monitor.domain;

import com.altran.pet_monitor.unit.domain.environments.DeviceReads;
import com.altran.pet_monitor.unit.domain.environments.Environments;
import com.altran.pet_monitor.domain.pets.Pets;
import com.altran.pet_monitor.domain.pets.Species;
import com.altran.pet_monitor.infra.spring.events.SpringContext;

public abstract class Context<T extends Context>  {

    private static Context instance;

    public static Context instance() {
        if (instance == null) {
            instance = SpringContext.instance();
        }
        return instance;
    }

    private static EventPublisher eventPublisher;

    private static Environments environments;

    private static DeviceReads deviceReads;

    private static Species species;

    private static Pets pets;

    protected abstract Object getBean(Class beanClass);

    public static EventPublisher eventPublisher() {
        if (eventPublisher == null) {
            eventPublisher = (EventPublisher) instance().getBean(EventPublisher.class);
        }
        return eventPublisher;
    }

    public static Environments environments() {
        if (environments == null) {
            environments = (Environments) instance().getBean(Environments.class);
        }
        return environments;
    }

    public static DeviceReads deviceReads() {
        if (deviceReads == null) {
            deviceReads = (DeviceReads) instance().getBean(DeviceReads.class);
        }
        return deviceReads;
    }
    public static Species species() {
        if (species == null) {
            species = (Species) instance().getBean(Species.class);
        }
        return species;
    }

    public static Pets pets() {
        if (pets == null) {
            pets = (Pets) instance().getBean(Species.class);
        }
        return pets;
    }

}
