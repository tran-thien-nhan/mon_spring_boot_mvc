package aptech.apispb2.services;

import aptech.apispb2.models.Product;
import aptech.apispb2.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    public Optional<Product> findProductById(Long id) {
        return productRepository.findById(id);
    }
    public void deleteProductById(Long id) {
        productRepository.deleteById(id);
    }

    public Boolean findProductByImage(String imageName) {
        for (Product product : productRepository.findAll()) {
            if (product.getImage().equals(imageName)) {
                return true;
            }
        }
        return false;
    }
}
