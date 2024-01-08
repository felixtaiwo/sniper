package com.mintyn.codingtest.Configuration.exception;


import com.mintyn.codingtest.model.dto.ApiResponse;
import jakarta.validation.ConstraintViolationException;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.io.Serializable;
import java.util.List;

@Slf4j
@ControllerAdvice
public class MintynRestExceptionHandler {

  @ExceptionHandler({MintynException.class})
  public ResponseEntity<ApiResponse<String>> mintynEx(MintynException ex) {
    return ResponseEntity
      .status(ex.getStatus())
      .body(ApiResponse.<String>builder()
        .payload("Error: %s".formatted(ex.getMessage()))
        .build()
      );
  }

  @ExceptionHandler({WebClientResponseException.class})
  public ResponseEntity<ApiResponse<String>> raiseForJaXRsExceptions(WebClientResponseException wae) {
    return ResponseEntity.status(wae.getStatusCode())
      .contentType(MediaType.APPLICATION_JSON)
      .body(ApiResponse.<String>builder()
        .payload("Error %s".formatted(wae.getMessage()))
        .build()
      );
  }

  @ExceptionHandler({ConstraintViolationException.class})
  public ResponseEntity<ApiResponse<List<Violation>>> raiseForConstraintValidationErrors(ConstraintViolationException be) {
    var violations = be.getConstraintViolations()
      .stream()
      .map(
        cv -> Violation.builder()
          .prop(cv.getPropertyPath().toString())
          .error(cv.getMessage()
          )
          .build()
      ).toList();
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
      .body(
        ApiResponse.<List<Violation>>builder()
          .payload(violations)
          .build()
      );
  }
  @ExceptionHandler({MethodArgumentNotValidException.class})
  public ResponseEntity<ApiResponse<List<Violation>>> raiseForMethodNotValid(MethodArgumentNotValidException be) {
    var violations = be.getAllErrors()
            .stream()
            .map(
                    cv -> Violation.builder()
                            .prop(cv.getObjectName())
                            .error(cv.getDefaultMessage()
                            )
                            .build()
            ).toList();
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(
                    ApiResponse.<List<Violation>>builder()
                            .payload(violations)
                            .build()
            );
  }

  @Getter
  @Jacksonized @Builder
  public static class Violation implements Serializable {
    String prop;
    String error;
  }
}
