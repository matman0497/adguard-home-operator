package com.matman0497.responses;

public class ErrorResponse extends Response {
  private final String errorCode;

  public ErrorResponse(String errorCode) {
    this.errorCode = errorCode;
  }

  public String getErrorCode() {
    return errorCode;
  }
}
