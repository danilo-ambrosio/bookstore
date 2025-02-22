package com.sporty.bookstore.domain.model.pricing;

import com.sporty.bookstore.domain.model.pricing.policy.NewReleasePolicy;
import com.sporty.bookstore.domain.model.pricing.policy.OldEditionPolicy;
import com.sporty.bookstore.domain.model.pricing.policy.PolicyNotCoveredByLoyaltyProgramException;
import com.sporty.bookstore.domain.model.pricing.policy.RegularEditionPolicy;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BookTest {

    @Test
    public void testThatOldEditionIsPricedAndDiscountDeducted() {
        final Book book = Book.price(BookId.of("1"), Price.of(156.78), new OldEditionPolicy());
        Assertions.assertEquals(156.78, book.retailPrice());
        Assertions.assertEquals(Price.of(125.42), book.deductDiscount(2, false));
        Assertions.assertEquals(Price.of(117.58), book.deductDiscount(14, false));
        Assertions.assertEquals(Price.of(0), book.deductDiscount(1, true));
    }

    @Test
    public void testThatRegularEditionIsPricedAndDiscountDeducted() {
        final Book oldEdition = Book.price(BookId.of("1"), Price.of(34.11), new RegularEditionPolicy());
        Assertions.assertEquals(34.11, oldEdition.retailPrice());
        Assertions.assertEquals(Price.of(34.11), oldEdition.deductDiscount(2, false));
        Assertions.assertEquals(Price.of(30.70), oldEdition.deductDiscount(14, false));
        Assertions.assertEquals(Price.of(0), oldEdition.deductDiscount(1, true));
    }

    @Test
    public void testThatNewReleaseIsPricedAndDiscountNotDeducted() {
        final Book newRelease = Book.price(BookId.of("1"), Price.of(759.65), new NewReleasePolicy());
        Assertions.assertEquals(759.65, newRelease.retailPrice());
        Assertions.assertEquals(Price.of(759.65), newRelease.deductDiscount(80, false));
        Assertions.assertThrows(PolicyNotCoveredByLoyaltyProgramException.class, () -> newRelease.deductDiscount(1, true));
    }

}
