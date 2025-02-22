package com.sporty.bookstore.infrastructure.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface BookInventoryRepository extends MongoRepository<BookData, String> {

    List<BookData> findByStockQuantityGreaterThanEqual(int quantity);

}
