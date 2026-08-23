package com.matman0497;

import com.matman0497.actions.Rewrite;
import com.matman0497.auth.BasicAuth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Client {

  private final Config config = ConfigManager.getDefault();

  private static final Logger log = LoggerFactory.getLogger(Client.class);

  private final HttpClient httpClient;

  private final BasicAuth basicAuth;

  public Client(HttpClient httpClient, BasicAuth basicAuth) {
    this.httpClient = httpClient;
    this.basicAuth = basicAuth;

    config.overrideFromEnv("client.base_url", "CLIENT_BASE_URL");
  }

  public HttpResponse<String> post(String path, String body)
      throws IOException, InterruptedException {

      return this.httpClient.send(
          HttpRequest.newBuilder()
              .header("Authorization", basicAuth.getHeaderValue())
              .header("Content-Type", "application/json")
              .uri(URI.create(getUrl() + path))
              .POST(HttpRequest.BodyPublishers.ofString(body))
              .build(),
          HttpResponse.BodyHandlers.ofString());
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

    var endpoint = URI.create(getUrl() + path);

    log.debug("Sending request to {}", endpoint);

    var response =
        this.httpClient.send(
            createBuilder().uri(endpoint).GET().build(),
            HttpResponse.BodyHandlers.ofString());

    if (response.statusCode() == 401) {
      throw new RuntimeException("unauthorized");
    }

    if (response.statusCode() == 403) {
      throw new RuntimeException("unauthenticated");
    }

    return response;
  }

  private HttpRequest.Builder createBuilder() {
    return HttpRequest.newBuilder()
        .header("Authorization", basicAuth.getHeaderValue())
        .header("Content-Type", "application/json");
  }
}
