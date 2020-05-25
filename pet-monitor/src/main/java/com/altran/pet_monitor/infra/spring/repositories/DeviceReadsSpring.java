package com.altran.pet_monitor.infra.spring.repositories;

import com.altran.pet_monitor.domain.environments.DeviceRead;
import com.altran.pet_monitor.domain.environments.DeviceReads;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface DeviceReadsSpring extends CrudRepository<DeviceRead, UUID>, DeviceReads {
}
