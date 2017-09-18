package demo.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class GenericUtil {
@Autowired
RestTemplate restTemplate;


@Value("${SMTP_HOST}")
private String smtpHost;
@Value("${USERNAME}")
private String userName;
@Value("${PASSWORD}")
private String passWord;

	public String thirdPartyCall(String url, HttpEntity<String> entity){
		
		return (restTemplate.postForEntity(url, entity, String.class)).getBody();
		
	}
	
	public boolean sendMail(Map headerMap,String body) throws MessagingException  {

		/*String[] top = {userName};
		 String from = headerMap.get("from").toString();
	        String to = headerMap.get("to").toString();
	        String subject = headerMap.get("subject") != null ? headerMap.get("subject").toString() : "";*/
	        smtpHost = "smtp.gmail.com";
	//	sendFromGMail(from,passWord,top,subject,body);
	      //  popMail();
		
		//return true;
	    try {
	        String from = headerMap.get("from").toString();
	        String to = headerMap.get("to").toString();
	        String cc = headerMap.get("cc") != null ? headerMap.get("cc").toString() : "";
	        String subject = headerMap.get("subject") != null ? headerMap.get("subject").toString() : "";
	        smtpHost = "smtp.gmail.com";
	        Properties properties = System.getProperties();
	        properties.setProperty("mail.smtp.host", smtpHost);
	       /* properties.setProperty("mail.smtp.port", "587");
	       
	       // return true;
	        properties.put("mail.smtp.auth", "true");
	        properties.put("mail.smtp.starttls.enable", "true");*/
	        
	        properties.put("mail.smtp.socketFactory.port", "465"); //SSL Port
	        properties.put("mail.smtp.socketFactory.class",
					"javax.net.ssl.SSLSocketFactory"); //SSL Factory Class
	        properties.put("mail.smtp.auth", "true"); //Enabling SMTP Authentication
	        properties.put("mail.smtp.port", "465");
	        
	        Authenticator auth = new Authenticator() {
				//override the getPasswordAuthentication method
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication("testusertest1111@gmail.com", passWord);
				}
			};
			System.out.println(userName + " " +passWord);
			Session session = Session.getInstance(properties, auth);
	        
	        
	        /*Session session = Session.getInstance(properties,
	      		  new javax.mail.Authenticator() {
	      			protected PasswordAuthentication getPasswordAuthentication() {
	      				return new PasswordAuthentication(userName, passWord);
	      			}
	      		  });*/
	        
	       // Session session = Session.getDefaultInstance(properties);
	        MimeMessage message = new MimeMessage(session);


	        String consolidatedCCEmailIds = "";
	      //  message.setFrom(new InternetAddress(from));
	        String consolidatedRecipientEmailIds = to.replace(';', ',');
	        message.addRecipients(Message.RecipientType.TO, consolidatedRecipientEmailIds);

	        if (cc.length() > 0) {
	            consolidatedCCEmailIds = cc.replace(';', ',');
	            message.setRecipients(Message.RecipientType.CC, consolidatedCCEmailIds);
	        }
	        message.setSubject(subject);
	        message.setContent(body, headerMap.get("content").toString());
	        Transport.send(message);
	        return true;
	    } catch(MessagingException ex){
	        ex.printStackTrace();
	        return false;
	    }

	}
	
	
	
	
	
	private static void sendFromGMail(String from, String pass, String[] to, String subject, String body) throws MessagingException {
		Properties props = System.getProperties();
		String host = "smtp.googlemail.com";

		//props.put("mail.smtp.starttls.enable", "true");

		props.put("mail.smtp.ssl.trust", host);
		props.put("mail.smtp.user", from);
		props.put("mail.smtp.password", pass);
		props.put("mail.smtp.port", "587");
		props.put("mail.smtp.auth", "true");


		Session session = Session.getDefaultInstance(props);
		MimeMessage message = new MimeMessage(session);

		try {


		    message.setFrom(new InternetAddress(from));
		    InternetAddress[] toAddress = new InternetAddress[to.length];

		    // To get the array of addresses
		    for( int i = 0; i < to.length; i++ ) {
		        toAddress[i] = new InternetAddress(to[i]);
		    }

		    for( int i = 0; i < toAddress.length; i++) {
		        message.addRecipient(Message.RecipientType.TO, toAddress[i]);
		    }



		    message.setSubject(subject);
		    message.setText(body);


		    Transport transport = session.getTransport("smtp");


		    transport.connect(host, from, pass);
		    transport.sendMessage(message, message.getAllRecipients());
		    transport.close();

		}
		catch (AddressException ae) {
		    ae.printStackTrace();
		}

	
	
	}
	
	
	
	
	
	
	
	 public void popMail() {
	         String server = "pop.gmail.com";
	         String user = userName;
	         String pass = passWord;

	        Store store = null;
	        Folder folder = null;
	        try {
	            // get default session
	            Properties properties = System.getProperties();
	            Session session = Session.getDefaultInstance(properties, null);
	            session.setDebug(true);
	            // get a pop3 message store, and connect to it
	            store = session.getStore("pop3");
	            store.connect(server, user, pass);
	            // get the default folder
	            folder = store.getDefaultFolder();
	            if (folder == null) {
	                throw new Exception("No default folder");
	            }
	            // get the inbox
	            folder = folder.getFolder("INBOX");
	            if (folder == null) {
	                throw  new Exception("No POP3 INBOX");
	            }
	            // open the folder read only
	            folder.open(Folder.READ_ONLY);
	            // get the messages and print them
	            Message[] messages = folder.getMessages();
	            for (int i = 0; i < messages.length; i++) {
	                printMail(messages[i]);
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        } finally {
	            try {
	                if (folder != null) {
	                    folder.close(false);
	                }
	                if (store != null) {
	                    store.close();
	                }
	            } catch (MessagingException ex) {
	                ex.printStackTrace();
	            }
	        }
	    }

	
	 public void printMail(Message message) {
	        try {
	            // get header information
	            String from = null;
	            from = ((InternetAddress) message.getFrom()[0]).getPersonal();
	            // print sender details
	            System.out.println("From: " + from);
	            // get and print subject
	            String subj = message.getSubject();
	            System.out.println("Subject: " + subj);
	            // get the message itself
	            Part messagePart = message;
	            Object content = messagePart.getContent();
	            if (content instanceof Multipart) {
	                messagePart = ((Multipart) content).getBodyPart(0);
	                System.out.println("[ Multipart Message ]");
	            }
	            // get the content type
	            String contentType = messagePart.getContentType();
	            // if the content is plain text, print it
	            System.out.println("Content: " + contentType);
	            if (contentType.startsWith("text/plain") || 
	                contentType.startsWith("text/html")) {
	                InputStream is = messagePart.getInputStream();
	                BufferedReader br = new BufferedReader(
	                    new InputStreamReader(is)
	                );
	                String line = br.readLine();
	                while (line != null ) {
	                    System.out.println(line);
	                    line = br.readLine();
	                }
	            }
	            System.out.println("");
	        } catch (Exception ex) {
	            ex.printStackTrace();
	        }
	    }
	
	
	
	
}
