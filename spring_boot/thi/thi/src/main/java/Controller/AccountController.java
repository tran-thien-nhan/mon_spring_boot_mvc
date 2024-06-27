package Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import thi.thi.Model.Account;
import thi.thi.Service.AccountService;

@Controller
@RequestMapping("/account")
public class AccountController {
    @Autowired
    private AccountService accountService;

//    @GetMapping
//    public String getAllAccounts(Model model) {
//        model.addAttribute("accounts", accountService.getAllAccounts());
//        return "account/list";
//    }

    @GetMapping("/login")
    public String login() {
        return "account/login"; // Đây là đường dẫn tới tệp login.html trong thư mục templates/account
    }

    //check login
    @PostMapping("/login")
    public String checkLogin(@ModelAttribute Account account, Model model) {
        try {
            if (accountService.authenticate(account.getEmail(), account.getPassword())) {
                Account loggedInAccount = accountService.getAccountByEmailAndPassword(account.getEmail(),account.getPassword());
                model.addAttribute("loggedInAccount", loggedInAccount);
                //return "redirect:account/create";
                return "redirect:/account/create";
            }
            model.addAttribute("error", true);
            return "account/login";
        } catch (Exception e) {
            return "/error";
        }
    }

    // show form create
    @GetMapping("/create")
    public String showFormCreate(Model model) {
        model.addAttribute("account", new Account());
        return "account/create";
    }

    // create
    @PostMapping("/create")
    public String createAccount(@ModelAttribute Account account) {
        accountService.createAccount(account);
        return "redirect:/account";
    }
}
