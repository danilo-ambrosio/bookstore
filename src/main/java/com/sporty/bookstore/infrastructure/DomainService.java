package com.sporty.bookstore.infrastructure;

import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to mark a class as a Domain Service.
 * Domain Services are responsible for encapsulating business logic and operations
 * related to a specific domain or subdomain of an application, being allowed to
 * depend on infra resources as Repositories and manipulate multiple aggregates in
 * a single transaction.
 *
 * @author Danilo Ambrosio
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Component
public @interface DomainService {

}
