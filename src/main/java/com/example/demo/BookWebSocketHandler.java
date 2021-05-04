package com.example.demo;


import com.example.demo.BookRepository.Book;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Mono;

public class BookWebSocketHandler implements WebSocketHandler {

    private final BookRepository bookRepository;
    private final ObjectMapper objectMapper;

    public BookWebSocketHandler(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
        this.objectMapper = new ObjectMapper().configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
    }

    @Override
    public Mono<Void> handle(WebSocketSession webSocketSession) {
        return webSocketSession.send(webSocketSession.receive().map(webSocketMessage ->
                readBook(webSocketMessage.getPayloadAsText())
        ).flatMap(bookRepository::save).map(this::writeBook).map(webSocketSession::textMessage));
    }

    @SneakyThrows
    private Book readBook(String text) {
        return objectMapper.readValue(text, Book.class);
    }

    @SneakyThrows
    private String writeBook(Book book) {
        return objectMapper.writeValueAsString(book);
    }
}
