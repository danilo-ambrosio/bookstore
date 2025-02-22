package com.sporty.bookstore.infrastructure;

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

  @ExceptionHandler(value = BookNotFoundException.class )
  public ResponseEntity<Object> resolveBookNotFoundException() {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
  }

}
