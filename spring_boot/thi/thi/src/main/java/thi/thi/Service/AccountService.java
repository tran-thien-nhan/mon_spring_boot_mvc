package thi.thi.Service;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import thi.thi.Model.Account;
import thi.thi.Repository.AccountRepository;

import java.util.List;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;

    //private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // get list
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    // get by id
    public Account getAccountByEmailAndPassword(String email, String password) {
        for (Account account : accountRepository.findAll()) {
            if (account.getEmail().equals(email) || account.getPassword().equals(password)) {
                return account;
            }
        }
        return null;
    }

    // create
    public Account createAccount(Account account) {
        //account.setPassword(hashPassword(account.getPassword()));
        if (account.getAge() < 18) {
            throw new RuntimeException("Age must be greater than 18");
        }
        return accountRepository.save(account);
    }

    // update
    public void updateAccount(Account account) {
        //account.setPassword(hashPassword(account.getPassword()));

        accountRepository.save(account);
    }

    // delete
    public void deleteAccount(String email) {
        accountRepository.deleteById(email);
    }

    // hash password
//    public String hashPassword(String password) { // dùng để mã hóa password
//        return passwordEncoder.encode(password);
//    }

    // giai ma password
//    public boolean checkPassword(String password, String hash) {
//        return passwordEncoder.matches(password, hash);
//    }

    //check login
    public boolean checkLogin(String email, String password) {
        Account account = accountRepository.findById(email).orElse(null);
        if (account != null) {
            //return passwordEncoder.matches(password, account.getPassword());
            //ko can hash password
            return true;
        }
        return false;
    }

    public boolean authenticate(String email, String rawPassword) {
        Account account = accountRepository.findById(email).orElse(null);
        if (account != null) {
            //return passwordEncoder.matches(rawPassword, account.getPassword());
            //ko can hash password
            return true;
        }
        return false;
    }
}
