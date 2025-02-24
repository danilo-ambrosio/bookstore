package com.sporty.bookstore.infrastructure.api.resource;

import com.google.gson.reflect.TypeToken;
import com.sporty.bookstore.infrastructure.api.resource.data.LoyaltyPointsData;
import com.sporty.bookstore.infrastructure.api.resource.data.PurchaseData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static com.sporty.bookstore.domain.model.pricing.policy.DiscountPolicy.DiscountPolicyName.REGULAR_EDITION;
import static com.sporty.bookstore.domain.model.purchase.PaymentMethod.CREDIT_CARD;
import static com.sporty.bookstore.domain.model.purchase.PaymentMethod.LOYALTY_POINTS;
import static com.sporty.bookstore.infrastructure.api.resource.InventoryTestData.effectiveJavaBook;
import static com.sporty.bookstore.infrastructure.api.resource.InventoryTestData.pragmaticProgrammerBook;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class LoyaltyResourceTest extends ResourceTest {

    @Test
    public void testThatLoyaltyPointsAreAccumulatedAndConsumed() throws Exception {
        final String customerId = "1";
        final double samePriceForAllBooks = 87.5;

        final List<String> bookIds =
                loadData(samePriceForAllBooks, REGULAR_EDITION,
                        List.of(effectiveJavaBook(), pragmaticProgrammerBook()));

        final PurchaseData purchaseData =
                PurchaseTestData.create(customerId, 15, samePriceForAllBooks, CREDIT_CARD, bookIds);

        this.mockMvc.perform(post("/purchases").header("userId", customerId)
                        .contentType(APPLICATION_JSON_VALUE).content(asJson(purchaseData)))
                .andExpect(status().isCreated());

        final ResultActions firstResponse =
            this.mockMvc.perform(get("/loyalties/points").header("userId", customerId))
                    .andExpect(status().isOk());

        final LoyaltyPointsData accumulatedPoints =
                toData(firstResponse, TypeToken.get(LoyaltyPointsData.class));

        Assertions.assertEquals(30, accumulatedPoints.total);

        final PurchaseData purchaseWithLoyaltyData =
                PurchaseTestData.create(customerId, 1, samePriceForAllBooks, LOYALTY_POINTS, bookIds);

        this.mockMvc.perform(post("/purchases").header("userId", customerId)
                        .contentType(APPLICATION_JSON_VALUE).content(asJson(purchaseWithLoyaltyData)))
                .andExpect(status().isCreated());

        final ResultActions secondResponse =
                this.mockMvc.perform(get("/loyalties/points").header("userId", customerId))
                        .andExpect(status().isOk());

        final LoyaltyPointsData pointsAfterUse =
                toData(secondResponse, TypeToken.get(LoyaltyPointsData.class));

        Assertions.assertEquals(10, pointsAfterUse.total);
    }

    @Test
    public void testThatLoyaltyPointsAreInsufficient() throws Exception {
        final String customerId = "1";
        final double samePriceForAllBooks = 87.5;

        final List<String> bookIds =
                loadData(samePriceForAllBooks, REGULAR_EDITION,
                        List.of(effectiveJavaBook(), pragmaticProgrammerBook()));

        final PurchaseData purchaseData =
                PurchaseTestData.create(customerId, 15, samePriceForAllBooks, CREDIT_CARD, bookIds);

        this.mockMvc.perform(post("/purchases").header("userId", customerId)
                        .contentType(APPLICATION_JSON_VALUE).content(asJson(purchaseData)))
                .andExpect(status().isCreated());

        final PurchaseData purchaseWithLoyaltyData =
                PurchaseTestData.create(customerId, 3, samePriceForAllBooks, LOYALTY_POINTS, bookIds);

        this.mockMvc.perform(post("/purchases").header("userId", customerId)
                        .contentType(APPLICATION_JSON_VALUE).content(asJson(purchaseWithLoyaltyData)))
                .andExpect(status().isBadRequest());
    }
}
