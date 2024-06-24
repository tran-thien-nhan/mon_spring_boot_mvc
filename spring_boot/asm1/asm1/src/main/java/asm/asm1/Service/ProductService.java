package asm.asm1.Service;

import asm.asm1.Model.Product;
import asm.asm1.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class ProductService {
    private final Path rootLocation = Paths.get("uploads");

    @Autowired
    private ProductRepository productRepository;

    public ProductService() {
        try {
            if (!Files.exists(rootLocation)) {
                Files.createDirectories(rootLocation);
            }
        } catch (IOException e) {
            throw new RuntimeException("Could not create upload directory!", e);
        }
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    public List<Product> searchProduct(String keyword) {
        return productRepository.findByTitleContainingOrCodeContaining(keyword, keyword);
    }

    public Product createProduct(Product product, MultipartFile file) throws IOException {
        if (!file.isEmpty()) {
            String fileName = file.getOriginalFilename();
            Files.copy(file.getInputStream(), this.rootLocation.resolve(fileName));
            product.setImage(fileName);
        }
        return productRepository.save(product);
    }

    public Boolean updateProduct(Product product, MultipartFile file) throws IOException {
        if (productRepository.existsById(product.getId())) {
            // Retrieve existing product from the repository to get the current image filename
            Product existingProduct = productRepository.findById(product.getId()).orElse(null);

            if (existingProduct != null && !file.isEmpty()) {
                String fileName = file.getOriginalFilename();
                Path newFilePath = this.rootLocation.resolve(fileName);

                // Copy new file to the target location
                Files.copy(file.getInputStream(), newFilePath);

                // Delete the old image if it exists
                if (existingProduct.getImage() != null) {
                    Path oldImagePath = this.rootLocation.resolve(existingProduct.getImage());
                    Files.deleteIfExists(oldImagePath);
                }

                // Set the new image filename in the product object
                product.setImage(fileName);
            } else {
                // Retain the old image filename if no new file is provided
                product.setImage(existingProduct.getImage());
            }

            productRepository.save(product);
            return true;
        }
        return false;
    }


    public Boolean deleteProduct(Product product) {
        if (productRepository.existsById(product.getId())) {
            try {
                Path imagePath = this.rootLocation.resolve(product.getImage());
                Files.deleteIfExists(imagePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
            productRepository.delete(product);
            return true;
        }
        return false;
    }
}
