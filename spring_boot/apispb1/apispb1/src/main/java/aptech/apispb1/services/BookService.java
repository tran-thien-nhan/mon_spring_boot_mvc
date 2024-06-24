package aptech.apispb1.services;

import aptech.apispb1.models.Author;
import aptech.apispb1.models.Book;
import aptech.apispb1.repository.AuthorRepository;
import aptech.apispb1.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    private BookRepository bookRepository;
    private AuthorRepository authorRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> findAllBooks() {
        return bookRepository.findAll();
    }

    public Optional<Book> findBookById(Long id) {
        return bookRepository.findById(id);
    }

    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }

    public void deleteBookById(Long id) {
        bookRepository.deleteById(id);
    }

    // Check if author email exists
    public boolean isThisTitleExist(String title) {
        for (Book book : bookRepository.findAll()) {
            if (book.getTitle().equals(title)) {
                return true;
            }
        }
        return false;
    }
}
