package com.icosmos.items.config;

import com.icosmos.items.handlers.UserHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
@EnableWebFlux
public class Routers {
  @Bean
  RouterFunction<ServerResponse> getItemsByNameRoute(UserHandler itemHandler) {
    return RouterFunctions.route().GET("/items", itemHandler::getAllItems).build();
  }
}
