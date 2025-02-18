package com.lcmartins.domain.general;

public abstract class ComparableValue<T extends Comparable<T>> {
    private final T value;

    public ComparableValue(T value) {
        this.value = value;
    }

    public boolean isEqual(T otherValue) {
        return value.compareTo(otherValue) == 0;
    }

    public boolean isLessThan(T otherValue) {
        return value.compareTo(otherValue) < 0;
    }

    public boolean isGreaterThan(T otherValue) {
        return value.compareTo(otherValue) > 0;
    }
}