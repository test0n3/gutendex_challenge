package com.aluracursos.gutendex.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record PersonData(
        String name,
        @JsonAlias("birth_year") Integer birthYear) {
}
