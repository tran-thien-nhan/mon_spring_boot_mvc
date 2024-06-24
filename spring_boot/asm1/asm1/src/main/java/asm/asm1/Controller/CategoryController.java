package asm.asm1.Controller;

import asm.asm1.Model.Category;
import asm.asm1.Service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public String getAllCategories(Model model) {
        model.addAttribute("categories", categoryService.getAllCategories());
        return "category/list";
    }

    @GetMapping("/search")
    public String searchCategory(Model model, @RequestParam String keyword) {
        model.addAttribute("categories", categoryService.searchCategory(keyword));
        return "category/list";
    }

    @GetMapping("/create")
    public String showFormCreateCategory(Model model) {
        model.addAttribute("category", new Category());
        return "category/create";
    }

    @PostMapping
    public String createCategory(@ModelAttribute Category category) {
        categoryService.createCategory(category);
        return "redirect:/category";
    }

    @GetMapping("/update/{id}")
    public String showFormUpdateCategory(Model model, @PathVariable Long id) {
        model.addAttribute("category", categoryService.getCategoryById(id));
        return "category/update";
    }

    @PostMapping("/update")
    public String updateCategory(@ModelAttribute Category category) {
        categoryService.updateCategory(category);
        return "redirect:/category";
    }

    @GetMapping("/delete/{id}")
    public String deleteCategory(@PathVariable Long id) {
        Category category = categoryService.getCategoryById(id);
        categoryService.deleteCategory(category.getId());
        return "redirect:/category";
    }
}
