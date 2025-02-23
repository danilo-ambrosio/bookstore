package com.sporty.bookstore.usecase.inventory;

import com.sporty.bookstore.domain.model.inventory.*;
import com.sporty.bookstore.infrastructure.UseCase;
import com.sporty.bookstore.infrastructure.repository.BookData;
import com.sporty.bookstore.infrastructure.repository.BookInventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@UseCase
public class BookInventoryUseCase {

    private final BookInventoryRepository bookInventoryRepository;

    public BookInventoryUseCase(@Autowired final BookInventoryRepository bookInventoryRepository) {
        this.bookInventoryRepository = bookInventoryRepository;
    }

    public Book inventory(final ISBN isbn,
                          final Title title,
                          final StockQuantity stockQuantity,
                          final List<Author> authors,
                          final List<Genre> genres) {
        final Book inventoriedBook =
                Book.inventory(isbn, title, stockQuantity, authors, genres);

        this.bookInventoryRepository.save(BookData.from(inventoriedBook));

        return inventoriedBook;
    }
}
