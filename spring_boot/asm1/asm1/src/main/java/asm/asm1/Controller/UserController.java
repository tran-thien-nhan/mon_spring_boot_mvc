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
    public String checkLogin(@ModelAttribute User user, HttpSession session, Model model) {
        try {
            if (userService.checkLogin(user.getUsername(), user.getPassword())) {
                User loggedInUser = userService.getUserByUsernameAndPassword(user.getUsername(), user.getPassword());
                session.setAttribute("loggedInUser", loggedInUser);

                if ("admin".equals(loggedInUser.getRole())) {
                    return "redirect:/home/users"; // Redirect to user list page for admin
                }
                return "redirect:/product"; // Redirect to product list page for regular user
            }
            model.addAttribute("error", true);
            return "home/login"; // Redirect back to login page with error
        } catch (Exception e) {
            // Redirect to error page in case of exception
            return "home/error";
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
}
