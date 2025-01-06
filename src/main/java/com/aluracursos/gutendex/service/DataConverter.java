package com.aluracursos.gutendex.service;

public interface DataConverter {
  <T> T obtainData(String json, Class<T> clazz);
}
