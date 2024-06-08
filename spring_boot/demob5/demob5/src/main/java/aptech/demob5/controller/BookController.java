package aptech.demob5.controller;

import aptech.demob5.models.Author;
import aptech.demob5.models.Book;
import aptech.demob5.services.AuthorService;
import aptech.demob5.services.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class BookController {
    @Autowired
    private BookService bookService;

    @Autowired
    private AuthorService authorService;

    @GetMapping("/books")
    public String getAllBooks(Model model) {
        model.addAttribute("books", bookService.getAllBooks());
        return "book/list";
    }

    @GetMapping("/books/new")
    public String showFormCreateBook(Model model) {
        model.addAttribute("book", new Book());
        model.addAttribute("authors", authorService.getAllAuthors());
        return "book/create";
    }

    @PostMapping("/books")
    public String createBook(@Valid @ModelAttribute("book") Book book,
                               @RequestParam("authorId") Long authorId) {
        Author author = authorService.getAuthorById(authorId);
        book.setAuthor(author);
        bookService.saveBook(book);
        return "redirect:/books";
    }

    //delete
    @GetMapping("/books/delete/{id}")
    public String deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return "redirect:/books";
    }

    @GetMapping("/books/edit/{id}")
    public String showFormEditBook(Model model, @PathVariable Long id) {
        Book book = bookService.getBookById(id);
        model.addAttribute("book", book);
        model.addAttribute("authors", authorService.getAllAuthors());
        return "book/update";
    }

    @PostMapping("/books/update/{id}")
    public String updateBook(@PathVariable Long id,
                               @Valid @ModelAttribute("book") Book book,
                               @RequestParam("authorId") Long authorId) {
        Author author = authorService.getAuthorById(authorId);
        book.setAuthor(author);
        book.setId(id);
        bookService.saveBook(book);
        return "redirect:/books";
    }
}
