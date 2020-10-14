package com.vitorino.codewithreative.ui.rest.controller;

import com.vitorino.codewithreative.application.command.PersonCommandHandler;
import com.vitorino.codewithreative.application.command.person.FindAllCommand;
import com.vitorino.codewithreative.application.command.person.FindByIdCommand;
import com.vitorino.codewithreative.application.command.person.SaveCommand;
import com.vitorino.codewithreative.domain.model.Person;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/person")
@Validated
@RequiredArgsConstructor
public class PersonRestController {

    private final PersonCommandHandler commandHandler;

    @GetMapping
    public Flux<Person> findAll(FindAllCommand command) {
        return commandHandler.execute(command);
    }

    @GetMapping("{id}")
    public Mono<Person> findById(@PathVariable(value = "id") UUID id) {
        return commandHandler.execute(new FindByIdCommand(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Person> save(@Valid @RequestBody SaveCommand command) {
        return commandHandler.execute(command);
    }

}
