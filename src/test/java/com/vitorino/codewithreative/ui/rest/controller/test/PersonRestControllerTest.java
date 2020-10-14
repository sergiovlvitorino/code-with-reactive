package com.vitorino.codewithreative.ui.rest.controller.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vitorino.codewithreative.application.command.person.SaveCommand;
import com.vitorino.codewithreative.domain.model.Person;
import com.vitorino.codewithreative.domain.repository.PersonRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import java.util.Arrays;
import java.util.UUID;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PersonRestControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private PersonRepository repository;

    @Autowired
    private ObjectMapper mapper;

    @Test
    public void testIfFindAllIsOk() {

        Arrays.asList(
                Person.builder().id(UUID.randomUUID()).name("John Doe1").build(),
                Person.builder().id(UUID.randomUUID()).name("John Doe2").build()
        ).stream().forEach(person -> repository.save(person).block());

        webTestClient
                .get()
                .uri("/person")
                .accept(MediaType.TEXT_EVENT_STREAM)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(Person.class)
                .hasSize(2).returnResult().getResponseBody().stream().forEach(person -> repository.delete(person));
    }

    @Test
    public void testIfFindByIdIsOk() {

        var person = Person.builder().id(UUID.randomUUID()).name("John Doe").build();
        repository.save(person).block();

        webTestClient
                .get()
                .uri("/person/" + person.getId().toString())
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(Person.class)
                .isEqualTo(person);

        repository.delete(person).block();

    }

    @Test
    public void testIfFindByIdReturnsBadRequestWhenIdWrong() {

        var person = Person.builder().id(UUID.randomUUID()).name("John Doe").build();
        repository.save(person).block();

        webTestClient
                .get()
                .uri("/person/" + person.getId().toString() + "dfsadfasd")
                .exchange()
                .expectStatus()
                .isBadRequest();
        repository.delete(person).block();

    }

    @Test
    public void testIfSaveIsOk() throws JsonProcessingException {
        var body = BodyInserters.fromValue(mapper.writeValueAsString(SaveCommand.builder().name("John Doe").build()));
        var person = webTestClient
                .post()
                .uri("/person/")
                .contentType(MediaType.APPLICATION_JSON)
                .body(body)
                .exchange()
                .expectStatus()
                .isCreated()
                .returnResult(Person.class)
                .getResponseBody()
                .blockFirst();

        repository.delete(person).block();
    }

    @Test
    public void testIfSaveReturnsBadRequestWhenNameIsEmpty() throws JsonProcessingException {
        var body = BodyInserters.fromValue(mapper.writeValueAsString(SaveCommand.builder().build()));
        webTestClient
                .post()
                .uri("/person/")
                .contentType(MediaType.APPLICATION_JSON)
                .body(body)
                .exchange()
                .expectStatus()
                .isBadRequest();
    }

}
