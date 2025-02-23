package com.sporty.bookstore.usecase.pricing;

import com.sporty.bookstore.domain.model.pricing.Price;
import com.sporty.bookstore.domain.model.pricing.Wholesale;
import com.sporty.bookstore.domain.model.pricing.WholesaleDiscountCalculator;
import com.sporty.bookstore.infrastructure.UseCase;

import java.util.List;

@UseCase
public class WholesalePricingCalculationUseCase {

    private final WholesaleDiscountCalculator calculator;

    public WholesalePricingCalculationUseCase(final WholesaleDiscountCalculator calculator) {
        this.calculator = calculator;
    }

    public Price calculate(final Wholesale wholesale) {
        return calculate(List.of(wholesale));
    }

    public Price calculate(final List<Wholesale> wholesales) {
        return calculator.price(wholesales);
    }

}
