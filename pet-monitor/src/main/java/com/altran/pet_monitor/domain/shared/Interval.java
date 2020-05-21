package com.altran.pet_monitor.domain.shared;

import com.altran.pet_monitor.util.Constants;
import lombok.Getter;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Embeddable
public class Interval<T extends Comparable> implements Serializable {

    private T minimum;

    private T maximum;

    Interval(T minimo, T maximo) {
        this.minimum = minimo;
        this.maximum = maximo;
    }

    public boolean contains(T value){

        if (value == null){
            throw new IllegalArgumentException(Constants.INVALID_INPUT_PARAMETERS);
        }

        return minimum.compareTo(value) <= 0 && maximum.compareTo(value) >= 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Interval<?> interval = (Interval<?>) o;
        return minimum.equals(interval.minimum) &&
                maximum.equals(interval.maximum);
    }

    @Override
    public int hashCode() {
        return Objects.hash(minimum, maximum);
    }

    @Override
    public String toString() {
        return "Interval{" +
                "minimum=" + minimum +
                ", maximum=" + maximum +
                '}';
    }

    public static <T extends Comparable> Interval<T> newInstance(T minimum, T maximum) {

        if (minimum == null) {
            throw new IllegalArgumentException(Constants.INVALID_INPUT_PARAMETERS);
        }

        if (maximum == null) {
            throw new IllegalArgumentException(Constants.INVALID_INPUT_PARAMETERS);
        }

        if (minimum.compareTo(maximum) > 0){
            throw new IllegalArgumentException(Constants.INVALID_INPUT_PARAMETERS);
        }

        return new Interval<>(minimum, maximum);
    }
}
