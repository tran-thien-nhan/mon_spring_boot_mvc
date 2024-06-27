package asm.asm1.Service;

import asm.asm1.Model.User;
import asm.asm1.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

//    public Boolean checkLogin(String username, String password) {
//        return userRepository.existsByUsernameAndPassword(username, password);
//    }

    public Boolean checkLogin(String email, String password) {
        return userRepository.existsByEmailAndPassword(email, password);
    }

    // find user by id
    public User getUserByUsernameAndPassword(String username, String password) {
        for (User user : userRepository.findAll()) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    public User getUserByEmailAndPassword(String email, String password) {
        for (User user : userRepository.findAll()) {
            if (user.getEmail().equals(email) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    // search user by keyword
    public List<User> searchUser(String keyword) {
        return userRepository.findByUsernameContaining(keyword);
    }

    // get all users getAllUsers
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // create user
    public User createUser(User user) {
        return userRepository.save(user);
    }
}
