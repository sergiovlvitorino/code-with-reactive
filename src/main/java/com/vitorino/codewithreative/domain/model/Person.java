package com.vitorino.codewithreative.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Document
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Person {

    @Id
    private UUID id;
    private String name;

}
