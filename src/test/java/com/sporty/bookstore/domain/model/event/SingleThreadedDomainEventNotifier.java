package com.sporty.bookstore.domain.model.event;

public class SingleThreadedDomainEventNotifier extends Notifier {

    @Override
    protected void callListener(final Listener listener, final DomainEvent event) {
        listener.on(event);
    }

}
