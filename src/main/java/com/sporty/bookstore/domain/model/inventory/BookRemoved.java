package com.sporty.bookstore.domain.model.inventory;

import com.sporty.bookstore.domain.model.event.DomainEvent;
import com.sporty.bookstore.domain.model.event.DomainEventType;

public record BookRemoved(String bookId) implements DomainEvent {

    @Override
    public DomainEventType type() {
        return DomainEventType.BOOK_WAS_REMOVED;
    }
}
