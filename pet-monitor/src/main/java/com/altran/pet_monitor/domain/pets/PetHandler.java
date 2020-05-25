package com.altran.pet_monitor.domain.pets;

import com.altran.pet_monitor.domain.Context;
import com.altran.pet_monitor.domain.events.EventContext;
import com.altran.pet_monitor.domain.shared.Interval;
import com.altran.pet_monitor.util.Constants;
import com.altran.pet_monitor.util.ExceptionUtils;
import com.google.common.collect.Lists;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class PetHandler {

    private Species species;

    private Pets pets;

    private Species species() {
        if (species == null) {
            species = Context.species();
        }
        return species;
    }

    private Pets pets() {
        if (pets == null) {
            pets = Context.pets();
        }
        return pets;
    }

    public List<Specie> findAllSpecies() {
        return Lists.newArrayList(species().findAll());
    }

    public List<Pet> findAllPetsOutOfEnvironment() {
        return Lists.newArrayList(
                pets().findAllByState(
                        PetState.DEALLOCATED));
    }

    public Pet newPet(String name, Specie specie,
                      Map<EventContext, Interval<Integer>> thresholds)  {

        if (name == null || name.trim().length() < 2) {
            throw new IllegalArgumentException(Constants.INVALID_INPUT_PARAMETERS);
        }

        if (specie == null) {
            throw new IllegalArgumentException(Constants.INVALID_INPUT_PARAMETERS);
        }

        Pet pet = null;
        try {
            pet = new Pet(name, specie, thresholds);
            return pets().save(pet);
        } catch(Throwable t) {
            ExceptionUtils.printErrorMessage(t, "new pet", null);
            throw new RuntimeException(t);
        }
    }

    public Pet updatePet(UUID petId, Map<EventContext, Interval<Integer>> thresholds) {

        if (petId == null) {
            throw new IllegalArgumentException(Constants.INVALID_INPUT_PARAMETERS);
        }

        Optional<Pet> optPet = pets().findById(petId);

        if (optPet.isEmpty()) {
            throw new IllegalArgumentException(Constants.INVALID_INPUT_PARAMETERS);
        }

        Pet pet = optPet.get();

        if (thresholds != null && !thresholds.isEmpty()){
            pet.processThresholds(thresholds);
        } else {
            pet.processSpecieThresholds();
        }

        try {
            return pets().save(pet);
        } catch (Throwable t) {
            ExceptionUtils.printErrorMessage(t, "pet update", pet.toString());
            throw new RuntimeException(t);
        }

    }
}
