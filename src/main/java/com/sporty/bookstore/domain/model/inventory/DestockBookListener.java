package com.sporty.bookstore.domain.model.inventory;

import com.sporty.bookstore.domain.model.event.Listener;
import com.sporty.bookstore.domain.model.event.Notifier;
import com.sporty.bookstore.domain.model.purchase.PurchaseProcessed;
import com.sporty.bookstore.infrastructure.DomainEventListener;
import com.sporty.bookstore.infrastructure.repository.BookInventoryData;
import com.sporty.bookstore.infrastructure.repository.BookInventoryRepository;

import static com.sporty.bookstore.domain.model.event.DomainEventType.PURCHASE_WAS_PROCESSED;

@DomainEventListener
public class DestockBookListener implements Listener<PurchaseProcessed> {

    private final BookInventoryRepository bookInventoryRepository;

    public DestockBookListener(final BookInventoryRepository bookInventoryRepository,
                               final Notifier domainEventNotifier) {
        this.bookInventoryRepository = bookInventoryRepository;
        domainEventNotifier.subscribe(PURCHASE_WAS_PROCESSED, this);
    }

    @Override
    public void on(final PurchaseProcessed event) {
        event.paymentDetails().forEach(detail -> destock(detail.bookId(), detail.quantity()));
    }

    private void destock(final String bookId, final int quantity) {
        bookInventoryRepository.findById(bookId)
                .map(BookInventoryData::toBook)
                .ifPresent(book -> {
                    book.destock(StockQuantity.of(quantity));
                    bookInventoryRepository.save(BookInventoryData.from(book));
                });
    }
}
