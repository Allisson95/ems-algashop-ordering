package com.github.allisson95.algashop.ordering.domain.valueobject;

import lombok.Builder;

import java.util.Objects;

@Builder(toBuilder = true)
public record Recipient(FullName fullName, Document document, Phone phone) {

    public Recipient {
        Objects.requireNonNull(fullName, "fullName cannot be null");
        Objects.requireNonNull(document, "document cannot be null");
        Objects.requireNonNull(phone, "phone cannot be null");
    }

}
