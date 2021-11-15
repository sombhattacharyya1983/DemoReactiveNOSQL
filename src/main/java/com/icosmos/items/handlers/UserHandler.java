package com.icosmos.items.handlers;

import com.icosmos.items.model.User;
import com.icosmos.items.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class UserHandler {
  private final UserRepository userRepository;

  public Mono<ServerResponse> getAllItems(ServerRequest serverRequest) {
    // do something with the serverRequest here
    return ServerResponse.ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(userRepository.findAll(), User.class);
  }
}
