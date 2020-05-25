package com.altran.pet_monitor.infra.spring.repositories;

import com.altran.pet_monitor.domain.pets.Pet;
import com.altran.pet_monitor.domain.pets.Pets;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface PetsSpring extends Pets, CrudRepository<Pet, UUID> {

}
