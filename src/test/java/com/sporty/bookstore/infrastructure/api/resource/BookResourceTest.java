package com.sporty.bookstore.infrastructure.api.resource;

import com.google.gson.reflect.TypeToken;
import com.sporty.bookstore.infrastructure.api.resource.data.BookInventoryData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class BookResourceTest extends ResourceTest {

    @Test
    public void testThatBookIsInventoried() throws Exception {
        final BookInventoryData effectiveJavaBook = BookInventoryTestData.effectiveJavaBook();

        final ResultActions response =
                this.mockMvc.perform(post("/books").contentType(APPLICATION_JSON_VALUE)
                        .content(asJson(effectiveJavaBook))).andExpect(status().isCreated());

        final BookInventoryData inventoriedBookData =
                toData(response, TypeToken.get(BookInventoryData.class));

        Assertions.assertNotNull(inventoriedBookData.id);
        Assertions.assertEquals(effectiveJavaBook.isbn, inventoriedBookData.isbn);
        Assertions.assertEquals(effectiveJavaBook.title, inventoriedBookData.title);
        Assertions.assertEquals(effectiveJavaBook.stockQuantity, inventoriedBookData.stockQuantity);
        Assertions.assertEquals(effectiveJavaBook.authors, inventoriedBookData.authors);
        Assertions.assertEquals(effectiveJavaBook.genres, inventoriedBookData.genres);
    }

    @Test
    public void testThatBookIsReinventoried() throws Exception {
        final BookInventoryData designPatternsBook = BookInventoryTestData.designPatternsBook();

        final ResultActions response =
                this.mockMvc.perform(post("/books").contentType(APPLICATION_JSON_VALUE)
                        .content(asJson(designPatternsBook))).andExpect(status().isCreated());

        final BookInventoryData inventoriedBookData =
                toData(response, TypeToken.get(BookInventoryData.class));

        designPatternsBook.authors.add("John Vlissides");

        final ResultActions reinventoryResponse =
                this.mockMvc.perform(put("/books/" + inventoriedBookData.id).contentType(APPLICATION_JSON_VALUE)
                        .content(asJson(designPatternsBook))).andExpect(status().isOk());

        final BookInventoryData reinventoriedBookData =
                toData(reinventoryResponse, TypeToken.get(BookInventoryData.class));

        Assertions.assertEquals(designPatternsBook.isbn, reinventoriedBookData.isbn);
        Assertions.assertEquals(designPatternsBook.title, reinventoriedBookData.title);
        Assertions.assertEquals(designPatternsBook.stockQuantity, reinventoriedBookData.stockQuantity);
        Assertions.assertEquals(designPatternsBook.authors, reinventoriedBookData.authors);
        Assertions.assertEquals(designPatternsBook.genres, reinventoriedBookData.genres);
    }

    @Test
    public void testThatInventoriedBooksAreRetrieved() throws Exception {
        final BookInventoryData effectiveJavaBook = BookInventoryTestData.effectiveJavaBook();
        final BookInventoryData designPatternsBook = BookInventoryTestData.designPatternsBook();

        this.mockMvc.perform(post("/books").contentType(APPLICATION_JSON_VALUE)
                .content(asJson(effectiveJavaBook))).andExpect(status().isCreated());

        this.mockMvc.perform(post("/books").contentType(APPLICATION_JSON_VALUE)
                .content(asJson(designPatternsBook))).andExpect(status().isCreated());

        final ResultActions response =
                this.mockMvc.perform(get("/books")).andExpect(status().isOk());

        final List<BookInventoryData> booksData = toData(response, new TypeToken<>(){});

        Assertions.assertEquals(2, booksData.size());
    }

    @Test
    public void testThatInventoriedBookIsRemoved() throws Exception {
        final BookInventoryData effectiveJavaBook = BookInventoryTestData.effectiveJavaBook();

        final ResultActions response =
                this.mockMvc.perform(post("/books").contentType(APPLICATION_JSON_VALUE)
                        .content(asJson(effectiveJavaBook))).andExpect(status().isCreated());

        final BookInventoryData inventoriedBookData =
                toData(response, TypeToken.get(BookInventoryData.class));

        this.mockMvc.perform(delete("/books/" + inventoriedBookData.id)).andExpect(status().isOk());

        final ResultActions emptyResponse =
                this.mockMvc.perform(get("/books")).andExpect(status().isOk());

        final List<BookInventoryData> booksData = toData(emptyResponse, new TypeToken<>(){});

        Assertions.assertEquals(0, booksData.size());
    }
}
