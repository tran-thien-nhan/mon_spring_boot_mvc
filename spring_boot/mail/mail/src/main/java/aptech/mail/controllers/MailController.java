package aptech.mail.controllers;

import aptech.mail.models.Mail;
import aptech.mail.services.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/mail")
public class MailController {
    @Autowired
    private MailService mailService;

    @PostMapping
    public ResponseEntity<?> sendMail(@RequestBody Mail mail){
        try {
            mailService.sendHTMLEmail(mail);
            return ResponseEntity.ok("Mail sent successfully");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error server: " + e.getMessage());
        }
    }

    @PostMapping("/new")
    public ResponseEntity<?> sendMailNew(@RequestBody Mail mail){
        try {
            mailService.sendHTMLEmailNew(mail);
            return ResponseEntity.ok("Mail sent successfully");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error server: " + e.getMessage());
        }
    }

    @PostMapping("/file")
    public ResponseEntity<?> sendMailWithFile(@ModelAttribute Mail mail, @RequestParam (required = false) MultipartFile file){
        try {
            mailService.sendHTMLEmailWithFile(mail, file);
            return ResponseEntity.ok("Mail sent successfully");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error server: " + e.getMessage());
        }
    }

    @PostMapping("/multiple")
    public ResponseEntity<?> sendMailToMultipleRecipients(@RequestBody Mail mail){
        try {
            mailService.sendHTMLEmailToMultipleRecipients(mail);
            return ResponseEntity.ok("Mail sent successfully");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error server: " + e.getMessage());
        }
    }

    @PostMapping("/multiplemail")
    public ResponseEntity<?> sendMailToMultipleRecipientsbcc(@RequestBody Mail mail){
        try {
            mailService.sendHTMLEmailToMultipleRecipientsBBC(mail);
            return ResponseEntity.ok("Mail sent successfully");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error server: " + e.getMessage());
        }
    }

    @PostMapping("/multiple-savedb")
    public ResponseEntity<?> sendMailToMultipleRecipientscc(@RequestBody Mail mail){
        try {
            mailService.sendHTMLEmailToMultipleRecipientsThenSaveDB(mail);
            return ResponseEntity.ok("Mail sent successfully");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error server: " + e.getMessage());
        }
    }
}
