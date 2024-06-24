package aptech.apispb1.controller;

import aptech.apispb1.dtos.AuthorDTO;
import aptech.apispb1.dtos.BookDTO;
import aptech.apispb1.helper.ApiResponse;
import aptech.apispb1.models.Author;
import aptech.apispb1.models.Book;
import aptech.apispb1.services.AuthorService;
import aptech.apispb1.services.BookService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/books")
public class BookController {
    private BookService bookService;
    private AuthorService authorService;

    public BookController(BookService _bookService, AuthorService _authorService) {
        bookService = _bookService;
        authorService = _authorService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<?>> findAll() {
        try {
            List<Book> books = bookService.findAllBooks();

            if (books.isEmpty()) {
                return ResponseEntity.ok(ApiResponse.notFound(null, "Not found",  null));
            }
            return ResponseEntity.ok(ApiResponse.success(books, "Success"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> findById(Long id) {
        try {
            Optional<Book> book = bookService.findBookById(id);
            if (book.isPresent()) {
                return ResponseEntity.ok(ApiResponse.success(book, "Success"));
            }
            return ResponseEntity.ok(ApiResponse.notFound(null, "Not found", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse<?>> createBook(@Valid @RequestBody Book book, BindingResult result) {
        try{
            if (result.hasErrors()) {
                return ResponseEntity.badRequest().body(ApiResponse.badRequest(result));
            }
            //duplicate value
            if (bookService.isThisTitleExist(book.getTitle())) {
                return ResponseEntity.ok(ApiResponse.duplicateValue(null, "Duplicate title", null));
            }
            Book saveBook = bookService.saveBook(book);
            ModelMapper modelMapper = new ModelMapper();
            BookDTO bookDTO = modelMapper.map(saveBook, BookDTO.class);
            Optional<Author> author = authorService.findById(saveBook.getAuthor().getId());
            if (author.isPresent() && saveBook.getAuthor() != null) {
                AuthorDTO authorDTO = modelMapper.map(saveBook.getAuthor(), AuthorDTO.class);
                bookDTO.setAuthorDto(authorDTO);
            }
            return ResponseEntity.ok(ApiResponse.success(saveBook, "Success"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.errorServer(null, "Server error",  null));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> updateBook(@PathVariable Long id,
                                                       @Valid @RequestBody Book book, BindingResult result){
        try {
            Optional<Book> bookExisted = bookService.findBookById(id);
            if(bookExisted.isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.notFound(null,"Author not found",null));
            }
            if(result.hasErrors()){
                return ResponseEntity.badRequest().body(ApiResponse.badRequest(result));
            }
            book.setId(id);
            Book updatedAuthor = bookService.saveBook(book);
            return  ResponseEntity.ok()
                    .body(ApiResponse.success(updatedAuthor,"Update author successfully"));

        }catch (Exception ex){
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.errorServer(null,"ERROR_SERVER",null));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> deleteBook(@PathVariable Long id){
        try {
            Optional<Book> book = bookService.findBookById(id);
            if (book.isEmpty()) {
                return ResponseEntity.ok(ApiResponse.notFound(null, "Not found", null));
            }
            bookService.deleteBookById(id);
            return ResponseEntity.ok(ApiResponse.success(null, "Success"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
