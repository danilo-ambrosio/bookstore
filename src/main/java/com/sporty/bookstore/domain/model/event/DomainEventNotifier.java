package com.sporty.bookstore.domain.model.event;

import com.sporty.bookstore.infrastructure.ThreadPool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DomainEventNotifier implements Notifier {

    private final ThreadPool threadPool;
    private final Map<DomainEventType, List<Listener<? extends DomainEvent>>> subscriptions = new HashMap<>();

    public DomainEventNotifier(final ThreadPool threadPool) {
        this.threadPool = threadPool;
    }

    @Override
    public void subscribe(final DomainEventType eventType, final Listener<? extends DomainEvent> listener) {
        this.subscriptions.computeIfAbsent(eventType, type -> new ArrayList<>()).add(listener);
    }

    @Override
    public void notify(final DomainEvent event) {
        this.subscriptions.getOrDefault(event.type(), new ArrayList<>())
            .forEach(listener -> callListener(listener, event));
    }

    private void callListener(final Listener listener, final DomainEvent event) {
        this.threadPool.execute(() -> listener.on(event));
    }
}
