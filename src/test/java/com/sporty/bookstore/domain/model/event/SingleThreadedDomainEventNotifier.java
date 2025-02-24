package com.sporty.bookstore.domain.model.event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SingleThreadedDomainEventNotifier implements Notifier {

    private final Map<DomainEventType, List<Listener<? extends DomainEvent>>> subscriptions = new HashMap<>();

    @Override
    public void subscribe(final DomainEventType eventType, final Listener<? extends DomainEvent> listener) {
        subscriptions.computeIfAbsent(eventType, type -> new ArrayList<>()).add(listener);
    }

    @Override
    public void notify(final DomainEvent event) {
        subscriptions.getOrDefault(event.type(), new ArrayList<>()).forEach(listener -> listener.on(event));
    }
}
