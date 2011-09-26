package com.robaone.api.business;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.Properties;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.URLName;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.robaone.api.data.AppDatabase;
import com.robaone.api.data.jdo.Message_queue_jdo;
import com.robaone.api.data.jdo.Message_queue_jdoManager;
import com.robaone.api.util.BaseProgram;
import com.robaone.dbase.hierarchial.ConnectionBlock;

public class msgsend extends BaseProgram {

	public static void main(String[] argv) {
		msgsend sent = new msgsend();
		sent.run_main(argv);
	}
	public void execute() throws Exception {



		try {
			ConnectionBlock block = new ConnectionBlock(){

				@Override
				public void run() throws Exception {
					Message_queue_jdoManager man = new Message_queue_jdoManager(this.getConnection());
					this.setPreparedStatement(man.prepareStatement(Message_queue_jdo.TO +" is not null order by "+Message_queue_jdo.CREATIONDATE +" desc"));
					this.setResultSet(this.getPreparedStatement().executeQuery());
					while(this.getResultSet().next()){
						/***
						 * Send message
						 */
						Message_queue_jdo record = man.bindMessage_queue(getResultSet());
						sendMessage(record);
					}
				}

			};
		}catch(Exception e){
			throw e;
		}
	}
	public void sendMessage(Message_queue_jdo message_jdo) throws Exception {
		String  to, subject = null, from = null, 
				cc = null, bcc = null, url = null;
		String mailhost = null;
		String mailer = "msgsend";
		String file = null;
		String protocol = null, host = null, user = null, password = null;
		boolean debug = false;

		int optind;
		from = message_jdo.getFrom();
		to = message_jdo.getTo();
		subject = message_jdo.getSubject();


		/*
		 * Process command line arguments.
		 */
		mailhost = this.getProperty("mail.host");
		user = this.getProperty("mail.user");
		password = this.getProperty("mail.password");
		debug = true;
		/*
		 * Initialize the JavaMail Session.
		 */
		Properties props = System.getProperties();
		// XXX - could use Session.getTransport() and Transport.connect()
		// XXX - assume we're using SMTP
		if (mailhost != null)
			props.put("mail.smtp.host", mailhost);

		// Get a Session object
		Session session = Session.getInstance(props, null);
		if (debug)
			session.setDebug(true);

		/*
		 * Construct the message and send it.
		 */
		Message msg = new MimeMessage(session);
		if (from != null)
			msg.setFrom(new InternetAddress(from));
		else
			msg.setFrom();

		msg.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse(to, false));
		if (cc != null)
			msg.setRecipients(Message.RecipientType.CC,
					InternetAddress.parse(cc, false));
		if (bcc != null)
			msg.setRecipients(Message.RecipientType.BCC,
					InternetAddress.parse(bcc, false));

		msg.setSubject(subject);

		String text = message_jdo.getBody();

			// We need a multipart message to hold the attachment.
			MimeBodyPart mbp1 = new MimeBodyPart();
			if(message_jdo.getHtml()){
				mbp1.setContent(message_jdo.getBody(),"text/html");
			}else{
				mbp1.setText(text);
			}
			MimeMultipart mp = new MimeMultipart();
			mp.addBodyPart(mbp1);
			msg.setContent(mp);
		
		msg.setHeader("X-Mailer", mailer);
		msg.setSentDate(new Date());

		// send the thing off
		Transport.send(msg);

		AppDatabase.writeLog("\nMail was sent successfully.");

		/*
		 * Save a copy of the message, if requested.
		 */




	}

	/**
	 * Read the body of the message until EOF.
	 */
	public static String collect(BufferedReader in) throws IOException {
		String line;
		StringBuffer sb = new StringBuffer();
		while ((line = in.readLine()) != null) {
			sb.append(line);
			sb.append("\n");
		}
		return sb.toString();
	}


}