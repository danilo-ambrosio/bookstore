package com.sporty.bookstore.usecase.inventory;

import com.sporty.bookstore.domain.model.inventory.BookId;
import com.sporty.bookstore.infrastructure.UseCase;
import com.sporty.bookstore.infrastructure.repository.BookInventoryRepository;

@UseCase
public class BookInventoryRemovalUseCase {

    private final BookInventoryRepository bookInventoryRepository;

    public BookInventoryRemovalUseCase(BookInventoryRepository bookInventoryRepository) {
        this.bookInventoryRepository = bookInventoryRepository;
    }

    public void remove(final BookId bookId) {
        this.bookInventoryRepository.deleteById(bookId.value());
    }

}
