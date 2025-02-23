package com.sporty.bookstore.infrastructure.api.resource.data;

import com.sporty.bookstore.domain.model.pricing.Book;
import com.sporty.bookstore.domain.model.pricing.BookId;
import com.sporty.bookstore.domain.model.pricing.Price;

public class PriceData {

    public final String bookId;
    public final double retailPrice;

    public static PriceData from(final Book book) {
        return new PriceData(book.bookId(), book.retailPrice());
    }

    public static PriceData from(final BookId bookId, final Price price) {
        return new PriceData(bookId.value(), price.value());
    }

    private PriceData(final String bookId,
                      final double retailPrice) {
        this.bookId = bookId;
        this.retailPrice = retailPrice;
    }

}
