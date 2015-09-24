/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package MailMedia;

import java.util.Date;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class MailSender {
    private final Properties settings;
    private static final String username = "cinemaone.unitn@gmail.com";
    private static final String password = "pythoniWeb";
    
    MailSender(){
        settings = System.getProperties();
        settings.put("mail.smtp.host", "smtp.gmail.com");
        settings.put("mail.smtp.port", "465");
        settings.put("mail.smtp.auth", "true");
        settings.put("mail.smtp.starttlsenable", "true");
        //  settings.put("mail.debug", "true");
    }
    MailSender(String[] names, String[] values){
        if(names.length != values.length)
            throw new IllegalArgumentException("names.length != values.length");
        settings = System.getProperties();
        for(int i=0; i<names.length; i++)
            settings.put(names[i], values[i]);
    }
    
    private Session getSession(){
        return Session.getInstance(settings, new
                                Authenticator(){
                                    @Override
                                    protected PasswordAuthentication
                                getPasswordAuthentication() {
                                    return new PasswordAuthentication(username, password);
                                }
                                });
    }
    
    public void sendMail(String to, String subject, String text, String allegato) throws MessagingException{
        Session session = getSession();
        //Create a new message
        Message msg = new MimeMessage(session);
//Set the FROM and TO fields –
        msg.setFrom(new InternetAddress("info@cinemaOne.it"));
        msg.setRecipients(Message.RecipientType.TO,
                InternetAddress.parse(to,false));
        msg.setSubject(subject);
        msg.setSentDate(new Date());
        
//attachment
        //Create the multipart message
        Multipart multipart = new MimeMultipart();
//Create the textual part of the message
        BodyPart messageBodyPart1 = new MimeBodyPart();
        messageBodyPart1.setText(text);
//Create the pdf part of the message
        if(allegato != null){
            DataSource source = new FileDataSource(allegato);
            BodyPart messageBodyPart2 = new MimeBodyPart();
            messageBodyPart2.setDataHandler( new DataHandler(source) );
            messageBodyPart2.setFileName("yourTicket");
            multipart.addBodyPart( messageBodyPart2 );
        }
//Add the parts to the Multipart message
        multipart.addBodyPart( messageBodyPart1 );
        
        msg.setContent(multipart);
        
//We create the transport object to actually send the e-mail
        Transport transport = session.getTransport("smtps");
        transport.connect(settings.getProperty("mail.smtp.host"), Integer.parseInt(settings.getProperty("mail.smtp.port")), username, password);
        transport.sendMessage(msg, msg.getAllRecipients());
        transport.close();
    }
    
    public void changePassword(String to, String link) throws MessagingException{        
        this.sendMail(to, "Modifica Password CinemaOne", "Testo che verrà gentilmente editato da Davide Corradini \n"+ link, null);
    }
    
    
    public static void main(String[] args){
        try {
            MailSender instance = new MailSender();
            //instance.sendMail("en.magnago@gmail.com", "test", "testtext", null);
            instance.changePassword("stefano__1994@hotmail.it", "http://www.google.it");
        } catch (MessagingException ex) {
           System.out.println("ERRORE NELL'INVIO DELLA MAIL" + ex);
        }
    }
    
}
