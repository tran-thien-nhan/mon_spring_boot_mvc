package com.example.demospb1.controllers;

import com.example.demospb1.models.Product;
import com.example.demospb1.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/products")
    public String listProduct(Model model) {
        var products = productService.findAll();
        model.addAttribute("products", products);
        return "list";
    }

    @GetMapping("/products/new")
    public String showFormCreateProduct(Model model) {
        model.addAttribute("product", new Product());
        return "create";
    }

    @PostMapping("/products")
    public String createProduct(@ModelAttribute("product") Product product) {
        productService.save(product);
        return "redirect:/products";
    }
}
