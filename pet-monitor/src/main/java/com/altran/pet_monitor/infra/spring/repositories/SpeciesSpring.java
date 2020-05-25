package com.altran.pet_monitor.infra.spring.repositories;

import com.altran.pet_monitor.domain.pets.Specie;
import com.altran.pet_monitor.domain.pets.Species;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface SpeciesSpring extends Species, CrudRepository<Specie, UUID> {
}
