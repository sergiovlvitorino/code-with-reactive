package com.vitorino.codewithreative.domain.repository;

import com.vitorino.codewithreative.domain.model.Person;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PersonRepository extends ReactiveMongoRepository<Person, UUID> {
}
