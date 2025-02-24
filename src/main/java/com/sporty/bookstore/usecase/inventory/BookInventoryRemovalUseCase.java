package com.sporty.bookstore.usecase.inventory;

import com.sporty.bookstore.domain.model.event.Notifier;
import com.sporty.bookstore.domain.model.inventory.BookId;
import com.sporty.bookstore.domain.model.inventory.BookRemoved;
import com.sporty.bookstore.infrastructure.UseCase;
import com.sporty.bookstore.infrastructure.repository.BookInventoryRepository;

@UseCase
public class BookInventoryRemovalUseCase {

    private final BookInventoryRepository bookInventoryRepository;
    private final Notifier domainEventNotifier;

    public BookInventoryRemovalUseCase(final BookInventoryRepository bookInventoryRepository,
                                       final Notifier domainEventNotifier) {
        this.bookInventoryRepository = bookInventoryRepository;
        this.domainEventNotifier = domainEventNotifier;
    }

    public void remove(final BookId bookId) {
        this.bookInventoryRepository.deleteById(bookId.value());
        this.domainEventNotifier.notify(new BookRemoved(bookId.value()));
    }

}
