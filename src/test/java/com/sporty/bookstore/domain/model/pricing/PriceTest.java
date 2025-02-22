package com.sporty.bookstore.domain.model.pricing;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PriceTest {

    @Test
    public void testThatTwoPricesAreAdded() {
        final Price originalPrice = Price.of(55.33);
        final Price additionalPrice = Price.of(13.67);
        final Price totalPrice = originalPrice.sum(additionalPrice);
        Assertions.assertEquals(Price.of(69), totalPrice);
        Assertions.assertNotEquals(Price.of(69.01), totalPrice);
    }

    @Test
    public void testThatDiscountIsDeducted() {
        final Price originalPrice = Price.of(1232.33);
        final Discount discount = Discount.deduct(15.5);
        final Price priceWithDiscount = originalPrice.off(discount);
        Assertions.assertEquals(Price.of(1041.32), priceWithDiscount);
    }
}
