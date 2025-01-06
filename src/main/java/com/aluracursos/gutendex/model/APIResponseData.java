package com.aluracursos.gutendex.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record APIResponseData(
        Integer count,
        List<BookData> results) {
}
