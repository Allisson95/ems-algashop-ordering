package com.github.allisson95.algashop.ordering.domain.valueobject;

import com.github.allisson95.algashop.ordering.domain.validator.Validators;
import org.jspecify.annotations.NonNull;

import java.util.Objects;

public record Email(String value) {

    public Email {
        Objects.requireNonNull(value, "email cannot be null");
        if (value.isBlank()) {
            throw new IllegalArgumentException("email cannot be blank");
        }
        if (!Validators.isValidEmail(value)) {
            throw new IllegalArgumentException("%s is not a well-formed email address".formatted(value));
        }
    }

    @Override
    public @NonNull String toString() {
        return value;
    }

}
