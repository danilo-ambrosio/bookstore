package com.sporty.bookstore.usecase.inventory;

import com.sporty.bookstore.domain.model.inventory.Book;
import com.sporty.bookstore.infrastructure.repository.BookData;
import com.sporty.bookstore.infrastructure.repository.BookInventoryRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookInventoryListUseCase {

    private final BookInventoryRepository bookInventoryRepository;

    public BookInventoryListUseCase(final BookInventoryRepository bookInventoryRepository) {
        this.bookInventoryRepository = bookInventoryRepository;
    }

    public List<Book> booksWithMinimumStockQuantity(final int minimumStockQuanity) {
        return this.bookInventoryRepository.findByStockQuantityGreaterThanEqual(minimumStockQuanity).stream().map(BookData::toBook).toList();
    }

}
