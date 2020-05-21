package com.altran.pet_monitor.domain.profiles;

import com.altran.pet_monitor.domain.shared.Interval;
import com.altran.pet_monitor.util.Constants;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.tuple.Pair;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

@Entity
@Getter(AccessLevel.PACKAGE)
@Setter(AccessLevel.PACKAGE)
public class Profile {

    @Id
    private UUID id;

    private String description;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "minimum", column = @Column(name = "temperature_minimum")),
            @AttributeOverride(name = "maximum", column = @Column(name = "temperature_maximum"))
    })
    private Interval<Integer> temperature;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "minimum", column = @Column(name = "humidity_minimum")),
            @AttributeOverride(name = "maximum", column = @Column(name = "humidity_maximum"))
    })
    private Interval<Integer> humidity;

    void initialize() {
        this.id =  UUID.randomUUID();
    }

    Profile(){ }

    static Pair<Boolean, String> validateDescription(String description) {
        boolean ret = description != null && description.trim().length() >= 3;
        String value = ret ? description : Constants.INVALID_INPUT_PARAMETERS;
        return Pair.of(ret,value);
    }

    static void proceedValidation(Pair<Boolean, String> validation){
        if (validation == null || validation.getLeft() == null || validation.getRight() == null){
            throw new IllegalStateException(Constants.INCONSISTENT_INTERNAL_STATE);
        }
        if (!validation.getLeft()){
            throw new IllegalArgumentException(validation.getRight());
        }
    }

    public String description() { return description; }

    public void description(String description) {
        proceedValidation(validateDescription(description));
        setDescription(description);
    }

    public Interval<Integer> temperature() {
        return temperature;
    }

    public void temperature(Interval<Integer> range) {

        if (range == null){
            throw new IllegalArgumentException(Constants.INVALID_INPUT_PARAMETERS);
        }

        setTemperature(range);
    }

    public Interval<Integer> humidity() {
        return humidity;
    }

    public void humidity(Interval<Integer> range){

        if (range == null){
            throw new IllegalArgumentException(Constants.INVALID_INPUT_PARAMETERS);
        }

        setHumidity(range);
    }

    public boolean acceptableConditions(int temperature, int humidity) {
        return this.temperature.contains(temperature) &&
                this.humidity.contains(humidity);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Profile profile = (Profile) o;
        return id.equals(profile.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Profile{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", temperature=" + temperature +
                ", humidity=" + humidity +
                '}';
    }

    public static Profile newInstance(String descricao, Interval<Integer> temperatura,
                                      Interval<Integer> humidade){

        proceedValidation(validateDescription(descricao));

        if (temperatura == null) {
            throw new IllegalArgumentException(Constants.INVALID_INPUT_PARAMETERS);
        }

        if (humidade == null) {
            throw new IllegalArgumentException(Constants.INVALID_INPUT_PARAMETERS);
        }

        Profile profile = new Profile();
        profile.initialize();
        profile.description(descricao);
        profile.temperature(temperatura);
        profile.humidity(humidade);

        return profile;
    }


}
