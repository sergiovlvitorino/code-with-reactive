package com.vitorino.codewithreative.application.command.person;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FindByIdCommand {

    @NotNull
    private UUID id;

}
