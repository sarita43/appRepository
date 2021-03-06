package com.example.misvacasapp.vista;

import java.util.Date;
import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 * Clase para el envio email
 * 
 */

public class Mail extends javax.mail.Authenticator { 
	private String _user; 
	private String _pass; 

	private String[] _to; 
	private String _from; 

	private String _port; 
	private String _sport; 

	private String _host; 

	private String _subject; 
	private String _body; 

	private boolean _auth; 

	private boolean _debuggable; 

	private Multipart _multipart; 

	public Mail() { 
		_host = "smtp.googlemail.com"; // default smtp server 
		_port = "465"; // default smtp port 
		_sport = "465"; // default socketfactory port 

		_user = "misvacasapp@gmail.es"; // username 
		_pass = "sara130490"; // password 
		_from = "Mis Vacas APP"; // email sent from 
		_subject = "Correo de autenticacion"; // email subject 
		_body = ""; // email body 

		_debuggable = false; // debug mode on or off - default off 
		_auth = true; // smtp authentication - default on 

		_multipart = new MimeMultipart(); 

	
	} 

	public Mail(String user, String pass) { 
		this(); 

		_user = user; 
		_pass = pass; 
	} 

	public boolean send() throws Exception { 
		Properties props = _setProperties(); 

		System.out.println("ENVIANDO....");
		if(!_user.equals("") && !_pass.equals("") && _to.length > 0 && !_from.equals("") && !_subject.equals("") && !_body.equals("")) { 
			System.out.println("Creando sesion....");
			Session session = Session.getInstance(props, this); 

			MimeMessage msg = new MimeMessage(session); 

			msg.setFrom(new InternetAddress(_from)); 

			InternetAddress[] addressTo = new InternetAddress[_to.length]; 
			for (int i = 0; i < _to.length; i++) { 
				addressTo[i] = new InternetAddress(_to[i]); 
			} 
			msg.setRecipients(MimeMessage.RecipientType.TO, addressTo); 

			msg.setSubject(_subject); 
			msg.setSentDate(new Date()); 

			// setup message body 
			BodyPart messageBodyPart = new MimeBodyPart(); 
			messageBodyPart.setText(_body); 
			_multipart.addBodyPart(messageBodyPart); 

			// Put parts in message 
			msg.setContent(_multipart); 

			// send email 
			Transport.send(msg); 

			return true; 
		} else { 
			System.out.println("NO ENVIADO....");
			return false; 
		} 
	} 

	@Override 
	public PasswordAuthentication getPasswordAuthentication() { 
		return new PasswordAuthentication(_user, _pass); 
	} 

	private Properties _setProperties() { 
		Properties props = new Properties(); 

		props.put("mail.smtp.host", _host); 

		if(_debuggable) { 
			props.put("mail.debug", "true"); 
		} 

		if(_auth) { 
			props.put("mail.smtp.auth", "true"); 
		} 

		props.put("mail.smtp.port", _port); 
		props.put("mail.smtp.socketFactory.port", _sport); 
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory"); 
		props.put("mail.smtp.socketFactory.fallback", "false"); 

		return props; 
	} 

	// the getters and setters 
	public String getBody() { 
		return _body; 
	} 

	public void setBody(String _body) { 
		this._body = _body; 
	} 

	public String getFrom() { 
		return _from; 
	} 

	public void setFrom(String _from) { 
		this._from = _from; 
	} 

	public String[] getTo() { 
		return _to; 
	} 

	public void setTo(String[] _to) { 
		this._to = _to; 
	} 

	public String getHost() { 
		return _host; 
	} 

	public void setHost(String _host) { 
		this._host = _host; 
	}   

	public String getPort() { 
		return _port; 
	} 

	public void setPort(String _port) { 
		this._port = _port; 
	}   

	public String getSport() { 
		return _sport; 
	} 

	public void setSport(String _sport) { 
		this._sport = _sport; 
	}   

	public String getSubject() { 
		return _body; 
	} 

	public void setSubject(String _subject) { 
		this._subject = _subject; 
	}   
}