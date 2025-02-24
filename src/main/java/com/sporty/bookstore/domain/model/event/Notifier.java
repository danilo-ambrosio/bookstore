package com.sporty.bookstore.domain.model.event;

/**
 * Defines a contract for the responsibility of propagating Domain Event
 * to each one of the interested listeners.
 *
 * @author Danilo Ambrosio
 */
public interface Notifier {

    void subscribe(DomainEventType eventType, Listener<? extends DomainEvent> listener);

    void notify(DomainEvent event);

}
