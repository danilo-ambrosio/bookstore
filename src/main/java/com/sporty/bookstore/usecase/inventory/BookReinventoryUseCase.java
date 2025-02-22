package com.sporty.bookstore.usecase.inventory;

import com.sporty.bookstore.domain.model.inventory.*;
import com.sporty.bookstore.infrastructure.repository.BookData;
import com.sporty.bookstore.infrastructure.repository.BookInventoryRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookReinventoryUseCase {

    private final BookInventoryRepository bookInventoryRepository;

    public BookReinventoryUseCase(final BookInventoryRepository bookInventoryRepository) {
        this.bookInventoryRepository = bookInventoryRepository;
    }

    public Book reinventory(final BookId bookId,
                            final ISBN isbn,
                            final Title title,
                            final StockQuantity stockQuantity,
                            final List<Author> authors,
                            final List<Genre> genres) {
        final Book inventoriedBook =
                this.bookInventoryRepository.findById(bookId.value())
                        .orElseThrow(BookNotFoundException::new)
                        .toBook();

        inventoriedBook.reinventory(isbn, title, stockQuantity, authors, genres);

        this.bookInventoryRepository.save(BookData.from(inventoriedBook));

        return inventoriedBook;
    }
}
