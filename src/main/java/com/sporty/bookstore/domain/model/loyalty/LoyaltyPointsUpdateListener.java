package com.sporty.bookstore.domain.model.loyalty;

import com.sporty.bookstore.domain.model.event.DomainEvent;
import com.sporty.bookstore.domain.model.event.DomainEventType;
import com.sporty.bookstore.domain.model.event.Listener;
import com.sporty.bookstore.domain.model.event.Notifier;
import com.sporty.bookstore.domain.model.purchase.PurchaseProcessed;
import com.sporty.bookstore.infrastructure.DomainEventListener;
import com.sporty.bookstore.infrastructure.repository.LoyaltyData;
import com.sporty.bookstore.infrastructure.repository.LoyaltyRepository;

@DomainEventListener
public class LoyaltyPointsUpdateListener implements Listener<PurchaseProcessed> {

    private final LoyaltyRepository loyaltyRepository;

    public LoyaltyPointsUpdateListener(final Notifier domainEventNotifier,
                                       final LoyaltyRepository loyaltyRepository) {
        this.loyaltyRepository = loyaltyRepository;
        domainEventNotifier.subscribe(DomainEventType.PURCHASE_WAS_PROCESSED, this);
    }

    @Override
    public void on(final PurchaseProcessed event) {
        final String beneficiaryId = event.customerId;
        createIfNotExists(beneficiaryId);
        loyaltyRepository.findById(event.customerId)
                .map(LoyaltyData::toLoyalty)
                .ifPresent(loyalty -> {
                    loyalty.useFor(event.booksCoveredWithLoyaltyPoints());
                    loyalty.accumulate(event.booksPayed());
                    loyaltyRepository.save(LoyaltyData.from(loyalty));
                });
    }

    private void createIfNotExists(final String beneficiaryId) {
        if (!loyaltyRepository.existsById(beneficiaryId)) {
            final Loyalty loyalty = Loyalty.of(Beneficiary.identifiedBy(beneficiaryId));
            loyaltyRepository.save(LoyaltyData.from(loyalty));
        }
    }
}
