package com.sporty.bookstore.domain.model.event;

/**
 * Defines contract for domain classes interested in processing domain event.
 *
 * @author Danilo Ambrosio
 */
public interface Listener<T extends DomainEvent> {

    void on(T event);

}
