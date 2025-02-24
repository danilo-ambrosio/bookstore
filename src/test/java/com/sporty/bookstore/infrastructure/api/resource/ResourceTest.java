package com.sporty.bookstore.infrastructure.api.resource;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sporty.bookstore.domain.model.pricing.policy.DiscountPolicy;
import com.sporty.bookstore.infrastructure.TestDomainEventConfig;
import com.sporty.bookstore.infrastructure.api.resource.data.InventoryData;
import com.sporty.bookstore.infrastructure.api.resource.data.PricingData;
import com.sporty.bookstore.infrastructure.repository.MongoDBExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@DirtiesContext
@AutoConfigureMockMvc
@ActiveProfiles("test")
@ExtendWith(MongoDBExtension.class)
@Import({TestDomainEventConfig.class})
public abstract class ResourceTest {

  @Autowired
  protected Gson gson;

  @Autowired
  protected MockMvc mockMvc;

  protected <T> T toData(final ResultActions resultActions, final TypeToken<T> type) throws UnsupportedEncodingException {
    final String responseContent = resultActions.andReturn().getResponse().getContentAsString();
    return gson.fromJson(responseContent, type.getType());
  }

  protected String asJson(final Object object) {
    return gson.toJson(object);
  }

  protected List<String> loadData(final double bookPrice,
                                final DiscountPolicy.DiscountPolicyName policy,
                                final List<InventoryData> books) throws Exception {
    final List<String> bookIds = new ArrayList<>();
    for(final InventoryData inventoryData : books) {
      final String bookId = loadInventory(inventoryData);
      bookIds.add(bookId);
      loadPricing(bookId, PricingData.from(bookPrice, policy.name()));
    }
    return bookIds;
  }

  private String loadInventory(final InventoryData inventoryData) throws Exception {
    final ResultActions response =
            this.mockMvc.perform(post("/books").contentType(APPLICATION_JSON_VALUE)
                    .content(asJson(inventoryData))).andExpect(status().isCreated());
    return toData(response, TypeToken.get(InventoryData.class)).id;
  }

  private void loadPricing(final String bookId, final PricingData pricingData) throws Exception {
    this.mockMvc.perform(post("/books/" + bookId + "/price").contentType(APPLICATION_JSON_VALUE)
            .content(asJson(pricingData))).andExpect(status().isCreated());
  }
}

