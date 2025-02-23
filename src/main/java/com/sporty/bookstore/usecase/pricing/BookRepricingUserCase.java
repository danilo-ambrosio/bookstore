package com.sporty.bookstore.usecase.pricing;

import com.sporty.bookstore.domain.model.pricing.Book;
import com.sporty.bookstore.domain.model.pricing.BookId;
import com.sporty.bookstore.domain.model.pricing.Price;
import com.sporty.bookstore.domain.model.pricing.policy.DiscountPolicies;
import com.sporty.bookstore.infrastructure.UseCase;
import com.sporty.bookstore.infrastructure.repository.BookPricingData;
import com.sporty.bookstore.infrastructure.repository.BookPricingRepository;
import com.sporty.bookstore.usecase.inventory.BookNotFoundException;

@UseCase
public class BookRepricingUserCase {

    private final DiscountPolicies discountPolicies;
    private final BookPricingRepository bookPricingRepository;

    public BookRepricingUserCase(final DiscountPolicies discountPolicies, final BookPricingRepository bookPricingRepository) {
        this.discountPolicies = discountPolicies;
        this.bookPricingRepository = bookPricingRepository;
    }

    public Book reprice(final BookId bookId, final Price newPrice) {
        final Book book =
                bookPricingRepository.findById(bookId.value())
                        .orElseThrow(BookNotFoundException::new)
                        .toBook(discountPolicies::withName);

        book.reprice(newPrice);
        bookPricingRepository.save(BookPricingData.from(book));
        return book;
    }
}
