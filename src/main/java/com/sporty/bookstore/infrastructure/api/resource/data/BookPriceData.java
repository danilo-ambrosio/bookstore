package com.sporty.bookstore.infrastructure.api.resource.data;

import com.sporty.bookstore.domain.model.pricing.Book;
import com.sporty.bookstore.domain.model.pricing.BookId;
import com.sporty.bookstore.domain.model.pricing.Price;

public class BookPriceData {

    public final String bookId;
    public final double retailPrice;

    public static BookPriceData from(final Book book) {
        return new BookPriceData(book.bookId(), book.retailPrice());
    }

    public static BookPriceData from(final BookId bookId, final Price price) {
        return new BookPriceData(bookId.value(), price.value.doubleValue());
    }

    private BookPriceData(final String bookId,
                          final double retailPrice) {
        this.bookId = bookId;
        this.retailPrice = retailPrice;
    }

}
