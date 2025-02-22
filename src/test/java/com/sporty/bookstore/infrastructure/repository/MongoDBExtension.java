package com.sporty.bookstore.infrastructure.repository;

import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.testcontainers.containers.MongoDBContainer;

public class MongoDBExtension implements BeforeAllCallback {

    @Override
    public void beforeAll(ExtensionContext context) {
        final MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:latest");
        mongoDBContainer.start();
        System.setProperty("spring.data.mongodb.uri", mongoDBContainer.getReplicaSetUrl());
    }
}
