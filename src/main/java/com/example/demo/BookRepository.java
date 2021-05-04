package com.example.demo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@AllArgsConstructor
public class BookRepository {

    private Map<Integer, Book> books;

    @PostConstruct
    public void init() {
        books = new HashMap<>();
        books.put(1, new Book(1, "Book1", "Author1", "Text1"));
        books.put(2, new Book(2, "Book2", "Author2", "Text2"));
        books.put(3, new Book(3, "Book3", "Author3", "Text3"));
    }

    public Mono<Book> findBy(Integer id) {
        return Mono.justOrEmpty(books.get(id));
    }

    public Flux<Book> findAll() {
        return Flux.fromIterable(books.values());
    }

    public Mono<Book> deleteBy(Integer id) {
        return Mono.justOrEmpty(books.get(id));
    }

    public Mono<Book> save(Book book) {
        if (book.id == null) book.id = books.size() + 1;
        return Mono.justOrEmpty(books.putIfAbsent(book.id, book));
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonIgnoreProperties
    public static class Book {
        private Integer id;
        private String title;
        private String author;
        private String text;

        public Book(String title, String author, String text) {
            this.title = title;
            this.author = author;
            this.text = text;
        }
    }

}
