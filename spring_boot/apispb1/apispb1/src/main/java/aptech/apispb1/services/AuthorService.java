package aptech.apispb1.services;

import aptech.apispb1.models.Author;
import aptech.apispb1.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorService {
    private AuthorRepository authorRepository;
    public AuthorService(AuthorRepository _authorRepository) {
        authorRepository = _authorRepository;
    }

    public List<Author> findAll() {
        return authorRepository.findAll();
    }

    public Optional<Author> findById(Long id) {
        return authorRepository.findById(id);
    }
    public Author save(Author author) {
        return authorRepository.save(author);
    }
    public void deleteById(Long id) {
        authorRepository.deleteById(id);
    }

    //is author email exists
    public boolean isAuthorEmailExists(String email) {
        for (Author author : authorRepository.findAll()) {
            if (author.getEmail().equals(email)) {
                return true;
            }
        }
        return false;
    }
}
