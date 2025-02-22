package com.sporty.bookstore.infrastructure.repository;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.testcontainers.containers.MongoDBContainer;

import java.io.IOException;
import java.util.Properties;

public class MongoDBExtension implements BeforeAllCallback, AfterEachCallback {

    private static final String IMAGE_PROPERTY = "spring.data.mongodb.image";
    private static final String DATABASE_NAME_PROPERTY = "spring.data.mongodb.database";
    private static final String URI_PROPERTY = "spring.data.mongodb.uri";

    private final MongoDatabase testDatabase;
    private final MongoDBContainer container;

    public MongoDBExtension() throws IOException {
        final Properties properties = loadProperties();
        final String dockerImage = properties.getProperty(IMAGE_PROPERTY);
        final String databaseName = properties.getProperty(DATABASE_NAME_PROPERTY);

        this.container = new MongoDBContainer(dockerImage);
        this.container.start();

        //Skip auto-closeable handling, client lifecycle should be managed by TestContainers
        final MongoClient client =
                MongoClients.create(container.getConnectionString());

        this.testDatabase = client.getDatabase(databaseName);
    }

    @Override
    public void beforeAll(ExtensionContext context) {
        System.setProperty(URI_PROPERTY, container.getReplicaSetUrl(testDatabase.getName()));
    }

    @Override
    public void afterEach(ExtensionContext context) {
        cleanDatabase();
    }

    private Properties loadProperties() throws IOException {
        return PropertiesLoaderUtils.loadProperties(new ClassPathResource("/application.properties"));
    }

    private void cleanDatabase() {
        testDatabase.listCollectionNames().map(testDatabase::getCollection).forEach(MongoCollection::drop);
    }

}
