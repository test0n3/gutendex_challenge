package com.aluracursos.gutendex.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record BookData(
                String title,
                List<PersonData> authors,
                List<String> languages,
                @JsonAlias("download_count") Double downloadCount) {
}
