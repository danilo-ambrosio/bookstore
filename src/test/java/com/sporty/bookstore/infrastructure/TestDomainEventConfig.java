package com.sporty.bookstore.infrastructure;

import com.sporty.bookstore.domain.model.event.Notifier;
import com.sporty.bookstore.domain.model.event.SingleThreadedDomainEventNotifier;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

@TestConfiguration
public class TestDomainEventConfig {

    @Bean
    @Profile("test")
    public Notifier domainEventNotifier() {
        return new SingleThreadedDomainEventNotifier();
    }

}
