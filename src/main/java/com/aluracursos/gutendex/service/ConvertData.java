package com.aluracursos.gutendex.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ConvertData implements DataConverter {

  private ObjectMapper mapper = new ObjectMapper();

  @Override
  public <T> T obtainData(String json, Class<T> clazz) {
    try {
      return mapper.readValue(json.toString(), clazz);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }
}
