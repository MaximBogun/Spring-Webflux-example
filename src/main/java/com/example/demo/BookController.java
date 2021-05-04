package com.example.demo;

import static com.example.demo.BookRepository.*;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/book")
@AllArgsConstructor
public class BookController {

    private final BookRepository bookRepository;

    @GetMapping("/get/{id}")
    public Mono<Book> getBook(@PathVariable Integer id) {
        return bookRepository.findBy(id);
    }

    @PostMapping("/save")
    public Mono<Book> save(@RequestBody Book book) {
        return bookRepository.save(book);
    }

    @DeleteMapping("/delete/{id}")
    public Mono<Book> delete(@PathVariable Integer id) {
        return bookRepository.deleteBy(id);
    }

    @GetMapping("/get/all")
    public Flux<Book> getAll() {
        return bookRepository.findAll();
    }

}
