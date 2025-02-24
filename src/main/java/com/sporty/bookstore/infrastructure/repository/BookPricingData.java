package com.sporty.bookstore.infrastructure.repository;

import com.sporty.bookstore.domain.model.pricing.Book;
import com.sporty.bookstore.domain.model.pricing.BookId;
import com.sporty.bookstore.domain.model.pricing.Price;
import com.sporty.bookstore.domain.model.pricing.policy.DiscountPolicy;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.convert.ValueConverter;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "book_pricing")
public class BookPricingData {

    @Id
    public final String bookId;
    public final double retailPrice;
    @ValueConverter(DiscountPolicyConverter.class)
    public final DiscountPolicy discountPolicy;

    public static BookPricingData from(final Book book) {
        return new BookPricingData(book.bookId(), book.retailPrice(), book.discountPolicy());
    }

    @PersistenceCreator
    public BookPricingData(String bookId, double retailPrice, DiscountPolicy discountPolicy) {
        this.bookId = bookId;
        this.retailPrice = retailPrice;
        this.discountPolicy = discountPolicy;
    }

    public Book toBook() {
        return Book.price(BookId.of(bookId), Price.of(retailPrice), discountPolicy);
    }
}
