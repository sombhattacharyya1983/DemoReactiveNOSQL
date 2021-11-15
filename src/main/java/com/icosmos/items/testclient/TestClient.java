package com.icosmos.items.testclient;

import com.icosmos.items.model.User;
import org.springframework.web.reactive.function.client.WebClient;

public class TestClient {
  private WebClient client = WebClient.create("http://localhost:8080");

  public void getAllBooksDemo() {
    client
        .get()
        .uri("/items")
        .exchange()
        .flatMapMany(res -> res.bodyToFlux(User.class))
        .collectList()
        .subscribe(users -> users.forEach(b -> System.out.println(b)));
  }
}
