package com.matman0497.auth;

import java.util.Base64;

public class BasicAuth {

  private final String username;
  private final String password;

  public BasicAuth(String username, String password) {
    this.username = username;
    this.password = password;
  }

  public String getHeaderValue() {
    return "Basic " + Base64.getEncoder().encodeToString((username + ":" + password).getBytes());
  }
}
