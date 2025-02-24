package com.sporty.bookstore.infrastructure.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface LoyaltyRepository extends MongoRepository<LoyaltyData, String> {

}
