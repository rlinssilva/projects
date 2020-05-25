package com.altran.pet_monitor.domain.pets;

import com.altran.pet_monitor.domain.events.EventContext;
import com.altran.pet_monitor.domain.shared.Interval;
import com.altran.pet_monitor.util.Constants;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@Entity
@Getter
@Setter(AccessLevel.PACKAGE)
public class Pet {

    @Id
    private UUID id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "specie")
    private Specie specie;

    @Enumerated(EnumType.ORDINAL)
    private PetState state;

    private Interval<Integer> temperatureRange;

    Pet() {

    }

    public Pet(String name, Specie specie, Map<EventContext,Interval<Integer>>... thresholds) {

        if (name == null || name.trim().length() < 3) {
            throw new IllegalArgumentException(Constants.INVALID_INPUT_PARAMETERS);
        }

        if (specie == null){
            throw new IllegalArgumentException(Constants.INVALID_INPUT_PARAMETERS);
        }

        this.id = UUID.randomUUID();
        this.name = name;
        this.specie = specie;

        Map<EventContext,Interval<Integer>> petThresholds =
                (thresholds != null && thresholds.length > 0 && thresholds[0] != null) ?
                thresholds[0] : specie.thresholds();

        processThresholds(petThresholds);

    }

    public void processThresholds(Map<EventContext, Interval<Integer>> thresholds) {
        thresholds
                .keySet()
                .forEach(
                        ec -> processThreshold(
                                ec,
                                thresholds.get(ec)));
    }

    private void processThreshold(EventContext ec, Interval<Integer> threshold) {
        switch (ec) {
            case TEMPERATURE:
                this.temperatureRange = threshold;
                break;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pet pet = (Pet) o;
        return id.equals(pet.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Pet{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", specie=" + specie +
                ", temperatureRange=" + temperatureRange +
                '}';
    }

    public void adjustThreshold(Interval<Integer> threshold){
        if (threshold == null) {
            throw new IllegalArgumentException(Constants.INVALID_INPUT_PARAMETERS);
        }
        setTemperatureRange(threshold);
    }

    public void allocated() {
        state = PetState.ALLOCATED;
    }

    public void deallocated() {
        state = PetState.DEALLOCATED;
    }

    public void processSpecieThreshholds() {
        if (getSpecie() != null) {
            throw new IllegalStateException(Constants.INCONSISTENT_STATE);
        }
        processThresholds(getSpecie().thresholds());
    }
}
