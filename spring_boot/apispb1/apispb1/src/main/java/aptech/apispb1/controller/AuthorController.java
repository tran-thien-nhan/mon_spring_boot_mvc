package aptech.apispb1.controller;

import aptech.apispb1.helper.ApiResponse;
import aptech.apispb1.models.Author;
import aptech.apispb1.models.Book;
import aptech.apispb1.services.AuthorService;
import aptech.apispb1.services.BookService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/authors")
public class AuthorController {
    private AuthorService authorService;
    private BookService bookService;
    public AuthorController(AuthorService _authorService, BookService _bookService) {
        authorService = _authorService;
        bookService = _bookService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<?>> findAll() {
        try {
            List<Author> authors = authorService.findAll();
            List<Book> books = bookService.findAllBooks();
            //List<Book> booksByAuthor = bookService.findBooksByAuthorId(1L);
            if (authors.isEmpty()) {
                return ResponseEntity.ok(ApiResponse.notFound(null, "Not found",  null));
            }
            return ResponseEntity.ok(ApiResponse.success(authors, "Success"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> findById(
            @PathVariable Long id) {
        try {
            Optional<Author> author = authorService.findById(id);
            if (author.isPresent()) {
                return ResponseEntity.ok(ApiResponse.success(author, "Success"));
            }
            return ResponseEntity.ok(ApiResponse.notFound(null, "Not found", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse<?>> createAuthor(@Valid @RequestBody Author author, BindingResult result) {
        try{
            if (result.hasErrors()) {
                return ResponseEntity.badRequest().body(ApiResponse.badRequest(result));
            }
            //duplicate value
            if (authorService.isAuthorEmailExists(author.getEmail())) {
                return ResponseEntity.ok(ApiResponse.duplicateValue(null, "Duplicate value", null));
            }
            Author newAuthor = authorService.save(author);
            return ResponseEntity.ok(ApiResponse.success(newAuthor, "Success"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.errorServer(null, "Server error",  null));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> updateAuthor(@PathVariable Long id,
                                                       @Valid @RequestBody Author author, BindingResult result){
        try {
            Optional<Author> authorExisted = authorService.findById(id);
            if(authorExisted.isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.notFound(null,"Author not found",null));
            }
            if(result.hasErrors()){
                return ResponseEntity.badRequest().body(ApiResponse.badRequest(result));
            }
            author.setId(id);
            Author updatedAuthor = authorService.save(author);
            return  ResponseEntity.ok()
                    .body(ApiResponse.success(updatedAuthor,"Update author successfully"));

        }catch (Exception ex){
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.errorServer(null,"ERROR_SERVER",null));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> deleteAuthor(@PathVariable Long id){
        try {
            Optional<Author> author = authorService.findById(id);
            if (author.isEmpty()) {
                return ResponseEntity.ok(ApiResponse.notFound(null, "Not found", null));
            }
            authorService.deleteById(id);
            return ResponseEntity.ok(ApiResponse.success(null, "Success"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
