package com.sporty.bookstore.infrastructure.api.resource;

import com.sporty.bookstore.domain.model.inventory.*;
import com.sporty.bookstore.infrastructure.api.resource.data.InventoryData;
import com.sporty.bookstore.usecase.inventory.BookInventoryListUseCase;
import com.sporty.bookstore.usecase.inventory.BookInventoryRemovalUseCase;
import com.sporty.bookstore.usecase.inventory.BookInventoryUseCase;
import com.sporty.bookstore.usecase.inventory.BookReinventoryUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * {@code InventoryResource} exposes {@link Book} resource operations via REST endpoints.
 *
 * @author Danilo Ambrosio
 */
@RestController
@RequestMapping("/books")
public class InventoryResource {

    private final BookInventoryUseCase bookInventoryUseCase;
    private final BookReinventoryUseCase bookReinventoryUseCase;
    private final BookInventoryListUseCase bookInventoryListUseCase;
    private final BookInventoryRemovalUseCase bookInventoryRemovalUseCase;

    public InventoryResource(final BookInventoryUseCase bookInventoryUseCase,
                             final BookReinventoryUseCase bookReinventoryUseCase,
                             final BookInventoryListUseCase bookInventoryListUseCase,
                             final BookInventoryRemovalUseCase bookInventoryRemovalUseCase) {
        this.bookInventoryUseCase = bookInventoryUseCase;
        this.bookReinventoryUseCase = bookReinventoryUseCase;
        this.bookInventoryListUseCase = bookInventoryListUseCase;
        this.bookInventoryRemovalUseCase = bookInventoryRemovalUseCase;
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<InventoryData> inventory(@RequestBody final InventoryData inventoryData) {
        final Title title = Title.of(inventoryData.title);
        final StockQuantity stockQuantity = StockQuantity.of(inventoryData.stockQuantity);
        final List<Author> authors = Author.names(inventoryData.authors);
        final List<Genre> genres = Genre.of(inventoryData.genres);
        final Book inventoriedBook =
                bookInventoryUseCase.inventory(ISBN.from(inventoryData.isbn), title, stockQuantity, authors, genres);

        return ResponseEntity.status(HttpStatus.CREATED).body(InventoryData.from(inventoriedBook));
    }

    @PutMapping(path = "/{id}", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<InventoryData> reinventory(@PathVariable("id") final String id,
                                                     @RequestBody final InventoryData inventoryData) {
        final BookId bookId = BookId.of(id);
        final ISBN isbn = ISBN.from(inventoryData.isbn);
        final Title title = Title.of(inventoryData.title);
        final StockQuantity stockQuantity = StockQuantity.of(inventoryData.stockQuantity);
        final List<Author> authors = Author.names(inventoryData.authors);
        final List<Genre> genres = Genre.of(inventoryData.genres);
        final Book inventoriedBook =
                bookReinventoryUseCase.reinventory(bookId, isbn, title, stockQuantity, authors, genres);

        return ResponseEntity.ok().body(InventoryData.from(inventoriedBook));
    }

    @DeleteMapping(path = "/{id}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<InventoryData> remove(@PathVariable final String id) {
        bookInventoryRemovalUseCase.remove(BookId.of(id));
        return ResponseEntity.ok().build();
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<InventoryData>> list(@RequestParam(value = "available", required = false) final boolean available) {
        final List<Book> books = bookInventoryListUseCase.booksWithMinimumStockQuantity(available ? 1 : 0);
        return ResponseEntity.ok().body(books.stream().map(InventoryData::from).toList());
    }
}
