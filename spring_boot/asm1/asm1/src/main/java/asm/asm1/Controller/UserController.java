package asm.asm1.Controller;

import asm.asm1.Model.User;
import asm.asm1.Service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/home")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String login() {
        return "home/login";
    }

    @PostMapping("/login")
    public String checkLogin(@ModelAttribute User user, Model model) {
        try {
            if (userService.checkLogin(user.getEmail(), user.getPassword())) {
                User loggedInUser = userService.getUserByUsernameAndPassword(user.getEmail(), user.getPassword());
                return "redirect:/home/create"; // Redirect to the create page
            }
            model.addAttribute("error", "Invalid email or password.");
            return "home/login"; // Redirect back to login page with error
        } catch (Exception e) {
            model.addAttribute("error", "An error occurred. Please try again.");
            return "home/error"; // Redirect to error page in case of exception
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // Invalidate session
        return "redirect:/home/login"; // Redirect to login page after logout
    }

    @GetMapping("/search")
    public String searchUser(Model model, @RequestParam String keyword) {
        model.addAttribute("users", userService.searchUser(keyword));
        return "home/list"; // Placeholder return statement
    }

    @GetMapping("/users")
    public String getAllUsers(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "home/list"; // Placeholder return statement
    }

    @GetMapping("/create")
    public String showFormCreateUser(Model model) {
        model.addAttribute("user", new User());
        return "home/create"; // Placeholder return statement
    }

    @PostMapping("/create")
    public String createUser(@ModelAttribute User user) {
        userService.createUser(user);
        return "redirect:/home/users"; // Redirect to the users page
    }
}
