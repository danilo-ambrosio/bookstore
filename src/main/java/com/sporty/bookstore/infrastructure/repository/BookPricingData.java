package com.sporty.bookstore.infrastructure.repository;

import com.sporty.bookstore.domain.model.pricing.Book;
import com.sporty.bookstore.domain.model.pricing.BookId;
import com.sporty.bookstore.domain.model.pricing.Price;
import com.sporty.bookstore.domain.model.pricing.policy.DiscountPolicy;
import com.sporty.bookstore.domain.model.pricing.policy.DiscountPolicy.DiscountPolicyName;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.function.Function;

@Document(collection = "book_pricing")
public class BookPricingData {

    @Id
    public final String bookId;
    public final double retailPrice;
    public final String discountPolicy;

    public static BookPricingData from(final Book book) {
        return new BookPricingData(book.bookId(), book.retailPrice(), book.discountPolicyName());
    }

    @PersistenceCreator
    public BookPricingData(String bookId, double retailPrice, String discountPolicy) {
        this.bookId = bookId;
        this.retailPrice = retailPrice;
        this.discountPolicy = discountPolicy;
    }

    public Book toBook(final Function<DiscountPolicyName, DiscountPolicy> discountPolicyMapper) {
        final DiscountPolicyName policyName = DiscountPolicyName.valueOf(discountPolicy);
        return Book.price(BookId.of(bookId), Price.of(retailPrice), discountPolicyMapper.apply(policyName));
    }
}
