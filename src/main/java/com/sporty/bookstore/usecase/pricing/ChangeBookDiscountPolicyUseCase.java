package com.sporty.bookstore.usecase.pricing;

import com.sporty.bookstore.domain.model.pricing.Book;
import com.sporty.bookstore.domain.model.pricing.BookId;
import com.sporty.bookstore.domain.model.pricing.policy.DiscountPolicies;
import com.sporty.bookstore.infrastructure.UseCase;
import com.sporty.bookstore.infrastructure.repository.BookPricingData;
import com.sporty.bookstore.infrastructure.repository.BookPricingRepository;
import com.sporty.bookstore.usecase.inventory.BookNotFoundException;

@UseCase
public class ChangeBookDiscountPolicyUseCase {

    private final BookPricingRepository bookPricingRepository;
    private final DiscountPolicies discountPolicies;

    public ChangeBookDiscountPolicyUseCase(final BookPricingRepository bookPricingRepository,
                                           final DiscountPolicies discountPolicies) {
        this.bookPricingRepository = bookPricingRepository;
        this.discountPolicies = discountPolicies;
    }

    public Book apply(final BookId bookId, final String discountPolicyName) {
        final Book book =
                bookPricingRepository.findById(bookId.value())
                        .orElseThrow(BookNotFoundException::new)
                        .toBook();

        book.apply(discountPolicies.withName(discountPolicyName));
        bookPricingRepository.save(BookPricingData.from(book));
        return book;
    }

}
