package com.sporty.bookstore.infrastructure.api.resource;

import com.google.gson.reflect.TypeToken;
import com.sporty.bookstore.domain.model.pricing.BookId;
import com.sporty.bookstore.domain.model.pricing.Price;
import com.sporty.bookstore.infrastructure.api.resource.data.DiscountPolicyData;
import com.sporty.bookstore.infrastructure.api.resource.data.PriceData;
import com.sporty.bookstore.infrastructure.api.resource.data.PricingData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.ResultActions;

import static com.sporty.bookstore.domain.model.pricing.policy.DiscountPolicy.DiscountPolicyName.OLD_EDITION;
import static com.sporty.bookstore.domain.model.pricing.policy.DiscountPolicy.DiscountPolicyName.REGULAR_EDITION;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PricingResourceTest extends ResourceTest {

    @Test
    public void testThatBookIsPriced() throws Exception {
        final PricingData pricingData =
                PricingData.from(127.55, "REGULAR_EDITION");

        final ResultActions response =
                this.mockMvc.perform(post("/books/1/price").contentType(APPLICATION_JSON_VALUE)
                        .content(asJson(pricingData))).andExpect(status().isCreated());

        final PricingData responseData =
                toData(response, TypeToken.get(PricingData.class));

        Assertions.assertNotNull(responseData.bookId);
        Assertions.assertEquals(pricingData.retailPrice, responseData.retailPrice);
        Assertions.assertEquals(pricingData.discountPolicy, responseData.discountPolicy);
    }

    @Test
    public void testThatBookIsRepriced() throws Exception {
        final PricingData pricingData =
                PricingData.from(127.55, REGULAR_EDITION.name());

        this.mockMvc.perform(post("/books/1/price").contentType(APPLICATION_JSON_VALUE)
                .content(asJson(pricingData))).andExpect(status().isCreated());

        final PriceData priceData =
                PriceData.from(BookId.of("1"), Price.of(105.30));

        final ResultActions response =
                this.mockMvc.perform(patch("/books/1/price").contentType(APPLICATION_JSON_VALUE)
                        .content(asJson(priceData))).andExpect(status().isOk());

        final PriceData responseData =
                toData(response, TypeToken.get(PriceData.class));

        Assertions.assertNotNull(responseData.bookId);
        Assertions.assertEquals(priceData.retailPrice, responseData.retailPrice);
    }

    @Test
    public void testThatWholesalePriceIsAffectedByRepricingAndChangingDiscountPolicy() throws Exception {
        final BookId bookId = BookId.of("1");

        final PricingData pricingData =
                PricingData.from(50.1, REGULAR_EDITION.name());

        this.mockMvc.perform(post("/books/1/price").contentType(APPLICATION_JSON_VALUE)
                .content(asJson(pricingData))).andExpect(status().isCreated());

        final PriceData priceData =
                PriceData.from(bookId, Price.of(61.5));

        this.mockMvc.perform(patch("/books/1/price").contentType(APPLICATION_JSON_VALUE)
                .content(asJson(priceData))).andExpect(status().isOk());

        final DiscountPolicyData discountPolicyData =
                DiscountPolicyData.from(bookId.value(), OLD_EDITION.name());

        this.mockMvc.perform(patch("/books/1/price/discount-policy").contentType(APPLICATION_JSON_VALUE)
                .content(asJson(discountPolicyData))).andExpect(status().isOk());

        final ResultActions response =
                this.mockMvc.perform(get("/books/1/price?quantity=10")).andExpect(status().isOk());

        final PriceData responseData =
                toData(response, TypeToken.get(PriceData.class));

        Assertions.assertEquals(bookId.value(), responseData.bookId);
        Assertions.assertEquals(46.12, responseData.retailPrice);
    }

    @Test
    public void testThatWholesaleIsCoveredByLoyaltyProgram() throws Exception {
        final BookId bookId = BookId.of("1");

        final PricingData pricingData =
                PricingData.from(50.1, REGULAR_EDITION.name());

        this.mockMvc.perform(post("/books/" + bookId.value() + "/price").contentType(APPLICATION_JSON_VALUE)
                .content(asJson(pricingData))).andExpect(status().isCreated());

        final ResultActions response =
                this.mockMvc.perform(get("/books/1/price?quantity=3&useLoyaltyPoints=true")).andExpect(status().isOk());

        final PriceData responseData =
                toData(response, TypeToken.get(PriceData.class));

        Assertions.assertEquals(bookId.value(), responseData.bookId);
        Assertions.assertEquals(0, responseData.retailPrice);
    }
}
