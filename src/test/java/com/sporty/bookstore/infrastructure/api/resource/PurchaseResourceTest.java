package com.sporty.bookstore.infrastructure.api.resource;

import com.google.gson.reflect.TypeToken;
import com.sporty.bookstore.domain.model.purchase.PaymentMethod;
import com.sporty.bookstore.infrastructure.api.resource.data.PurchaseData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static com.sporty.bookstore.domain.model.pricing.policy.DiscountPolicy.DiscountPolicyName.OLD_EDITION;
import static com.sporty.bookstore.domain.model.pricing.policy.DiscountPolicy.DiscountPolicyName.REGULAR_EDITION;
import static com.sporty.bookstore.infrastructure.api.resource.InventoryTestData.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PurchaseResourceTest extends ResourceTest {

    @Test
    public void testThatPurchaseWithBooksInStockIsProcessedUsingCreditCard() throws Exception {
        final String customerId = "1";
        final int sameQuantityForAllBooks = 8;
        final double samePriceForAllBooks = 87.5;
        final double expectedTotalPrice = 157.5;

        final List<String> bookIds =
                loadData(samePriceForAllBooks, REGULAR_EDITION,
                        List.of(effectiveJavaBook(), pragmaticProgrammerBook()));

        final PurchaseData purchaseData =
                PurchaseTestData.create(customerId, sameQuantityForAllBooks,
                        samePriceForAllBooks, PaymentMethod.CREDIT_CARD, bookIds);

        final ResultActions response =
                this.mockMvc.perform(post("/purchases").header("userId", customerId)
                        .contentType(APPLICATION_JSON_VALUE).content(asJson(purchaseData)))
                        .andExpect(status().isCreated());

        final PurchaseData responseData =
                toData(response, TypeToken.get(PurchaseData.class));

        final double totalPrice =
                responseData.items.stream().mapToDouble(item -> item.totalPrice).reduce(0, Double::sum);

        Assertions.assertNotNull(purchaseData.purchaseId);
        Assertions.assertEquals(2, responseData.items.size());
        Assertions.assertEquals(expectedTotalPrice, totalPrice);
    }

    @Test
    public void testThatPurchaseUnavailableBooksIsNotProcessed() throws Exception {
        final String customerId = "1";
        final int sameQuantityForAllBooks = 3;
        final double samePriceForAllBooks = 41.57;

        final List<String> bookIds =
                loadData(samePriceForAllBooks, OLD_EDITION, List.of(designPatternsBook()));

        final PurchaseData purchaseData =
                PurchaseTestData.create(customerId, sameQuantityForAllBooks,
                        samePriceForAllBooks, PaymentMethod.CREDIT_CARD, bookIds);

        this.mockMvc.perform(post("/purchases").header("userId", customerId).contentType(APPLICATION_JSON_VALUE)
                .content(asJson(purchaseData))).andExpect(status().isNotFound());
    }



}
