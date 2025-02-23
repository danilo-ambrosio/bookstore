package com.sporty.bookstore.infrastructure.api.resource;

import com.sporty.bookstore.domain.model.pricing.Book;
import com.sporty.bookstore.domain.model.pricing.BookBundle;
import com.sporty.bookstore.domain.model.pricing.BookId;
import com.sporty.bookstore.domain.model.pricing.Price;
import com.sporty.bookstore.infrastructure.api.resource.data.DiscountPolicyData;
import com.sporty.bookstore.infrastructure.api.resource.data.PriceData;
import com.sporty.bookstore.infrastructure.api.resource.data.PricingData;
import com.sporty.bookstore.usecase.pricing.BookPricingUseCase;
import com.sporty.bookstore.usecase.pricing.BookRepricingUserCase;
import com.sporty.bookstore.usecase.pricing.ChangeBookDiscountPolicyUseCase;
import com.sporty.bookstore.usecase.pricing.PriceReviewUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("books/{id}/price")
public class PricingResource {

    private final BookPricingUseCase bookPricingUseCase;
    private final BookRepricingUserCase bookRepricingUserCase;
    private final ChangeBookDiscountPolicyUseCase changeBookDiscountPolicyUseCase;
    private final PriceReviewUseCase priceReviewUseCase;

    public PricingResource(final BookPricingUseCase bookPricingUseCase,
                           final BookRepricingUserCase bookRepricingUserCase,
                           final ChangeBookDiscountPolicyUseCase changeBookDiscountPolicyUseCase,
                           final PriceReviewUseCase priceReviewUseCase) {
        this.bookPricingUseCase = bookPricingUseCase;
        this.bookRepricingUserCase = bookRepricingUserCase;
        this.changeBookDiscountPolicyUseCase = changeBookDiscountPolicyUseCase;
        this.priceReviewUseCase = priceReviewUseCase;
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<PricingData> price(@PathVariable("id") final String id,
                                             @RequestBody final PricingData pricingData) {
        final BookId bookId = BookId.of(id);
        final Price retailPrice = Price.of(pricingData.retailPrice);
        final Book book =  bookPricingUseCase.price(bookId, retailPrice, pricingData.discountPolicy);
        return ResponseEntity.status(HttpStatus.CREATED).body(PricingData.from(book));
    }

    @PatchMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<PriceData> reprice(@PathVariable("id") final String id,
                                             @RequestBody final PriceData priceData) {
        final BookId bookId = BookId.of(id);
        final Price retailPrice = Price.of(priceData.retailPrice);
        final Book book = bookRepricingUserCase.reprice(bookId, retailPrice);
        return ResponseEntity.ok().body(PriceData.from(book));
    }

    @PatchMapping(path="/discount-policy", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<DiscountPolicyData> applyDiscountPolicy(@PathVariable("id") final String id,
                                                                  @RequestBody final DiscountPolicyData discountPolicyData) {
        final BookId bookId = BookId.of(id);
        final Book book = changeBookDiscountPolicyUseCase.apply(bookId, discountPolicyData.policyName);
        return ResponseEntity.ok().body(DiscountPolicyData.from(book.bookId(), book.discountPolicyName()));
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<PriceData> price(@PathVariable("id") final String id,
                                           @RequestParam(value = "quantity", required = false) final int quantity,
                                           @RequestParam(value = "useLoyaltyPoints", required = false) final boolean useLoyaltyPoints) {
        final BookId bookId = BookId.of(id);
        final BookBundle bookBundle = BookBundle.of(bookId, quantity, useLoyaltyPoints);
        final Price price = priceReviewUseCase.calculate(bookBundle);
        return ResponseEntity.ok().body(PriceData.from(bookId, price));
    }
}
