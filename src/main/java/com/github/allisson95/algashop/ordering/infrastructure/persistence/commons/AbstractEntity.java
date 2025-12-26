package com.github.allisson95.algashop.ordering.infrastructure.persistence.commons;

import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PostLoad;
import jakarta.persistence.PostPersist;
import jakarta.persistence.Transient;
import org.jspecify.annotations.NonNull;
import org.springframework.data.domain.AfterDomainEventPublication;
import org.springframework.data.domain.DomainEvents;
import org.springframework.data.domain.Persistable;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

import static java.util.Objects.requireNonNull;

@MappedSuperclass
public abstract class AbstractEntity<PK extends Serializable> implements Persistable<PK> {

    @Transient
    private boolean isNew = true;

    @Transient
    private transient Supplier<List<Object>> domainEventSupplier = Collections::emptyList;

    @Transient
    private transient Runnable onAllEventsPublished = () -> {
    };

    @PostPersist
    @PostLoad
    void markNotNew() {
        this.isNew = false;
    }

    @Override
    public boolean isNew() {
        return this.isNew;
    }

    public void setDomainEventSupplier(final Supplier<List<Object>> domainEventSupplier) {
        requireNonNull(domainEventSupplier, "domainEventSupplier cannot be null");
        this.domainEventSupplier = domainEventSupplier;
    }

    public void setOnAllEventsPublished(final Runnable onAllEventsPublished) {
        requireNonNull(onAllEventsPublished, "onAllEventsPublished cannot be null");
        this.onAllEventsPublished = onAllEventsPublished;
    }

    /**
     * Clears all domain events currently held. Usually invoked by the infrastructure in place in Spring Data
     * repositories.
     */
    @AfterDomainEventPublication
    protected void clearDomainEvents() {
        this.onAllEventsPublished.run();
    }

    /**
     * All domain events currently captured by the aggregate.
     */
    @DomainEvents
    protected @NonNull Collection<Object> domainEvents() {
        return Collections.unmodifiableList(this.domainEventSupplier.get());
    }

}
