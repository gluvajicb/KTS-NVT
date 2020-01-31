package tim20.KTS_NVT.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import tim20.KTS_NVT.model.User;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.UUID;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Async
    public void sendVerificationEmail(User user) {
        String uuid = UUID.randomUUID().toString();
        user.setVerificationToken(uuid);

        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(user.getEmail());
        mail.setFrom("ktsnvt@vpetrovic.com");
        mail.setSubject("Complete registration on KTS/NVT");
        String content = null;
        try {
            content = String.format(
                    "Hello %s,\nYou must activate your account. Please do so by following the link below.\n\nhttp://localhost:8080/security/verify?email=%s&token=%s",
                    user.getUsername(), URLEncoder.encode(user.getEmail(), "UTF-8"), URLEncoder.encode(uuid, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        mail.setText(content);
        mailSender.send(mail);
    }
}