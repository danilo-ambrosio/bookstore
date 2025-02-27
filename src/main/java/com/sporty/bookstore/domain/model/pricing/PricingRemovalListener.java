package com.sporty.bookstore.domain.model.pricing;

import com.sporty.bookstore.domain.model.event.DomainEventType;
import com.sporty.bookstore.domain.model.event.Listener;
import com.sporty.bookstore.domain.model.event.Notifier;
import com.sporty.bookstore.domain.model.inventory.BookRemoved;
import com.sporty.bookstore.infrastructure.DomainEventListener;
import com.sporty.bookstore.infrastructure.repository.BookPricingRepository;

@DomainEventListener
public class PricingRemovalListener implements Listener<BookRemoved> {

    private final BookPricingRepository bookPricingRepository;

    public PricingRemovalListener(final Notifier domainEventNotifier,
                                  final BookPricingRepository bookPricingRepository) {
        this.bookPricingRepository = bookPricingRepository;
        domainEventNotifier.subscribe(DomainEventType.BOOK_WAS_REMOVED, this);
    }

    @Override
    public void on(final BookRemoved event) {
        bookPricingRepository.deleteById(event.bookId());
    }
}
