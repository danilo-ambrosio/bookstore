package com.sporty.bookstore.domain.model.pricing;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

/**
 * This class represents a monetary value with a fixed scale and rounding mode.
 * Every monetary calculation is ultimately encapsulated by {@code Price} in order
 * to keep mathematical accuracy.
 *
 * @author  Danilo Ambrosio
 */
public class Price {

    private static final int MONETARY_SCALE = 2;
    private static final RoundingMode DEFAULT_ROUNDING = RoundingMode.HALF_UP;

    public final BigDecimal value;

    public static Price of(double value) {
        return new Price(new BigDecimal(value));
    }

    private Price(final BigDecimal value) {
        this.value = value.setScale(MONETARY_SCALE, DEFAULT_ROUNDING);
    }

    public Price sum(final Price price) {
        return new Price(this.value.add(price.value).setScale(MONETARY_SCALE, DEFAULT_ROUNDING));
    }

    public Price off(final Discount discount) {
        final BigDecimal hundred = new BigDecimal(100);
        final BigDecimal discountAmount = this.value.multiply(discount.percentage)
                .divide(hundred, MONETARY_SCALE, DEFAULT_ROUNDING);
        return new Price(this.value.subtract(discountAmount));
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof Price price)) return false;
        return Objects.equals(value, price.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }

    @Override
    public String toString() {
        return "Price{" +
                "value=" + value +
                '}';
    }
}
