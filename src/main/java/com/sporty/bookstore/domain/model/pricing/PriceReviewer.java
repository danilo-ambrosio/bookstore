package com.sporty.bookstore.domain.model.pricing;

import com.sporty.bookstore.domain.model.bundle.Bundle;
import com.sporty.bookstore.infrastructure.DomainService;
import com.sporty.bookstore.infrastructure.repository.BookPricingData;
import com.sporty.bookstore.infrastructure.repository.BookPricingRepository;

/**
 * This class is responsible for reviewing the price of books due to wholesale, if any,
 * or deducting discounts due to the use of loyalty points.
 *
 * @author Danilo Ambrosio
 */
@DomainService
public class PriceReviewer {

    private final BookPricingRepository repository;

    public PriceReviewer(final BookPricingRepository repository) {
        this.repository = repository;
    }

    public Price review(final Bundle bundle) {
        return repository.findById(bundle.bookId())
                .map(BookPricingData::toBook)
                .map(book -> book.deductDiscount(bundle.quantity(), bundle.useLoyaltyPoints()))
                .orElse(Price.of(0)); //TODO: Check how to handle BookNotFound
    }
}
