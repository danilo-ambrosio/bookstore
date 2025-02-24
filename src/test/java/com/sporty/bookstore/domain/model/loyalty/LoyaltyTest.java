package com.sporty.bookstore.domain.model.loyalty;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class LoyaltyTest {

    @Test
    public void testThatLoyaltyPointsAreAccumulated() {
        final Loyalty loyalty = Loyalty.of(Beneficiary.identifiedBy("1"));
        loyalty.accumulate(20);
        loyalty.accumulate(35);
        Assertions.assertEquals(55, loyalty.points());
        Assertions.assertTrue(loyalty.cover(5));
        Assertions.assertFalse(loyalty.cover(6));
    }

    @Test
    public void testThatLoyaltyPointsAreUsed() {
        final Loyalty loyalty = Loyalty.accumulated(Beneficiary.identifiedBy("1"), Points.of(100));
        loyalty.useFor(7);
        Assertions.assertEquals(30, loyalty.points());
        Assertions.assertTrue(loyalty.cover(3));
        Assertions.assertFalse(loyalty.cover(4));
    }

    @Test
    public void testThatLoyaltyPointsAreInsufficient() {
        final Loyalty loyalty = Loyalty.accumulated(Beneficiary.identifiedBy("1"), Points.of(60));
        Assertions.assertThrows(InsufficientLoyaltyPointsException.class, () -> loyalty.useFor(7));
    }

}