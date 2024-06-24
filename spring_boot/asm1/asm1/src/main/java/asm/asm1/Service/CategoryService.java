package asm.asm1.Service;

import asm.asm1.Model.Category;
import asm.asm1.Model.Product;
import asm.asm1.Repository.CategoryRepository;
import asm.asm1.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id).orElse(null);
    }

    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }

    public void updateCategory(Category category) {
        categoryRepository.save(category);
    }

    public void deleteCategory(Long id) {
        Category category = categoryRepository.findById(id).orElse(null);
        if (category != null) {
            List<Product> products = category.getProducts();
            for (Product product : products) {
                productRepository.delete(product);
            }
            categoryRepository.deleteById(id);
        }
    }

    public List<Category> searchCategory(String keyword) {
        return categoryRepository.findByNameContaining(keyword);
    }
}
