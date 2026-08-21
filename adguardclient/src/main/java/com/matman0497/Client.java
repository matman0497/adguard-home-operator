package com.matman0497;

import com.matman0497.auth.BasicAuth;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Client {

  private final Config config = ConfigManager.getDefault();

  private final HttpClient httpClient;

  private final BasicAuth basicAuth;

  public Client(HttpClient httpClient, BasicAuth basicAuth) {
    this.httpClient = httpClient;
    this.basicAuth = basicAuth;
  }

  public HttpResponse<String> post(String path, String body)
      throws IOException, InterruptedException {

    var response =
        this.httpClient.send(
            HttpRequest.newBuilder()
                .header("Authorization", basicAuth.getHeaderValue())
                .header("Content-Type", "application/json")
                .uri(URI.create(getUrl() + path))
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build(),
            HttpResponse.BodyHandlers.ofString());

    return response;
  }

  private String getUrl() {
    return config.get("client.base_url");
  }

  public HttpResponse<String> put(String path, String body)
      throws IOException, InterruptedException {

    var response =
        this.httpClient.send(
            HttpRequest.newBuilder()
                .header("Authorization", basicAuth.getHeaderValue())
                .header("Content-Type", "application/json")
                .uri(URI.create(getUrl() + path))
                .PUT(HttpRequest.BodyPublishers.ofString(body))
                .build(),
            HttpResponse.BodyHandlers.ofString());

    return response;
  }

  public HttpResponse<String> get(String path) throws IOException, InterruptedException {
    var response =
        this.httpClient.send(
            createBuilder().uri(URI.create(getUrl() + path)).GET().build(),
            HttpResponse.BodyHandlers.ofString());

    return response;
  }

  private HttpRequest.Builder createBuilder() {
    return HttpRequest.newBuilder()
        .header("Authorization", basicAuth.getHeaderValue())
        .header("Content-Type", "application/json");
  }
}
