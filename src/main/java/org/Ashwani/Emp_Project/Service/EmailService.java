package org.Ashwani.Emp_Project.Service;

import org.Ashwani.Emp_Project.Repository.UserRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Service
public class EmailService {

    private final UserRepository userRepository;
    public EmailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean emailExists(String email) {
        return userRepository.existsByEmail(email);
    }

    @Async
    public boolean sendEmail(String message, String subject, String to) {
        //Host
        String host = "smtp.gmail.com";
        String from = "goswamiashwani123@gmail.com";

        //get the system properties
        Properties properties = System.getProperties();
        System.out.println("PROPERTIES: " + properties);

        //Setting important properties to properties object
        properties.put("mail.smtp.host",host);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable","true");
        properties.put("mail.smtp.auth","true");

        //Step 1: Get the session
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from,"pibj cxux qwky yfre");
            }
        });
        session.setDebug(true);

        //Step 2: Compose the message
        MimeMessage mimeMessage = new MimeMessage(session);
        try{
            mimeMessage.setFrom(from);
            mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            mimeMessage.setSubject(subject);
            mimeMessage.setText(message);

            //Step 3: Send email using Transport class
            Transport.send(mimeMessage);
            System.out.println("Email sent...........");
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
