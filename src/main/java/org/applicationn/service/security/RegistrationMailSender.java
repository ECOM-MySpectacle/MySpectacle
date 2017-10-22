package org.applicationn.service.security;

import java.io.Serializable;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletContext;

import org.omnifaces.util.Faces;

/**
 * Utility class for sending mails for registration activation
 * and password reset.
 *
 */
public class RegistrationMailSender implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final Logger logger = Logger.getLogger(RegistrationMailSender.class.getName());

    private static final String REGISTRATION_SUBJECT = "registration subject";
    private static final String REGISTRATION_MESSAGE = "registration message\nLink:";
    private static final String PW_RESET_SUBJECT = "password reset subject";
    private static final String PW_RESET_MESSAGE = "password reset message\nLink:";
    
    public static void sendRegistrationActivation(String to, String activationLink) {

        ServletContext context = Faces.getServletContext();
        String from = context.getInitParameter("mail.from");

        try {

            Message message = new MimeMessage(getSession(context));
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(to));
            message.setSubject(REGISTRATION_SUBJECT);
            message.setText(REGISTRATION_MESSAGE + activationLink);

            logger.log(Level.INFO, "Sending registration activation to {0}", to);
            
            Transport.send(message);

            logger.log(Level.INFO, "Mail sent succesfully!");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
    
    public static void sendPasswordReset(String to, String passwordResetLink) {

        ServletContext context = Faces.getServletContext();
        String from = context.getInitParameter("mail.from");

        try {

            Message message = new MimeMessage(getSession(context));
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(to));
            message.setSubject(PW_RESET_SUBJECT);
            message.setText(PW_RESET_MESSAGE + passwordResetLink);

            logger.log(Level.INFO, "Sending password reset to {0}", to);
            
            Transport.send(message);

            logger.log(Level.INFO, "Mail sent succesfully!");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
    
    private static Session getSession(ServletContext context) {

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", context.getInitParameter("mail.smtp.host"));
        props.put("mail.smtp.port", context.getInitParameter("mail.smtp.port"));

        final String username = context.getInitParameter("mail.username");
        final String password = context.getInitParameter("mail.password");
        
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
        
        return session;
    }

}
