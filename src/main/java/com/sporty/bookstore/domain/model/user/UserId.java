package com.sporty.bookstore.domain.model.user;

import org.springframework.data.annotation.PersistenceConstructor;

import java.util.Objects;
import java.util.UUID;

/**
 * {@code UserId} contains a value that identifies a {@link User}.
 *
 * @author Danilo Ambrosio
 */
public class UserId {

  private final String value;

  public static UserId create() {
    return new UserId(UUID.randomUUID().toString());
  }

  public static UserId of(final String value) {
    return new UserId(value);
  }

  private UserId(final String value) {
    this.value = value;
  }

  public String value() {
    return value;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    final UserId userId = (UserId) o;
    return Objects.equals(value, userId.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(value);
  }

}
