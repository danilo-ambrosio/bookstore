package com.sporty.bookstore.infrastructure.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface BookInventoryRepository extends MongoRepository<BookInventoryData, String> {

    //TODO: Create BookStatus (IN_STOCK, OUT_OF_STOCK) and replace this query with findByStatus
    List<BookInventoryData> findByStockQuantityGreaterThanEqual(int quantity);

}
