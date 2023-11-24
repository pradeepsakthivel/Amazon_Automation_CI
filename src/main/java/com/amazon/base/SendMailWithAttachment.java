package com.amazon.base;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
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

public class SendMailWithAttachment extends TestUtilities {

	ResourceBundle mailDetails = ResourceBundle.getBundle("Properties.notification");

	public void sendMail(int pass, int fail, int skip, String subject, String buildInfo) {

		String toAddressList = mailDetails.getString("To_Address");
		String[] toAddressListArray = toAddressList.split(",");
		for (String addrValue : toAddressListArray) {
			System.out.println("Values are: " + addrValue);
		}

		String user = mailDetails.getString("Sender_Email");
		String password = mailDetails.getString("Sender_Email_Key");

		Properties properties = System.getProperties();

		properties.setProperty("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.port", "587");
		properties.put("mail.smtp.starttls.enable", "true");
		//		properties.put("mail.smtp.port", "465"); 
		//		properties.put("mail.smtp.ssl.enable", "true");

		System.out.println("Set the properties");
		Session session = Session.getDefaultInstance(properties, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(user, password);
			}
		});
		System.out.println("Compose message started");

		try {
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(user));
			for (String addrValue : toAddressListArray) {
				message.addRecipient(Message.RecipientType.TO, new InternetAddress(addrValue));
			}
			//	    	 message.addRecipient(Message.RecipientType.TO,new InternetAddress(toAddress1));

			message.setSubject(subject);
			System.out.println("Set the subject");

			int tot = pass + fail + skip;

			// create MimeBodyPart object and set your message text
			String msg = "Hi,\r\n" + "\r\n"
					+ "An automated test execution was performed. Please find the attached report. \r\n\r\n";
			String msg2 = "Execution Summary\r\n";
			String execStatus = "Total tests run: " + tot + ", Passes: " + pass + ", Failures: " + fail + ", Skips: "
					+ skip + "\r\n\r\n";
			String buildDetailsHeading = "Component Details:\r\n";
			String buildDetailsContent = buildInfo;
			String msg5 = "\r\n\r\n" + "Thanks," + "\r\n" + "Automation Team";

			String mail_content = msg + msg2 + execStatus + buildDetailsHeading + buildDetailsContent + msg5;
			BodyPart messageBodyPart1 = new MimeBodyPart();

			messageBodyPart1.setText(mail_content);
			System.out.println("Set the mail body");

			// create new MimeBodyPart object and set DataHandler object to this object
			MimeBodyPart messageBodyPart2 = new MimeBodyPart();

			String filename = "target/" + reportFileName;// change accordingly "src/main/resources/geckodriver.exe"
			DataSource source = new FileDataSource(filename);
			messageBodyPart2.setDataHandler(new DataHandler(source));
			messageBodyPart2.setFileName(filename);

			// create new MimeBodyPart object and set DataHandler object to this object
			MimeBodyPart messageBodyPart3 = new MimeBodyPart();
			Path p = Paths.get("target/Test_report.xlsx"); // target/DCS_Validation.xlsx
			boolean exists = Files.exists(p);
			boolean notExists = Files.notExists(p);

			if (exists) {
				String filenameDCS = "target/Test_report.xlsx";// target/DCS_Validation.xls
				DataSource source1 = new FileDataSource(filenameDCS);
				messageBodyPart3.setDataHandler(new DataHandler(source1));
				messageBodyPart3.setFileName(filenameDCS);
			}

			// create Multipart object and add MimeBodyPart objects to this object
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart1);
			multipart.addBodyPart(messageBodyPart2);
			if (exists) {
				multipart.addBodyPart(messageBodyPart3);
			}

			// set the multiplart object to the message object
			message.setContent(multipart);

			// send message
			Transport.send(message);

			System.out.println("Composed message sent successfully");
			if (exists) {
				Files.delete(p);
			}
		} catch (MessagingException ex) {
			ex.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
