/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package baoph.utils;

import baoph.tblAccount.TblAccountDTO;
import java.util.Properties;
import java.util.Random;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.apache.log4j.Logger;

/**
 *
 * @author DELL
 */
public class SendEmail {
    
    static Logger logger = Logger.getLogger(SendEmail.class);
    public static String getRandom() {
        Random rnd = new Random();
        int number = rnd.nextInt(999999);
        logger.info(number);
        return String.format("%06d", number);
    }

    public static boolean sendEmail(TblAccountDTO user,  String code) {
        boolean test = false;
        String toEmail = user.getEmail();
        String fromEmail = "adviepage@gmail.com";
        String password = "adviepage123";
        try {
            Properties prop = new Properties();
            // set properties of mail server
            prop.put("mail.smtp.auth", true);
            prop.put("mail.smtp.starttls.enable", "true");
            prop.put("mail.smtp.host", "smtp.gmail.com");
            prop.put("mail.smtp.port", "25");
            
            // authentication my email and password
            Session session = Session.getInstance(prop, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(fromEmail, password);
                }
            });
            
            // create message
            Message mess = new MimeMessage(session);
            // set sender email
            mess.setFrom(new InternetAddress(fromEmail));
            // set recipient email
            mess.setRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
            // set subject title
            mess.setSubject("Attention! Vie page user Verification. ");
            // set text message
            mess.setText("What's up bro! We see that you registered to Vie page but you haven't activated your account."
                    + "Please verify your account by using this code: " + code);
            // transport message
            Transport.send(mess);
            logger.debug("Send mail successfully");
            test = true;
        } catch (Exception e) {
           logger.error("SendEmail_Exception :_" + e.getMessage());
        }
        return test;
    }
}
