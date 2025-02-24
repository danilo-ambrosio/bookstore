package com.sporty.bookstore.usecase.purchase;

import com.sporty.bookstore.domain.model.event.Notifier;
import com.sporty.bookstore.domain.model.inventory.BookAvailability;
import com.sporty.bookstore.domain.model.inventory.BookId;
import com.sporty.bookstore.domain.model.inventory.StockQuantity;
import com.sporty.bookstore.domain.model.loyalty.Beneficiary;
import com.sporty.bookstore.domain.model.loyalty.LoyaltyCoverage;
import com.sporty.bookstore.domain.model.pricing.Price;
import com.sporty.bookstore.domain.model.pricing.PriceReviewer;
import com.sporty.bookstore.domain.model.purchase.*;
import com.sporty.bookstore.infrastructure.UseCase;
import com.sporty.bookstore.infrastructure.repository.PurchaseData;
import com.sporty.bookstore.infrastructure.repository.PurchaseRepository;

import java.util.List;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;

@UseCase
public class PurchaseUseCase {

    private final BookAvailability bookAvailability;
    private final PurchaseRepository purchaseRepository;
    private final PriceReviewer priceReviewer;
    private final LoyaltyCoverage loyaltyCoverage;
    private final Notifier domainEventNotifier;

    public PurchaseUseCase(final BookAvailability bookAvailability,
                           final PurchaseRepository purchaseRepository,
                           final PriceReviewer priceReviewer,
                           final LoyaltyCoverage loyaltyCoverage,
                           final Notifier domainEventNotifier) {
        this.bookAvailability = bookAvailability;
        this.purchaseRepository = purchaseRepository;
        this.priceReviewer = priceReviewer;
        this.loyaltyCoverage = loyaltyCoverage;
        this.domainEventNotifier = domainEventNotifier;
    }

    public Purchase process(final Customer customer,
                            final List<PaymentDetail> paymentDetails) {
        checkBookAvailability(paymentDetails);
        checkLoyaltyCoverage(customer, paymentDetails);

        final Payments payments =
                reviewPrices(paymentDetails);

        final Purchase purchase =
                Purchase.process(customer, payments);

        purchaseRepository.save(PurchaseData.from(purchase));
        domainEventNotifier.notify(new PurchaseProcessed(customer, payments));
        return purchase;
    }

    private void checkBookAvailability(final List<PaymentDetail> paymentDetails) {
        paymentDetails.forEach(purchasedBook -> {
            final BookId bookId = BookId.of(purchasedBook.bookId());
            final StockQuantity stockQuantity = StockQuantity.of(purchasedBook.quantity());
            bookAvailability.check(bookId, stockQuantity);
        });
    }

    private void checkLoyaltyCoverage(final Customer customer,
                                      final List<PaymentDetail> paymentDetails) {
        final Beneficiary beneficiary = Beneficiary.identifiedBy(customer.id());
        paymentDetails.forEach(detail -> loyaltyCoverage.check(beneficiary, detail));
    }

    private Payments reviewPrices(final List<PaymentDetail> paymentDetails) {
        return paymentDetails.stream().map(purchasedBook -> {
                final Price price = priceReviewer.review(purchasedBook);
                return purchasedBook.adjustPrice(price);
            }).collect(collectingAndThen(toList(), Payments::of));
    }
}

