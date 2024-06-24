package asm.asm1.Repository;

import asm.asm1.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByTitleContainingOrCodeContaining(String keyword, String keyword1);
}
