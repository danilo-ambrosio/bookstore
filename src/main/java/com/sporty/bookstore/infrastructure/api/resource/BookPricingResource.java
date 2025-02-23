package com.sporty.bookstore.infrastructure.api.resource;

import com.sporty.bookstore.domain.model.pricing.Book;
import com.sporty.bookstore.domain.model.pricing.BookId;
import com.sporty.bookstore.domain.model.pricing.Price;
import com.sporty.bookstore.domain.model.pricing.Wholesale;
import com.sporty.bookstore.infrastructure.api.resource.data.BookDiscountPolicyData;
import com.sporty.bookstore.infrastructure.api.resource.data.BookPriceData;
import com.sporty.bookstore.infrastructure.api.resource.data.BookPricingData;
import com.sporty.bookstore.usecase.pricing.BookPricingUseCase;
import com.sporty.bookstore.usecase.pricing.BookRepricingUserCase;
import com.sporty.bookstore.usecase.pricing.ChangeBookDiscountPolicyUseCase;
import com.sporty.bookstore.usecase.pricing.WholesalePricingCalculationUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("books/{id}/price")
public class BookPricingResource {

    private final BookPricingUseCase bookPricingUseCase;
    private final BookRepricingUserCase bookRepricingUserCase;
    private final ChangeBookDiscountPolicyUseCase changeBookDiscountPolicyUseCase;
    private final WholesalePricingCalculationUseCase wholesalePricingCalculationUseCase;

    public BookPricingResource(final BookPricingUseCase bookPricingUseCase,
                               final BookRepricingUserCase bookRepricingUserCase,
                               final ChangeBookDiscountPolicyUseCase changeBookDiscountPolicyUseCase,
                               final WholesalePricingCalculationUseCase wholesalePricingCalculationUseCase) {
        this.bookPricingUseCase = bookPricingUseCase;
        this.bookRepricingUserCase = bookRepricingUserCase;
        this.changeBookDiscountPolicyUseCase = changeBookDiscountPolicyUseCase;
        this.wholesalePricingCalculationUseCase = wholesalePricingCalculationUseCase;
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<BookPricingData> price(@PathVariable("id") final String id,
                                               @RequestBody final BookPricingData bookPricingData) {
        final BookId bookId = BookId.of(id);
        final Price retailPrice = Price.of(bookPricingData.retailPrice);
        final Book book =  bookPricingUseCase.price(bookId, retailPrice, bookPricingData.discountPolicy);
        return ResponseEntity.status(HttpStatus.CREATED).body(BookPricingData.from(book));
    }

    @PatchMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<BookPriceData> reprice(@PathVariable("id") final String id,
                                                 @RequestBody final BookPriceData bookPriceData) {
        final BookId bookId = BookId.of(id);
        final Price retailPrice = Price.of(bookPriceData.retailPrice);
        final Book book = bookRepricingUserCase.reprice(bookId, retailPrice);
        return ResponseEntity.ok().body(BookPriceData.from(book));
    }

    @PatchMapping(path="/discount-policy", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<BookDiscountPolicyData> applyDiscountPolicy(@PathVariable("id") final String id,
                                                                      @RequestBody final BookDiscountPolicyData discountPolicyData) {
        final BookId bookId = BookId.of(id);
        final Book book = changeBookDiscountPolicyUseCase.apply(bookId, discountPolicyData.policyName);
        return ResponseEntity.ok().body(BookDiscountPolicyData.from(book.bookId(), book.discountPolicyName()));
    }

    /**
     * Calculate wholesale price for a book based on its current discount policy.
     * If none discount is applicable, retail price is returned.
     *
     * @param id bookId
     * @param quantity number of books to be purchased
     * @param useLoyaltyPoints true if customer wants to use loyalty points
     * @return BookPriceData
     */
    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<BookPriceData> priceWholesale(@PathVariable("id") final String id,
                                                        @RequestParam(value = "quantity", required = false) final int quantity,
                                                        @RequestParam(value = "useLoyaltyPoints", required = false) final boolean useLoyaltyPoints) {
        final BookId bookId = BookId.of(id);
        final Wholesale wholesale = Wholesale.of(BookId.of(id), quantity, useLoyaltyPoints);
        final Price price = wholesalePricingCalculationUseCase.calculate(wholesale);
        return ResponseEntity.ok().body(BookPriceData.from(bookId, price));
    }
}
