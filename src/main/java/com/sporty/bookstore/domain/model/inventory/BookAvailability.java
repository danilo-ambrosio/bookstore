package com.sporty.bookstore.domain.model.inventory;

import com.sporty.bookstore.infrastructure.DomainService;
import com.sporty.bookstore.infrastructure.repository.BookData;
import com.sporty.bookstore.infrastructure.repository.BookInventoryRepository;

@DomainService
public class BookAvailability {

    private final BookInventoryRepository inventoryRepository;

    public BookAvailability(final BookInventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    public void check(BookId bookId, StockQuantity quantity) {
        inventoryRepository.findById(bookId.value())
                .map(BookData::toBook)
                .filter(book -> book.hasStock(quantity.quantity()))
                .orElseThrow(UnavailableBookException::new);
    }
}
