package asm.asm1.Repository;

import asm.asm1.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Boolean existsByUsernameAndPassword(String username, String password);

    List<User> findByUsernameContaining(String keyword);

    Boolean existsByEmailAndPassword(String email, String password);
}
