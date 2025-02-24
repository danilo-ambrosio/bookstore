package com.sporty.bookstore.infrastructure;

import com.sporty.bookstore.domain.model.event.DomainEventNotifier;
import com.sporty.bookstore.domain.model.event.Notifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class DomainEventConfig {

    @Bean
    @Profile("local")
    public Notifier domainEventNotifier() {
        return new DomainEventNotifier(new ThreadPool());
    }

}
