package com.altran.pet_monitor.unit.domain.environments;

import com.altran.pet_monitor.domain.Context;
import com.altran.pet_monitor.domain.EventPublisher;
import com.altran.pet_monitor.domain.events.*;
import com.altran.pet_monitor.domain.pets.Pet;
import com.altran.pet_monitor.util.Constants;
import com.altran.pet_monitor.util.ExceptionUtils;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public class EnvironmentHandler {

    private EventPublisher eventPublisher;

    private Environments environments;

    private DeviceReads deviceReads;

    EventPublisher eventPublisher() {
        if (eventPublisher == null) {
            eventPublisher = Context.eventPublisher();
        }
        return eventPublisher;
    }

    Environments environments() {
        if (environments == null) {
            environments = Context.environments();
        }
        return environments;
    }

    DeviceReads deviceReads() {
        if (deviceReads == null) {
            deviceReads = Context.deviceReads();
        }
        return deviceReads;
    }

    public String handleDeviceIdRequest() {
        UUID deviceId = UUID.randomUUID();
        Environment newEnvironment = new Environment(deviceId);
        try {
            environments().save(newEnvironment);
            return deviceId.toString();
        } catch (Throwable t) {
            ExceptionUtils.printErrorMessage(t, "handling new device request.", null);
            throw new RuntimeException(t);
        }
    }

    public void handleDeviceRead(DeviceRead read) {

        if (read == null) {
            throw new IllegalArgumentException(Constants.INVALID_INPUT_PARAMETERS);
        }

        UUID deviceId = read.getDeviceId();
        Environment environment = environments().findByDeviceId(deviceId);

        if (environment == null) {
            throw new IllegalStateException(
                    Constants.INVALID_INPUT_PARAMETERS);
        }

        environment
                .check(read)
                .forEach((k,v)->{processEnvCheckResults(read,k,v);});

        eventPublisher()
                .send(
                        Events.newInstance(
                                EnvironmentConditionsUpdated.class,
                                EventContext.TEMPERATURE,
                                Constants.TEMPERATURE_UPDATED,
                                environment));

        try {
            deviceReads().save(read);
        } catch (Throwable t) {
            ExceptionUtils.printErrorMessage(t,"saving environment device read.", read.toString());
        }

    }

    private void processEnvCheckResults(DeviceRead read, EventContext context, Boolean result) {
        if (!result) {
            eventPublisher()
                    .send(
                            Events.newInstance(
                                    EnvironmentConditionsOutOfBounds.class,
                                    context,
                                    Constants.DEVICE_READ_OUT_OF_BOUNDS,
                                    read));
        }
    }

    public List<Environment> findAllDisabledEnvironments() {
        return environments()
                .findByState(EnvironmentState.DISABLED);
    }

    public List<Environment> findAllEnabledEnvironments() {
        return environments()
                .findByState(EnvironmentState.ENABLED);
    }

    public Environment setUp(UUID deviceId, String name, Set<Pet> pets) {

        if (deviceId == null) {
            throw new IllegalArgumentException(Constants.INVALID_INPUT_PARAMETERS);
        }

        Environment environment = environments().findByDeviceId(deviceId);

        if (environment == null) {
            throw new IllegalArgumentException(Constants.INVALID_INPUT_PARAMETERS);
        }

        try {
            environment.setUp(name,pets);
            return environments().save(environment);
        } catch (Throwable t) {
            ExceptionUtils
                    .printErrorMessage(
                            t,
                            "environment setup.",
                            environment.toString());
            throw new RuntimeException(t);
        }

    }



}
