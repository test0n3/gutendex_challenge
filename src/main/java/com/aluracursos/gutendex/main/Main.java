package com.aluracursos.gutendex.main;

import com.aluracursos.gutendex.service.RequestAPI;
import com.aluracursos.gutendex.model.APIResponseData;
import com.aluracursos.gutendex.model.BookData;
import com.aluracursos.gutendex.service.ConvertData;

import java.util.Scanner;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Optional;

public class Main {
  private Scanner input = new Scanner(System.in);
  private RequestAPI requestAPI = new RequestAPI();
  private ConvertData convertData = new ConvertData();
  private final String URL_BASE = "https://gutendex.com/books/";

  public void bookChallenge() {
    var dataAPI = getAPIResponse();

    // List of the first 10 books
    displayBooksByTen(dataAPI);

    // List of the 10 most downloaded books
    displayMostDownloadedBooks(dataAPI);

    // Statistics of a searched book
    System.out.print("\nIntroduzca el título del libro que está buscando: ");
    String bookTitle = input.nextLine();
    bookTitle = URLEncoder.encode(bookTitle, StandardCharsets.UTF_8);
    searchBookByTitle(bookTitle);
    input.close();
  }

  private APIResponseData getAPIResponse() {
    String url = URL_BASE;
    var json = requestAPI.requestData(url);
    System.out.println(json);
    APIResponseData apiResponseData = convertData.obtainData(json,
        APIResponseData.class);
    return apiResponseData;
  }

  public void displayBooksByTen(APIResponseData apiResponseData) {
    System.out.println("\nLista de 10 primeros libros\n===========================");
    List<BookData> books = apiResponseData.results();
    // for (int i = 0; i < 10; i++) {
    // BookData book = books.get(i);
    // System.out.println((i + 1) + ". " + book.title() + " - " +
    // book.authors().get(0).name());
    // System.out.println(book);
    // }
    books.stream().limit(5).forEach(System.out::println);
  }

  public void displayMostDownloadedBooks(APIResponseData apiResponseData) {
    System.out.println("\nLista de los 10 libros más descargados\n======================================");
    List<BookData> books = apiResponseData.results();
    books.stream()
        .sorted(Comparator.comparing(BookData::downloadCount).reversed())
        .limit(10)
        .map(book -> book.title().toUpperCase())
        .forEach(System.out::println);
  }

  public void searchBookByTitle(String bookTitle) {
    String url = URL_BASE + "?languages=es" + "&?search=" + bookTitle;
    var json = requestAPI.requestData(url);
    APIResponseData apiResponseData = convertData.obtainData(json,
        APIResponseData.class);

    Optional<BookData> searchResult = apiResponseData.results().stream()
        .filter(book -> book.title().toUpperCase().contains(bookTitle.toUpperCase()))
        .findFirst();
    // System.out.println("size: " + apiResponseData.results().size());

    if (searchResult.isPresent()) {
      System.out.println("\nLibro encontrado ");
      System.out.println(searchResult.get());
      System.out.println("\nEstadísticas de libro buscado");
      bookStatistics(apiResponseData.results());
    } else {
      System.out.println("\nLibro no encontrado");
    }

    // DoubleSummaryStatistics statistics = apiResponseData.results()
    // .stream()
    // .filter(books -> books.downloadCount() > 0)
    // .collect(Collectors.summarizingDouble(BookData::downloadCount));
    // // System.out.println("\nTitle: " + apiResponseData);
    // System.out.println("Average downloads: " + statistics.getAverage());
    // System.out.println("Max downloads: " + statistics.getMax());
    // System.out.println("Min downloads: " + statistics.getMin());
    // System.out.println("Number of registries: " + statistics.getCount());
  }

  private void bookStatistics(List<BookData> books) {
    DoubleSummaryStatistics statistics = books.stream()
        .filter(book -> book.downloadCount() > 0)
        .mapToDouble(BookData::downloadCount)
        .summaryStatistics();
    System.out.println("Average downloads: " + statistics.getAverage());
    System.out.println("Max downloads: " + statistics.getMax());
    System.out.println("Min downloads: " + statistics.getMin());
    System.out.println("Number of registries: " + statistics.getCount());
  }
}
