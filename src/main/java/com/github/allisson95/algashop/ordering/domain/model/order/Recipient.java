package com.github.allisson95.algashop.ordering.domain.model.order;

import com.github.allisson95.algashop.ordering.domain.model.commons.Document;
import com.github.allisson95.algashop.ordering.domain.model.commons.FullName;
import com.github.allisson95.algashop.ordering.domain.model.commons.Phone;
import lombok.Builder;

import static java.util.Objects.requireNonNull;

@Builder(toBuilder = true)
public record Recipient(FullName fullName, Document document, Phone phone) {

    public Recipient {
        requireNonNull(fullName, "fullName cannot be null");
        requireNonNull(document, "document cannot be null");
        requireNonNull(phone, "phone cannot be null");
    }

}
