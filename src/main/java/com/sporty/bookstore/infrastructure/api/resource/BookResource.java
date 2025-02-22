package com.sporty.bookstore.infrastructure.api.resource;

import com.sporty.bookstore.domain.model.inventory.*;
import com.sporty.bookstore.infrastructure.api.resource.data.BookInventoryData;
import com.sporty.bookstore.usecase.inventory.BookInventoryListUseCase;
import com.sporty.bookstore.usecase.inventory.BookInventoryUseCase;
import com.sporty.bookstore.usecase.inventory.BookReinventoryUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * {@code BookInventoryResource} exposes {@link Book} resource operations via REST endpoints.
 *
 * @author Danilo Ambrosio
 */
@RestController
@RequestMapping("/books")
public class BookResource {

    private final BookInventoryUseCase bookInventoryUseCase;
    private final BookReinventoryUseCase bookReinventoryUseCase;
    private final BookInventoryListUseCase bookInventoryListUseCase;

    public BookResource(final BookInventoryUseCase bookInventoryUseCase,
                        final BookReinventoryUseCase bookReinventoryUseCase,
                        final BookInventoryListUseCase bookInventoryListUseCase) {
        this.bookInventoryUseCase = bookInventoryUseCase;
        this.bookReinventoryUseCase = bookReinventoryUseCase;
        this.bookInventoryListUseCase = bookInventoryListUseCase;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BookInventoryData> inventory(@RequestBody final BookInventoryData inventoryData) {
        final Title title = Title.of(inventoryData.title);
        final StockQuantity stockQuantity = StockQuantity.of(inventoryData.stockQuantity);
        final List<Author> authors = Author.names(inventoryData.authors);
        final List<Genre> genres = Genre.of(inventoryData.genres);

        final Book inventoriedBook =
                bookInventoryUseCase.inventory(ISBN.from(inventoryData.isbn), title, stockQuantity, authors, genres);

        return ResponseEntity.status(HttpStatus.CREATED).body(BookInventoryData.from(inventoriedBook));
    }

    @PutMapping(path = "/{bookId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BookInventoryData> reinventory(@PathVariable final String bookId,
                                                         @RequestBody final BookInventoryData inventoryData) {
        final ISBN isbn = ISBN.from(inventoryData.isbn);
        final Title title = Title.of(inventoryData.title);
        final StockQuantity stockQuantity = StockQuantity.of(inventoryData.stockQuantity);
        final List<Author> authors = Author.names(inventoryData.authors);
        final List<Genre> genres = Genre.of(inventoryData.genres);

        final Book inventoriedBook =
                bookReinventoryUseCase.reinventory(BookId.of(bookId), ISBN.from(inventoryData.isbn),
                        title, stockQuantity, authors, genres);

        return ResponseEntity.ok().body(BookInventoryData.from(inventoriedBook));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<BookInventoryData>> list() {
        final List<BookInventoryData> bookInventoryDataList =
                bookInventoryListUseCase.list().stream().map(BookInventoryData::from).toList();
        return ResponseEntity.ok().body(bookInventoryDataList);
    }
}
