package com.icosmos.items.repositories;

import com.azure.spring.data.cosmos.repository.ReactiveCosmosRepository;
import com.icosmos.items.model.User;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface UserRepository extends ReactiveCosmosRepository<User, String> {
    Flux<User> findByFirstName(String firstName);
    Flux<User> findAll();
}
