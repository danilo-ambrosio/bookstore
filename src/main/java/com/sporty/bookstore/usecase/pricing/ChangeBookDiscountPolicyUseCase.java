package com.sporty.bookstore.usecase.pricing;

import com.sporty.bookstore.domain.model.pricing.Book;
import com.sporty.bookstore.domain.model.pricing.BookId;
import com.sporty.bookstore.domain.model.pricing.policy.DiscountPolicies;
import com.sporty.bookstore.domain.model.pricing.policy.DiscountPolicy.DiscountPolicyName;
import com.sporty.bookstore.infrastructure.UseCase;
import com.sporty.bookstore.infrastructure.repository.BookPricingData;
import com.sporty.bookstore.infrastructure.repository.BookPricingRepository;
import com.sporty.bookstore.usecase.inventory.BookNotFoundException;

@UseCase
public class ChangeBookDiscountPolicyUseCase {

    private final DiscountPolicies discountPolicies;
    private final BookPricingRepository bookPricingRepository;

    public ChangeBookDiscountPolicyUseCase(final DiscountPolicies discountPolicies,
                                           final BookPricingRepository bookPricingRepository) {
        this.discountPolicies = discountPolicies;
        this.bookPricingRepository = bookPricingRepository;
    }

    public Book apply(final BookId bookId, final String discountPolicyName) {
        final Book book =
                bookPricingRepository.findById(bookId.value())
                        .orElseThrow(BookNotFoundException::new)
                        .toBook(discountPolicies::withName);

        final DiscountPolicyName newPolicyName =
                DiscountPolicyName.valueOf(discountPolicyName);

        book.apply(discountPolicies.withName(newPolicyName));

        bookPricingRepository.save(BookPricingData.from(book));

        return book;
    }

}
