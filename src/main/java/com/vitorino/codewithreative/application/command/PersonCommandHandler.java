package com.vitorino.codewithreative.application.command;

import com.vitorino.codewithreative.application.command.person.FindAllCommand;
import com.vitorino.codewithreative.application.command.person.FindByIdCommand;
import com.vitorino.codewithreative.application.command.person.SaveCommand;
import com.vitorino.codewithreative.application.service.PersonService;
import com.vitorino.codewithreative.domain.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class PersonCommandHandler {

    @Autowired
    private PersonService service;

    public Mono<Person> execute(SaveCommand command) {
        var person = Person.builder().name(command.getName()).build();
        return service.save(person);
    }

    public Flux<Person> execute(FindAllCommand command) {
        return service.findAll();
    }

    public Mono<Person> execute(FindByIdCommand command) {
        return service.findById(command.getId());
    }
}
