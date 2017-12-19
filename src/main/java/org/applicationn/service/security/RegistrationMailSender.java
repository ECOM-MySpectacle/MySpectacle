package org.applicationn.service.security;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.imageio.ImageIO;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.ServletContext;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.EnumMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.omnifaces.util.Faces;

/**
 * Utility class for sending mails for registration activation
 * and password reset.
 */
public class RegistrationMailSender implements Serializable
{

	private static final String QR_CODE_FILE = "./QRCode.png";

	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger(RegistrationMailSender.class.getName());

	private static final String REGISTRATION_SUBJECT = "registration subject";
	private static final String REGISTRATION_MESSAGE = "registration message\nLink:";
	private static final String PW_RESET_SUBJECT = "password reset subject";
	private static final String PW_RESET_MESSAGE = "password reset message\nLink:";

	public static void sendQRCode(String to, String name, String idSpectacle, int nbPlaces, String nomSpectacle)
	{


		//ServletContext context = Faces.getServletContext();
		String from = "ecommyspectacle@gmail.com";

		String subject = "Billeterie MySpetacle";
		String corps = "Bonjour, \nNous vous confirmons l'achat de " + nbPlaces + " du spectacle " + nomSpectacle + ".\n Veuillez trouver en pièce jointe votre e-billet sous la forme d'un QRCode. \n \n L'équipe MySpectacle";

		// Get system properties
		Properties properties = System.getProperties();

		// Setup mail server
		properties.setProperty("mail.smtp.host", "smtp.gmail.com");
		properties.setProperty("mail.smtp.port", "587");
		properties.setProperty("mail.smtp.starttls.enable", "true");
		properties.setProperty("mail.smtp.EnableSSL.enable", "true");
		properties.setProperty("mail.smtp.auth", "true");
		properties.setProperty("mail.from", from);
		properties.setProperty("mail.username", from);
		properties.setProperty("mail.user", from);
		properties.setProperty("mail.password", "hugedindoon");

		Session session = Session.getDefaultInstance(properties);

		try
		{
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from));
			Address[] recipients = InternetAddress.parse(to);
			message.setRecipients(Message.RecipientType.TO, recipients);
			message.setSubject(subject);

			// Create the message part
			MimeBodyPart messageBodyPart = new MimeBodyPart();
			// Now set the actual message
			messageBodyPart.setText(corps);
			// Create a multipar message
			Multipart multipart = new MimeMultipart();
			// Set text message part
			multipart.addBodyPart(messageBodyPart);

			// Part two is attachment
			messageBodyPart = new MimeBodyPart();

			generateQRCode(name + "," + idSpectacle + "," + nbPlaces);

			logger.log(Level.INFO, "QRCode generated", to);

			String filename = QR_CODE_FILE;
			DataSource source = new FileDataSource(filename);
			messageBodyPart.setDataHandler(new DataHandler(source));
			messageBodyPart.setFileName(filename);
			multipart.addBodyPart(messageBodyPart);

			// Send the complete message parts
			message.setContent(multipart);

			logger.log(Level.INFO, "Sending QRCode to {0}", to);

			// Transport.send(message);
			session.getTransport("smtps").sendMessage(message, recipients);

			logger.log(Level.INFO, "Mail sent succesfully!");

		}
		catch(MessagingException e)
		{
			throw new RuntimeException(e);
		}
	}


	private static void generateQRCode(String myCodeText)
	{

		String filePath = QR_CODE_FILE;
		int size = 250;
		String fileType = "png";
		File myFile = new File(filePath);
		try
		{

			Map<EncodeHintType, Object> hintMap = new EnumMap<EncodeHintType, Object>(EncodeHintType.class);
			hintMap.put(EncodeHintType.CHARACTER_SET, "UTF-8");

			// Now with zxing version 3.2.1 you could change border size (white border size to just 1)
			hintMap.put(EncodeHintType.MARGIN, 1); /* default = 4 */
			hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);

			QRCodeWriter qrCodeWriter = new QRCodeWriter();
			BitMatrix byteMatrix = qrCodeWriter.encode(myCodeText, BarcodeFormat.QR_CODE, size,
					size, hintMap);
			int CrunchifyWidth = byteMatrix.getWidth();
			BufferedImage image = new BufferedImage(CrunchifyWidth, CrunchifyWidth,
					BufferedImage.TYPE_INT_RGB);
			image.createGraphics();

			Graphics2D graphics = (Graphics2D) image.getGraphics();
			graphics.setColor(Color.WHITE);
			graphics.fillRect(0, 0, CrunchifyWidth, CrunchifyWidth);
			graphics.setColor(Color.BLACK);

			for(int i = 0; i < CrunchifyWidth; i++)
			{
				for(int j = 0; j < CrunchifyWidth; j++)
				{
					if(byteMatrix.get(i, j))
					{
						graphics.fillRect(i, j, 1, 1);
					}
				}
			}
			ImageIO.write(image, fileType, myFile);
		}
		catch(WriterException e)
		{
			e.printStackTrace();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}


	}


	public static void sendRegistrationActivation(String to, String activationLink)
	{

		ServletContext context = Faces.getServletContext();
		String from = context.getInitParameter("mail.from");

		try
		{

			Message message = new MimeMessage(getSession(context));
			message.setFrom(new InternetAddress(from));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(to));
			message.setSubject(REGISTRATION_SUBJECT);
			message.setText(REGISTRATION_MESSAGE + activationLink);

			logger.log(Level.INFO, "Sending registration activation to {0}", to);

			Transport.send(message);

			logger.log(Level.INFO, "Mail sent succesfully!");

		}
		catch(MessagingException e)
		{
			throw new RuntimeException(e);
		}
	}

	public static void sendPasswordReset(String to, String passwordResetLink)
	{

		ServletContext context = Faces.getServletContext();
		String from = context.getInitParameter("mail.from");

		try
		{

			Message message = new MimeMessage(getSession(context));
			message.setFrom(new InternetAddress(from));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(to));
			message.setSubject(PW_RESET_SUBJECT);
			message.setText(PW_RESET_MESSAGE + passwordResetLink);

			logger.log(Level.INFO, "Sending password reset to {0}", to);

			Transport.send(message);

			logger.log(Level.INFO, "Mail sent succesfully!");

		}
		catch(MessagingException e)
		{
			throw new RuntimeException(e);
		}
	}

	private static Session getSession(ServletContext context)
	{

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", context.getInitParameter("mail.smtp.host"));
		props.put("mail.smtp.port", context.getInitParameter("mail.smtp.port"));

		final String username = context.getInitParameter("mail.username");
		final String password = context.getInitParameter("mail.password");

		Session session = Session.getInstance(props,
				new javax.mail.Authenticator()
				{
					@Override
					protected PasswordAuthentication getPasswordAuthentication()
					{
						return new PasswordAuthentication(username, password);
					}
				});

		return session;
	}

}
