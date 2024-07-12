package com.mohammad_bakur.journey;

import com.github.javafaker.Faker;
import com.github.javafaker.Name;
import com.mohammad_bakur.user.models.Usert;
import com.mohammad_bakur.user.requests.UserRegistrationRequest;
import com.mohammad_bakur.user.requests.UserUpdateRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Random;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UsertIntegrationTest {

    @Autowired
    private WebTestClient webTestClient;

    private static final Random RANDOM = new Random();
    private static final String CLIENT_URI = "/api/v1/clients";

    @Test
    void canRegisterClient() {
        // create registration request
        Faker faker = new Faker();
        Name fakerName = faker.name();

        String name = fakerName.fullName();
        String email = fakerName.lastName() + "-" + UUID.randomUUID() + "@amigoscode.com";
        int age = RANDOM.nextInt(1, 100);

        UserRegistrationRequest request = new UserRegistrationRequest(
                name, email, age
        );
        // send a post request
        webTestClient.post()
                .uri(CLIENT_URI)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(request), UserRegistrationRequest.class)
                .exchange()
                .expectStatus()
                .isOk();

        // get all customers
        List<Usert> allCustomers = webTestClient.get()
                .uri(CLIENT_URI)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(new ParameterizedTypeReference<Usert>() {
                })
                .returnResult()
                .getResponseBody();

        // make sure that customer is present
        Usert expectedCustomer = new Usert(
                name, email, age
        );

        assertThat(allCustomers)
                .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id")
                .contains(expectedCustomer);

        int id = allCustomers.stream()
                .filter(customer -> customer.getEmail().equals(email))
                .map(Usert::getId)
                .findFirst()
                .orElseThrow();

        expectedCustomer.setId(id);

        // get customer by id
        webTestClient.get()
                .uri(CLIENT_URI + "/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(new ParameterizedTypeReference<Usert>() {
                })
                .isEqualTo(expectedCustomer);
    }

    @Test
    void canDeleteClient() {
        // create registration request
        Faker faker = new Faker();
        Name fakerName = faker.name();

        String name = fakerName.fullName();
        String email = fakerName.lastName() + "-" + UUID.randomUUID() + "@amigoscode.com";
        int age = RANDOM.nextInt(1, 100);

        UserRegistrationRequest request = new UserRegistrationRequest(
                name, email, age
        );

        // send a post request
        webTestClient.post()
                .uri(CLIENT_URI)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(request), UserRegistrationRequest.class)
                .exchange()
                .expectStatus()
                .isOk();

        // get all customers
        List<Usert> allCustomers = webTestClient.get()
                .uri(CLIENT_URI)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(new ParameterizedTypeReference<Usert>() {
                })
                .returnResult()
                .getResponseBody();


        int id = allCustomers.stream()
                .filter(customer -> customer.getEmail().equals(email))
                .map(Usert::getId)
                .findFirst()
                .orElseThrow();

        // delete customer

        webTestClient.delete()
                .uri(CLIENT_URI + "/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk();

        // get customer by id
        webTestClient.get()
                .uri(CLIENT_URI + "/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isNotFound();
    }

    @Test
    void canUpdateClient() {
        // create registration request
        Faker faker = new Faker();
        Name fakerName = faker.name();

        String name = fakerName.fullName();
        String email = fakerName.lastName() + "-" + UUID.randomUUID() + "@amigoscode.com";
        int age = RANDOM.nextInt(1, 100);

        UserRegistrationRequest request = new UserRegistrationRequest(
                name, email, age
        );

        // send a post request
        webTestClient.post()
                .uri(CLIENT_URI)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(request), UserRegistrationRequest.class)
                .exchange()
                .expectStatus()
                .isOk();

        // get all customers
        List<Usert> allCustomers = webTestClient.get()
                .uri(CLIENT_URI)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(new ParameterizedTypeReference<Usert>() {
                })
                .returnResult()
                .getResponseBody();


        int id = allCustomers.stream()
                .filter(customer -> customer.getEmail().equals(email))
                .map(Usert::getId)
                .findFirst()
                .orElseThrow();

        // update customer

        String newName = "Ali";

        UserUpdateRequest updateRequest = new UserUpdateRequest(
                newName, null, null
        );

        webTestClient.put()
                .uri(CLIENT_URI + "/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(updateRequest), UserUpdateRequest.class)
                .exchange()
                .expectStatus()
                .isOk();

        // get customer by id
        Usert updatedCustomer = webTestClient.get()
                .uri(CLIENT_URI + "/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(Usert.class)
                .returnResult()
                .getResponseBody();

        Usert expected = new Usert(
                id, newName, email, age
        );

        assertThat(updatedCustomer).isEqualTo(expected);
    }
}
