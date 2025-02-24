package com.sporty.bookstore.infrastructure;

import com.sporty.bookstore.domain.model.inventory.UnavailableBookException;
import com.sporty.bookstore.domain.model.loyalty.InsufficientLoyaltyPointsException;
import com.sporty.bookstore.usecase.identity.AuthenticationException;
import com.sporty.bookstore.usecase.identity.UniqueUsernameException;
import com.sporty.bookstore.usecase.inventory.BookNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * {@code ExceptionTranslator} translate exceptions to HTTP responses.
 *
 * @author Danilo Ambrosio
 */
@ControllerAdvice
public class ExceptionTranslator {

  @ExceptionHandler(value = AuthenticationException.class)
  public ResponseEntity<Object> resolveAuthenticationException() {
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
  }

  @ExceptionHandler(value = BookNotFoundException.class )
  public ResponseEntity<Object> resolveBookNotFoundException() {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
  }

  @ExceptionHandler(value = UnavailableBookException.class )
  public ResponseEntity<Object> resolveUnavailableBookException() {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
  }

  @ExceptionHandler(value = UniqueUsernameException.class )
  public ResponseEntity<Object> resolveUniqueUsernameException() {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
  }

  @ExceptionHandler(value = InsufficientLoyaltyPointsException.class )
  public ResponseEntity<Object> resolveInsufficientLoyaltyPointsException() {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
  }

}
