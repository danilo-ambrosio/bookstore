package com.sporty.bookstore.domain.model.event;

/**
 * Domain events are used to notify interested parties about changes in the domain state.
 * It provides a way to encapsulate the event type and the source object that triggered the event.
 *
 * @author Danilo Ambrosio
 */
public interface DomainEvent {

    DomainEventType type();

}