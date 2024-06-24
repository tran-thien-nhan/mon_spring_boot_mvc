package asm.asm1.Controller;

import asm.asm1.Model.Product;
import asm.asm1.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService productService;

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
        return "product/create";
    }

    @PostMapping
    public String createProduct(@ModelAttribute Product product, @RequestParam("file") MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        product.setImage(fileName);
        productService.createProduct(product, file);
        return "redirect:/product";
    }

    @GetMapping("/update/{id}")
    public String showFormUpdateProduct(Model model, @PathVariable Long id) {
        model.addAttribute("product", productService.getProductById(id));
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
}
