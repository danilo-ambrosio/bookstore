package com.sporty.bookstore.domain.model.bundle;

/**
 * This interface represents a bundle of books.
 * It provides methods to retrieve the book ID, quantity, and whether loyalty points should be used.
 *
 * @author Danilo Ambrosio
 */
public interface Bundle {

    String bookId();
    int quantity();
    boolean useLoyaltyPoints();

}
