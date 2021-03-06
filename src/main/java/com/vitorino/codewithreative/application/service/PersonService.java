package com.vitorino.codewithreative.application.service;

import com.vitorino.codewithreative.domain.model.Person;
import com.vitorino.codewithreative.domain.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepository repository;

    public Mono<Person> save(Person person) {
        person.setId(UUID.randomUUID());
        return repository.save(person);
    }
    
    public Flux<Person> findAll(){
        return repository.findAll();
    }

    public Mono<Person> findById(UUID id) {
        return repository.findById(id);
    }
}
