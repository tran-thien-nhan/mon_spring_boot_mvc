package aptech.mail.services;

import aptech.mail.models.Mail;
import aptech.mail.repository.MailRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class MailService{
    @Autowired
    private MailRepository mailRepository;

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String sender;

    public void sendHTMLEmail(Mail mail) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage(); // tạo ra một mail
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,true); // tạo ra một helper để giúp việc gửi mail
        helper.setFrom(sender); // set người gửi
        helper.setTo(mail.getToMails()); // set người nhận
        helper.setSubject(mail.getSubject()); // set tiêu đề
        helper.setText(mail.getContent(),true); // set nội dung
        mailSender.send(mimeMessage); // gửi mail
    }

    public void sendHTMLEmailNew(Mail mail) throws MessagingException, UnsupportedEncodingException {
        if (mail.getToMails() == null || mail.getToMails().isEmpty()) {
            throw new MessagingException("To address must not be null or empty");
        }
        MimeMessage mimeMessage = mailSender.createMimeMessage(); // tạo ra một mail
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,true); // tạo ra một helper để giúp việc gửi mail
        helper.setFrom(new InternetAddress(sender,"FPT APTECH")); // set người gửi
        helper.setTo(mail.getToMails()); // set người nhận
        helper.setSubject(mail.getSubject()); // set tiêu đề
        helper.setText(mail.getContent(),true); // set nội dung
        mailSender.send(mimeMessage); // gửi mail
    }

    public void sendHTMLEmailWithFile(Mail mail, MultipartFile file) throws MessagingException, UnsupportedEncodingException {
        if (mail.getToMails() == null || mail.getToMails().isEmpty()) {
            throw new MessagingException("To address must not be null or empty");
        }
        MimeMessage mimeMessage = mailSender.createMimeMessage(); // tạo ra một mail
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,true); // tạo ra một helper để giúp việc gửi mail
        helper.setFrom(new InternetAddress(sender,"FPT APTECH")); // set người gửi
        helper.setTo(mail.getToMails()); // set người nhận
        helper.setSubject(mail.getSubject()); // set tiêu đề
        helper.setText(mail.getContent(),true); // set nội dung
        helper.addAttachment(file.getOriginalFilename(),file); // thêm file đính kèm
        mailSender.send(mimeMessage); // gửi mail
    }

    //gửi cho nhiều email cùng lúc
    public void sendHTMLEmailToMultipleRecipients(Mail mail) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setFrom(sender);
        String [] toArrayMails = new String[mail.getToAddresses().size()];
        mail.getToAddresses().toArray(toArrayMails);
        helper.setTo(toArrayMails);
        helper.setSubject(mail.getSubject());
        helper.setText(mail.getContent(), true);
        mailSender.send(mimeMessage);
    }

    // gửi cho nhiều email cùng lúc nhưng không để lộ danh sách người nhận
    public void sendHTMLEmailToMultipleRecipientsBBC(Mail mail) throws MessagingException {
        for (String ma : mail.getToAddresses()) {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom(sender);
            helper.setTo(ma);
            helper.setSubject(mail.getSubject());
            helper.setText(mail.getContent(), true);
            mailSender.send(mimeMessage);
        }
    }

    public void sendHTMLEmailToMultipleRecipientsThenSaveDB(Mail mail) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setFrom(sender);
        String [] toArrayMails = new String[mail.getToAddresses().size()];
        mail.getToAddresses().toArray(toArrayMails);
        helper.setTo(toArrayMails);
        helper.setSubject(mail.getSubject());
        helper.setText(mail.getContent(), true);
        mailSender.send(mimeMessage);

        // lưu vào database
        String listMailString = String.join(",", mail.getToAddresses());
        mail.setToMails(listMailString);
        mail.setTimestamp(LocalDateTime.now());
        mailRepository.save(mail);
    }
}
