package com.icosmos.items;

import com.icosmos.items.model.User;
import com.icosmos.items.repositories.UserRepository;
import com.icosmos.items.testclient.TestClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.util.Assert;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

@SpringBootApplication
public class ItemsApplication implements CommandLineRunner {
    private static final Logger LOGGER = LoggerFactory.getLogger(ItemsApplication.class);
    @Autowired
    private UserRepository repository;
    public static void main(String[] args) {
        SpringApplication.run(ItemsApplication.class, args);
    }

    public void run(String... var1) {
        this.repository.deleteAll().block();
        LOGGER.info("Deleted all data in container.");

        final User testUser = new User("testId", "testFirstName", "testLastName", "test address line one");
        final User testUserONe = new User("testId2", "testFirstName2", "testLastName2", "test address line one2");
        final User testUserTwo = new User("testId3", "testFirstName3", "testLastName3", "test address line one3");

        // Save the User class to Azure Cosmos DB database.
        final Mono<User> saveUserMono = repository.save(testUser);
        final Mono<User> saveUserMono1 = repository.save(testUserONe);
        final Mono<User> saveUserMono2 = repository.save(testUserTwo);

        final Flux<User> firstNameUserFlux = repository.findByFirstName("testFirstName");

        //  Nothing happens until we subscribe to these Monos.
        //  findById will not return the user as user is not present.
        final Mono<User> findByIdMono = repository.findById(testUser.getId());
        final User findByIdUser = findByIdMono.block();
        Assert.isNull(findByIdUser, "User must be null");

        final User savedUser = saveUserMono.block();
        Assert.state(savedUser != null, "Saved user must not be null");
        Assert.state(savedUser.getFirstName().equals(testUser.getFirstName()), "Saved user first name doesn't match");

        final User savedUser1 = saveUserMono1.block();
        Assert.state(savedUser1 != null, "Saved user must not be null");
        Assert.state(savedUser.getFirstName().equals(testUser.getFirstName()), "Saved user first name doesn't match");

        final User savedUser2 = saveUserMono2.block();
        Assert.state(savedUser2 != null, "Saved user must not be null");
        Assert.state(savedUser.getFirstName().equals(testUser.getFirstName()), "Saved user first name doesn't match");

        firstNameUserFlux.collectList().block();

        final Optional<User> optionalUserResult = repository.findById(testUser.getId()).blockOptional();
        Assert.isTrue(optionalUserResult.isPresent(), "Cannot find user.");

        final User result = optionalUserResult.get();
        Assert.state(result.getFirstName().equals(testUser.getFirstName()), "query result firstName doesn't match!");
        Assert.state(result.getLastName().equals(testUser.getLastName()), "query result lastName doesn't match!");

        LOGGER.info("findOne in User collection get result: {}", result.toString());

        //Test using web client
        TestClient testClient = new TestClient();
        testClient.getAllBooksDemo();

    }
}
