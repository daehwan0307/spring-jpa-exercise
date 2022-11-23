package service;

import domain.Author;
import domain.Book;
import domain.dto.BookReponse;
import org.springframework.data.domain.Page;
import repository.AuthorRepository;
import repository.BookRepository;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class BookService {
    public BookService(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    private final BookRepository bookRepository;
    private  final AuthorRepository authorRepository;

    public List<BookReponse> findBooks(Pageable pageable){
        Page<Book> books = bookRepository.findAll(pageable);
        List<BookReponse> bookResponses = books.stream()
                .map(book->{
                    Optional<Author> optionalAuthor = authorRepository.findById(book.getAuthorId());
                    return  BookReponse.of(book,optionalAuthor.get().getName());
                }).collect(Collectors.toList());
        return bookResponses;
    }
}
