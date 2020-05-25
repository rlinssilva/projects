package com.altran.pet_monitor.domain.pets;

import java.util.Optional;
import java.util.UUID;

public interface Pets {

    Iterable<Pet> findAllByState(PetState state);

    Pet save(Pet pet);

    Optional<Pet> findById(UUID petId);
}
