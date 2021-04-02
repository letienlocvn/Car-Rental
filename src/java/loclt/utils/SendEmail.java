/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package loclt.utils;

import java.util.Date;
import java.util.Properties;
import java.util.Random;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import loclt.authenticate.AuthenticateDTO;

/**
 *
 * @author WIN
 */
public class SendEmail {

    public String getRandom() {
        Random random = new Random();
        int number = random.nextInt(999999);
        return String.format("%06d", number);
    }

    public boolean sendEmail(String smtpServer, AuthenticateDTO userDTO,
            String subject, String body) throws Exception {
        //java.security.Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
        Properties props = System.getProperties();
        props.put("mail.smtp.host", smtpServer);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.starttls.enable", "true");
        final String login = "locltse141069@fpt.edu.vn";//”nth001@gmail.com”;//usermail
        final String pwd = "meomaydoremon!";//”password cua ban o day”;
        Authenticator pa = null; //default: no authentication
        
        if (login != null && pwd != null) { //authentication required?
            props.put("mail.smtp.auth", "true");
            pa = new Authenticator() {
                @Override
                public PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(login, pwd);
                }
            };
        }//else: no authentication
        Session session = Session.getInstance(props, pa);
        // — Create a new message –
        Message msg = new MimeMessage(session);
        // — Set the FROM and TO fields –
        msg.setFrom(new InternetAddress(login));
        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(userDTO.getEmail(), false));

        // — Set the subject and body text –
        msg.setSubject(subject);
        msg.setText(body);
        // — Set some other header information –
        msg.setHeader("X - Mailer", "LOTONtechEmail");
        msg.setSentDate(new Date());
        msg.saveChanges();
        // — Send the message –
        Transport.send(msg);
        System.out.println("Message sent OK.");
        return true;
    }

    /*public boolean sendEmail(AuthenticateDTO user) {
        String toEmail = user.getEmail();
        String fromEmail = "letienlocvn@gmail.com";
        String password = "meomaydoremon!18";

        try {
            Properties pr = new Properties();
            pr.setProperty("mail.smtp.host", "smtp.mail.com");
            pr.setProperty("mail.smtp.port", "587");
            pr.setProperty("mail.smtp.auth", "true");
            pr.setProperty("mail.smtp.starttls.enable", "true");
            pr.put("mail.smtp.socketFactory.port", "587");
            pr.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

            //Get Session
            Session sessionMail = Session.getInstance(pr, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(fromEmail, password);
                }
            });

            Message mess = new MimeMessage(sessionMail);
            mess.setFrom(new InternetAddress(fromEmail));
            mess.setRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));

            mess.setSubject("USER Email Verification");
            mess.setText("Registered successfully. Please verify your account using this code " + user.getCode());
            Transport.send(mess);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }*/
}
