package com.altran.pet_monitor.domain.environments;

import com.altran.pet_monitor.domain.events.EnvironmentState;

import java.util.List;
import java.util.UUID;

public interface Environments {

    Environment findByDeviceId(UUID deviceId);

    Environment save(Environment environment);

    List<Environment> findAll();

    List<Environment> findByState(EnvironmentState state);
}
