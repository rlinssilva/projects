package com.altran.pet_monitor.domain.environments;

import com.altran.pet_monitor.domain.events.EnvironmentState;
import com.altran.pet_monitor.domain.events.EventContext;
import com.altran.pet_monitor.domain.pets.Pet;
import com.altran.pet_monitor.domain.shared.Interval;
import com.altran.pet_monitor.util.Constants;
import lombok.Getter;

import javax.persistence.*;
import java.util.*;

@Entity
@Getter
public class Environment {

    private static final Integer HIGHEST_TEMPERATURE = 100;
    private static final Integer LOWEST_TEMPERATURE = -100;

    @Id
    private UUID id;

    private String description;

    private UUID deviceId;

    @ManyToOne
    @JoinTable(
            name = "pet_environment",
            joinColumns = @JoinColumn(name = "environment_id"),
            inverseJoinColumns = @JoinColumn(name = "pet_id"))
    private Set<Pet> pets;

    @Enumerated(EnumType.ORDINAL)
    private EnvironmentState state;

    @Transient
    private Interval<Integer> temperatureRange;

    Environment() { }

    public Environment(UUID deviceId) {

        if (deviceId == null) {
            throw new IllegalArgumentException(Constants.INVALID_INPUT_PARAMETERS);
        }

        this.id = UUID.randomUUID();
        this.deviceId = deviceId;
        this.pets = new HashSet<>();
        this.state = EnvironmentState.DISABLED;
    }

    public void setUp(String name, Set<Pet> pets) {

        if (name == null || name.trim().length() < 3) {
            throw new IllegalArgumentException(Constants.INVALID_INPUT_PARAMETERS);
        }

        Set<Pet> removedPets = getRemovedPets(pets);

        if (pets == null) {
            pets = new HashSet<>();
        }

        this.description = name;
        this.pets = pets;
        this.state = EnvironmentState.ENABLED;

        setRemovedPetsAsDeallocated(removedPets);
        setPetsAsAllocated(pets);

        calculateEnvironmentRanges();
    }

    private Set<Pet> getRemovedPets(Set<Pet> pets) {
        Set<Pet> ret = new HashSet<>();
        ret.addAll(this.pets);
        ret.retainAll(pets);
        return ret;
    }

    private void setRemovedPetsAsDeallocated(Set<Pet> removedPets) {
        removedPets.forEach(pet -> pet.deallocated());
    }

    private void setPetsAsAllocated(Set<Pet> pets) {
        pets.forEach(pet -> pet.allocated());
    }

    private Interval<Integer> calculateEnvironmentRanges() {

        Integer lowBound = LOWEST_TEMPERATURE;
        Integer highBound = HIGHEST_TEMPERATURE;

        if (!pets.isEmpty()) {

            lowBound = pets
                    .stream()
                    .map(pet -> pet.getTemperatureRange().getMinimum())
                    .max(Comparator.<Integer>naturalOrder())
                    .orElseThrow(NoSuchElementException::new);

            highBound = pets
                    .stream()
                    .map(pet -> pet.getTemperatureRange().getMaximum())
                    .min(Comparator.<Integer>naturalOrder())
                    .orElseThrow(NoSuchElementException::new);

        }

        if (highBound < lowBound) {
            throw new IllegalStateException(Constants.INVALID_ENVIRONMENT_TEMPERATURE_RANGE);
        }

        return Interval.newInstance(lowBound, highBound);
    }

    public Set<Pet> getPets() {
        return Collections.unmodifiableSet(pets);
    }

    public void addPet(Pet pet) {
        if (pet == null) {return;}
        this.pets.add(pet);
        calculateEnvironmentRanges();
    }

    public void removePet(Pet pet) {
        if (!this.pets.contains(pet)) {
            return;
        }
        this.pets.remove(pet);
        calculateEnvironmentRanges();
    }

    public Map<EventContext,Boolean> check(DeviceRead read) {

        if (read == null) {
            throw new IllegalArgumentException(Constants.INVALID_INPUT_PARAMETERS);
        }

        Map<EventContext,Boolean> ret = new HashMap<>();
        ret.put(EventContext.TEMPERATURE, temperatureRange.contains(read.getTemperature()));
        return ret;
    }
}
