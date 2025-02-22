package com.sporty.bookstore.infrastructure.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<UserData, String> {

    Optional<UserData> findByUsernameAndPassword(final String username, final String password);
    boolean existsByUsername(final String username);
}

