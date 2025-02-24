package com.sporty.bookstore.domain.model.loyalty;

import com.sporty.bookstore.domain.model.bundle.Bundle;
import com.sporty.bookstore.domain.model.pricing.Book;
import com.sporty.bookstore.domain.model.pricing.policy.PolicyNotCoveredByLoyaltyProgramException;
import com.sporty.bookstore.infrastructure.DomainService;
import com.sporty.bookstore.infrastructure.repository.BookPricingData;
import com.sporty.bookstore.infrastructure.repository.BookPricingRepository;
import com.sporty.bookstore.infrastructure.repository.LoyaltyData;
import com.sporty.bookstore.infrastructure.repository.LoyaltyRepository;
import com.sporty.bookstore.usecase.inventory.BookNotFoundException;

@DomainService
public class LoyaltyCoverage {

    private final LoyaltyRepository loyaltyRepository;
    private final BookPricingRepository pricingRepository;

    public LoyaltyCoverage(final LoyaltyRepository loyaltyRepository,
                           final BookPricingRepository pricingRepository) {
        this.loyaltyRepository = loyaltyRepository;
        this.pricingRepository = pricingRepository;
    }

    public void check(final Beneficiary beneficiary, final Bundle bundle) {
        final Book book =
                pricingRepository.findById(bundle.bookId())
                        .map(BookPricingData::toBook)
                        .orElseThrow(BookNotFoundException::new);

        if(bundle.useLoyaltyPoints()) {
            if(!book.canUseLoyaltyPoints()) {
                throw new PolicyNotCoveredByLoyaltyProgramException();
            }
            if(!hasSufficientPoints(beneficiary, bundle)) {
                throw new InsufficientLoyaltyPointsException();
            }
        }
     }

     public boolean hasSufficientPoints(final Beneficiary beneficiary, final Bundle bundle) {
        final int purchasedBooks = bundle.quantity();
        return loyaltyRepository.findById(beneficiary.id())
                .map(LoyaltyData::toLoyalty)
                .map(loyalty -> loyalty.cover(purchasedBooks))
                .orElse(false);
     }

}
