package com.sporty.bookstore.domain.model.purchase;

import com.sporty.bookstore.domain.model.pricing.Price;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.sporty.bookstore.domain.model.purchase.PaymentMethod.*;

public class PurchaseTest {

    @Test
    public void testThatPurchaseIsProcessed() {
        final List<PaymentDetail> details =
                List.of(PaymentDetail.of(BookId.of("1"), CREDIT_CARD, Quantity.of(3), Price.of(77.33)),
                        PaymentDetail.of(BookId.of("2"), BANK_TRANSFER, Quantity.of(1), Price.of(12.99)),
                        PaymentDetail.of(BookId.of("3"), DEBIT_CARD, Quantity.of(2), Price.of(5.01)));

        final Purchase purchase =
                Purchase.process(Customer.identifiedBy("1"), Payments.of(details));

        Assertions.assertNotNull(purchase.id());
        Assertions.assertEquals("1", purchase.customerId());
        Assertions.assertEquals(3, purchase.payments().size());
        Assertions.assertEquals(95.33, purchase.totalPrice());
        Assertions.assertEquals(95.33, purchase.totalPrice());
    }
}
