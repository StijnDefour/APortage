package be.ap.edu.aportage.managers;

import java.io.File;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.Future;

import java.io.*;
import java.net.InetAddress;
import java.util.Properties;
import java.util.Date;

import javax.mail.*;

import javax.mail.internet.*;

import com.sun.mail.smtp.*;


public class MyMailManager {

    private static String YOUR_DOMAIN_NAME = "sandbox6ab5a9a432374952abbbe4101f9956b6.mailgun.org";
    private static String API_KEY = "f4d179f585e0622eb5970d27d0b6a4d9-41a2adb4-82dc5405";


    public static void sendSmtpMail() throws MessagingException {
        Properties props = System.getProperties();
        props.put("mail.smtps.host", "smtp.mailgun.org");
        props.put("mail.smtps.auth", "true");


        try {
            Session session = Session.getInstance(props, null);
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress("YOU@sandbox6ab5a9a432374952abbbe4101f9956b6.mailgun.org"));
            InternetAddress[] addrs = InternetAddress.parse("yuakarima@icloud.com", false);
            msg.setRecipients(Message.RecipientType.TO, addrs);

            msg.setSubject("Hello");
            msg.setText("Testing some Mailgun awesomness");
            msg.setSentDate(new Date());

            SMTPTransport t = (SMTPTransport) session.getTransport("smtps");
            t.connect("smtp.mailgun.com", "postmaster@sandbox6ab5a9a432374952abbbe4101f9956b6.mailgun.org", "04f290f995194528a792eb74b0a74ffd-41a2adb4-e3822719");
            t.sendMessage(msg, msg.getAllRecipients());

            System.out.println("Response: " + t.getLastServerResponse());
            t.close();
        } catch (MessagingException e) {
            e.printStackTrace();
        }


    }
}
