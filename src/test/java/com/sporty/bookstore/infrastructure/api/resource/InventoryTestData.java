package com.sporty.bookstore.infrastructure.api.resource;

import com.sporty.bookstore.infrastructure.api.resource.data.InventoryData;

import java.util.List;

public class InventoryTestData {

    public static InventoryData effectiveJavaBook() {
        return new InventoryData(
                "1",
                "9780134685991",
                "Effective Java",
                15,
                List.of("Joshua Bloch"),
                List.of("Programming", "Java")
        );
    }

    public static InventoryData designPatternsBook() {
        return new InventoryData(
                "2",
                "9780201633610",
                "Design Patterns: Elements of Reusable Object-Oriented Software",
                0,
                List.of("Erich Gamma", "Richard Helm", "Ralph Johnson"),
                List.of("Software Design", "Programming")
        );
    }

    public static InventoryData pragmaticProgrammerBook() {
        return new InventoryData(
                "3",
                "1432187986415",
                "The Pragmatic Programmer",
                190,
                List.of("Andrew Hunt"),
                List.of("Software Design", "Computer Science")
        );
    }


}
