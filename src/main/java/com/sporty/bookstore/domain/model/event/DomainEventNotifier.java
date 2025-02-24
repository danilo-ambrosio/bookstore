package com.sporty.bookstore.domain.model.event;

import com.sporty.bookstore.infrastructure.ThreadPool;

/**
 * The default implementation of {@link Notifier} asynchronously calls
 * listeners interested in {@link DomainEvent}.
 *
 * @author Danilo Ambrosio
 */
public class DomainEventNotifier extends Notifier {

    private final ThreadPool threadPool;

    public DomainEventNotifier(final ThreadPool threadPool) {
        this.threadPool = threadPool;
    }

    protected void callListener(final Listener listener, final DomainEvent event) {
        this.threadPool.execute(() -> listener.on(event));
    }
}
