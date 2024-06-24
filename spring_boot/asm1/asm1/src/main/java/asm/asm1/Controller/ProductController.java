package asm.asm1.Controller;

import asm.asm1.Model.Category;
import asm.asm1.Model.Product;
import asm.asm1.Service.CategoryService;
import asm.asm1.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public String getAllProducts(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        return "product/list";
    }

    @GetMapping("/search")
    public String searchProduct(Model model, @RequestParam String keyword) {
        model.addAttribute("products", productService.searchProduct(keyword));
        return "product/list";
    }

    @GetMapping("/create")
    public String showFormCreateProduct(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("categories", categoryService.getAllCategories());
        return "product/create";
    }

    @PostMapping("/create")
    public String createProduct(@ModelAttribute Product product, @RequestParam("file") MultipartFile file) throws IOException {
        if (!file.isEmpty()) {
            String fileName = file.getOriginalFilename();
            product.setImage(fileName);
            productService.createProduct(product, file);
        } else {
            productService.saveProduct(product);
        }
        return "redirect:/product";
    }

    @GetMapping("/update/{id}")
    public String showFormUpdateProduct(Model model, @PathVariable Long id) {
        model.addAttribute("product", productService.getProductById(id));
        model.addAttribute("categories", categoryService.getAllCategories());
        return "product/update";
    }

    @PostMapping("/update")
    public String updateProduct(@ModelAttribute Product product, @RequestParam("file") MultipartFile file) throws IOException {
        Boolean result = productService.updateProduct(product, file);
        if (!result) {
            return "product/update";
        }
        return "redirect:/product";
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        Product product = productService.getProductById(id);
        Boolean result = productService.deleteProduct(product);
        if (!result) {
            return "product/list";
        }
        return "redirect:/product";
    }

    @GetMapping("/detail/{id}")
    public String showProductDetail(@PathVariable Long id, Model model) {
        Product product = productService.getProductById(id);
        model.addAttribute("product", product);
        return "product/detail";
    }
    @GetMapping("/sortByIdAsc")
    public String sortProductsByIdAsc(Model model) {
        List<Product> sortedProducts = productService.getAllProductsSortedByIdAsc();
        model.addAttribute("products", sortedProducts);
        return "product/list";
    }

    @GetMapping("/sortByIdDesc")
    public String sortProductsByIdDesc(Model model) {
        List<Product> sortedProducts = productService.getAllProductsSortedByIdDesc();
        model.addAttribute("products", sortedProducts);
        return "product/list";
    }

}
