package com.lcmartins.domain.general;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public class Price extends ComparableValue<BigDecimal> {
    private final BigDecimal value;

    private Price(final BigDecimal value) {
        super(value);
        this.value = value.setScale(2, RoundingMode.HALF_EVEN);
    }

    public static Price with(final BigDecimal value) {
        return new Price(value);
    }

    public static Price with(final Long value) {
        return new Price(BigDecimal.valueOf(value));
    }

    public static Price withCalc(final Integer quantity, final BigDecimal rawPrice) {
        return new Price(BigDecimal.valueOf(quantity).multiply(rawPrice));
    }

    public BigDecimal getValue() {
        return value;
    }
    
    public boolean isValid() {
        return Objects.nonNull(value) && !this.isEqual(BigDecimal.ZERO) && !this.isLessThan(BigDecimal.valueOf(0));
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Price price)) return false;
        return Objects.equals(getValue(), price.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getValue());
    }
}
