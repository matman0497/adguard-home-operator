package com.matman0497.responses;

import java.net.http.HttpResponse;

public class Handler {

  public Response handle(HttpResponse<String> response) {
    if (response.statusCode() >= 200 && response.statusCode() < 300) {
      return new EmptyResponse();
    }

    return new ErrorResponse(Integer.toString(response.statusCode()));
  }
}
