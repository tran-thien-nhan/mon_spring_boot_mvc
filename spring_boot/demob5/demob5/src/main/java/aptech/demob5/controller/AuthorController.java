package aptech.demob5.controller;

import aptech.demob5.models.Author;
import aptech.demob5.services.AuthorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthorController {
    @Autowired
    private AuthorService authorService;

    @GetMapping("/authors")
    public String getAllAuthors(Model model) {
        model.addAttribute("authors", authorService.getAllAuthors());
        return "author/list";
    }

    @GetMapping("/authors/new")
    public String showFormCreateAuthor(Model model) {
        model.addAttribute("author", new Author());
        return "author/create";
    }

    @PostMapping("/authors")
    public String createAuthor(@Valid @ModelAttribute("author") Author author,
                               BindingResult result) {
        if (result.hasErrors()) {
            return "author/create";
        }
        authorService.saveAuthor(author);
        return "redirect:/authors";
    }

    @GetMapping("/authors/edit/{id}")
    public String showFormEditAuthor(Model model, @PathVariable Long id) {
        Author author = authorService.getAuthorById(id);
        model.addAttribute("author", author);
        return "author/update";
    }

    @PostMapping("/authors/update/{id}")
    public String updateAuthor(@PathVariable Long id,
                               @Valid @ModelAttribute("author") Author author,
                               BindingResult result) {
        if (result.hasErrors()) {
            return "author/update";
        }

        // Cập nhật thông tin của tác giả dựa trên id
        author.setId(id); // Cập nhật id của tác giả
        authorService.saveAuthor(author);

        return "redirect:/authors";
    }

    @GetMapping("/authors/delete/{id}")
    public String deleteAuthor(@PathVariable Long id) {
        authorService.deleteAuthor(id);
        return "redirect:/authors";
    }
}
