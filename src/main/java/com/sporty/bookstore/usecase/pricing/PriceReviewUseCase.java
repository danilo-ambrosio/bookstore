package com.sporty.bookstore.usecase.pricing;

import com.sporty.bookstore.domain.model.pricing.BookBundle;
import com.sporty.bookstore.domain.model.pricing.Price;
import com.sporty.bookstore.domain.model.pricing.PriceReviewer;
import com.sporty.bookstore.infrastructure.UseCase;

@UseCase
public class PriceReviewUseCase {

    private final PriceReviewer reviewer;

    public PriceReviewUseCase(final PriceReviewer reviewer) {
        this.reviewer = reviewer;
    }

    public Price calculate(final BookBundle bookBundle) {
        return reviewer.review(bookBundle);
    }

}
