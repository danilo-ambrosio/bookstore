package com.sporty.bookstore.infrastructure.api.resource;

import com.sporty.bookstore.infrastructure.api.resource.data.BookInventoryData;

import java.util.List;

public class BookInventoryTestData {

    public static BookInventoryData effectiveJavaBook() {
        return new BookInventoryData(
                "1234567890",
                "9780134685991",
                "Effective Java",
                15,
                List.of("Joshua Bloch"),
                List.of("Programming", "Java")
        );
    }

    public static BookInventoryData designPatternsBook() {
        return new BookInventoryData(
                "2345678901",
                "9780201633610",
                "Design Patterns: Elements of Reusable Object-Oriented Software",
                0,
                List.of("Erich Gamma", "Richard Helm", "Ralph Johnson"),
                List.of("Software Design", "Programming")
        );
    }

}
