package com.sporty.bookstore.usecase.pricing;

import com.sporty.bookstore.domain.model.pricing.Book;
import com.sporty.bookstore.domain.model.pricing.BookId;
import com.sporty.bookstore.domain.model.pricing.Price;
import com.sporty.bookstore.domain.model.pricing.policy.DiscountPolicies;
import com.sporty.bookstore.domain.model.pricing.policy.DiscountPolicy;
import com.sporty.bookstore.domain.model.pricing.policy.DiscountPolicy.DiscountPolicyName;
import com.sporty.bookstore.infrastructure.UseCase;
import com.sporty.bookstore.infrastructure.repository.BookPricingData;
import com.sporty.bookstore.infrastructure.repository.BookPricingRepository;

@UseCase
public class BookPricingUseCase {

    private final DiscountPolicies discountPolicies;
    private final BookPricingRepository bookPricingRepository;

    public BookPricingUseCase(final DiscountPolicies discountPolicies, final BookPricingRepository bookPricingRepository) {
        this.discountPolicies = discountPolicies;
        this.bookPricingRepository = bookPricingRepository;
    }

    public Book price(final BookId bookId, final Price retailPrice, final String discountPolicyName) {
        final DiscountPolicyName name =
                DiscountPolicyName.valueOf(discountPolicyName);

        final DiscountPolicy policy =
                discountPolicies.withName(name);

        final Book book =
                Book.price(bookId, retailPrice, policy);

        bookPricingRepository.save(BookPricingData.from(book));

        return book;
    }

}
