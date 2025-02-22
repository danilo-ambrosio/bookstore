package com.sporty.bookstore.domain.model.pricing;

import java.math.BigDecimal;

public class Discount {

    public final BigDecimal percentage;

    public static Discount deduct(final double percentage) {
        return new Discount(percentage);
    }

    private Discount(final double percentage) {
        this.percentage = new BigDecimal(percentage);
    }

}
