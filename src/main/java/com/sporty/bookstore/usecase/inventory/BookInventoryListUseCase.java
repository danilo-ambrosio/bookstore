package com.sporty.bookstore.usecase.inventory;

import com.sporty.bookstore.domain.model.inventory.Book;
import com.sporty.bookstore.infrastructure.UseCase;
import com.sporty.bookstore.infrastructure.repository.BookInventoryData;
import com.sporty.bookstore.infrastructure.repository.BookInventoryRepository;

import java.util.List;

@UseCase
public class BookInventoryListUseCase {

    private final BookInventoryRepository bookInventoryRepository;

    public BookInventoryListUseCase(final BookInventoryRepository bookInventoryRepository) {
        this.bookInventoryRepository = bookInventoryRepository;
    }

    public List<Book> booksWithMinimumStockQuantity(final int minimumStockQuantity) {
        return this.bookInventoryRepository.findByStockQuantityGreaterThanEqual(minimumStockQuantity)
                .stream().map(BookInventoryData::toBook).toList();
    }

}
