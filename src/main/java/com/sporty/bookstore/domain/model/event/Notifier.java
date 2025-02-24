package com.sporty.bookstore.domain.model.event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Defines a contract for the responsibility of propagating Domain Event
 * to each one of the interested listeners.
 *
 * @author Danilo Ambrosio
 */
public abstract class Notifier {

    private final Map<DomainEventType, List<Listener<? extends DomainEvent>>> subscriptions = new HashMap<>();

    public void subscribe(DomainEventType eventType, Listener<? extends DomainEvent> listener) {
        subscriptions.computeIfAbsent(eventType, type -> new ArrayList<>()).add(listener);
    }

    public void notify(final DomainEvent event) {
        subscriptions.getOrDefault(event.type(), new ArrayList<>())
                .forEach(listener -> callListener(listener, event));
    }

    protected abstract void callListener(final Listener listener, final DomainEvent event);

}
