package com.sporty.bookstore.domain.model.pricing;

import com.sporty.bookstore.domain.model.pricing.policy.DiscountPolicies;
import com.sporty.bookstore.infrastructure.DomainService;
import com.sporty.bookstore.infrastructure.repository.BookPricingRepository;

import java.util.List;
import java.util.Optional;

/**
 * This class is responsible for calculating the price of a book based on the wholesale discount.
 * It takes a list of wholesale discounts and calculates the total price by applying the discount
 * policy to each book in the list.
 *
 * @author Danilo Ambrosio
 */
@DomainService
public class WholesaleDiscountCalculator {

    private final DiscountPolicies discountPolicies;
    private final BookPricingRepository repository;

    public WholesaleDiscountCalculator(final DiscountPolicies discountPolicies,
                                       final BookPricingRepository repository) {
        this.discountPolicies = discountPolicies;
        this.repository = repository;
    }

    public Price price(final List<Wholesale> wholesales) {
        return wholesales.stream().map(sale -> {
            final Optional<Book> book =
                    repository.findById(sale.bookId.value())
                            .map(data -> data.toBook(discountPolicies::withName));

            if(book.isEmpty()) {
                //TODO: Define how to handle BookNotFoundException
                return Price.of(0);
            }

            return book.get().deductDiscount(sale.bookQuantity, sale.useLoyaltyPoints);
        }).reduce(Price.of(0), Price::sum);
    }
}
