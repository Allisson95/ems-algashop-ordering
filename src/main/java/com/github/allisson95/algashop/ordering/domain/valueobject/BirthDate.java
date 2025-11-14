package com.github.allisson95.algashop.ordering.domain.valueobject;

import org.jspecify.annotations.NonNull;

import java.time.Clock;
import java.time.LocalDate;
import java.util.Objects;

public record BirthDate(LocalDate value) {

    public BirthDate {
        Objects.requireNonNull(value, "birthDate cannot be null");
        if (value.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("birthDate cannot be in the future");
        }
    }

    public Integer age(final Clock ageCalculationClock) {
        return value.until(LocalDate.now(ageCalculationClock)).getYears();
    }

    @Override
    public @NonNull String toString() {
        return value.toString();
    }

}
