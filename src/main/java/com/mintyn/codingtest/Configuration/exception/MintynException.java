package com.mintyn.codingtest.Configuration.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.io.Serial;

@Getter
public class MintynException extends RuntimeException {

  @Serial
  private static final long serialVersionUID = 144332211234L;

  private final HttpStatus status;

  public MintynException(HttpStatus status, String message) {
    super(message);
    this.status = status;
  }
}
