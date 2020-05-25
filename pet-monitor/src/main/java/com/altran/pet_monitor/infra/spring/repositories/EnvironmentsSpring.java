package com.altran.pet_monitor.infra.spring.repositories;

import com.altran.pet_monitor.unit.domain.environments.Environment;
import com.altran.pet_monitor.unit.domain.environments.Environments;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface EnvironmentsSpring extends CrudRepository<Environment, UUID>, Environments  {

}
