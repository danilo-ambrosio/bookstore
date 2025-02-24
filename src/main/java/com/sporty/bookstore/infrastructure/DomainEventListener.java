package com.sporty.bookstore.infrastructure;

import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to mark a class as a Domain Event Listener.
 * Domain Event Listeners are responsible for handling domain events and reacting to
 * them by performing necessary actions or side effects.
 *
 * @author Danilo Ambrosio
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Component
public @interface DomainEventListener {

}
