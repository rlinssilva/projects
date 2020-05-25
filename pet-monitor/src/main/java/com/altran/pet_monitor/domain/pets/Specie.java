package com.altran.pet_monitor.domain.pets;

import com.altran.pet_monitor.domain.events.EventContext;
import com.altran.pet_monitor.domain.shared.Interval;
import com.altran.pet_monitor.util.Constants;
import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@Entity
@Getter
public class Specie {

    @Id
    private UUID id;

    private String name;

    private Interval<Integer> temperatureRange;

    Specie() {

    }

    public Specie(String name, Interval<Integer> temperatureRange) {

        if (name == null && name.trim().length() < 3) {
            throw new IllegalArgumentException(Constants.INVALID_INPUT_PARAMETERS);
        }

        if (temperatureRange == null) {
            throw new IllegalArgumentException(Constants.INVALID_INPUT_PARAMETERS);
        }

        this.id = UUID.randomUUID();
        this.name = name;
        this.temperatureRange = temperatureRange;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Specie specie = (Specie) o;
        return id.equals(specie.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Specie{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", temperatureRange=" + temperatureRange +
                '}';
    }

    public Map<EventContext, Interval<Integer>> thresholds() {
        Map<EventContext, Interval<Integer>> ret = new HashMap<>();
        if (temperatureRange != null){
            ret.put(EventContext.TEMPERATURE, temperatureRange);
        }
        return ret;
    }
}
