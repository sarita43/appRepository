package basedatos;

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
 * Clase para enviar emails
 * 
 * @author Sara Martínez López
 * 
 */
public class Mail extends javax.mail.Authenticator {

	// ----------------------------------Atributos--------------------------------//
	/** Usuario que envia el email */
	private String _user;
	/** Contraseña del correo */
	private String _pass;
	/** Array de Strings de emails a los que enviar correo */
	private String[] _to;
	/** Desde donde se envia el email */
	private String _from;
	/** Puerto */
	private String _port;
	/** Puerto del shocket **/
	private String _sport;
	/** Servidor por defecto */
	private String _host;
	/** Asunto del correo */
	private String _subject;
	/** Cuerpo del correo */
	private String _body;
	/** Autenticación */
	private boolean _auth;
	/** Modo debug */
	private boolean _debuggable;
	/** Mustipart */
	private Multipart _multipart;

	// -----------------------------------Métodos------------------------------------//
	/**
	 * Constructor de la clase
	 */
	public Mail() {
		_host = "smtp.googlemail.com"; // default smtp server
		_port = "465"; // default smtp port
		_sport = "465"; // default socketfactory port
		_user = ""; // username
		_pass = ""; // password
		_from = ""; // email sent from
		_subject = "Correo de autenticacion"; // email subject
		_body = ""; // email body
		_debuggable = false; // debug mode on or off - default off
		_auth = true; // smtp authentication - default on
		_multipart = new MimeMultipart();
	}

	/**
	 * Construnctor de la clase
	 * 
	 * @param user
	 *            String correo de la aplicación
	 * @param pass
	 *            String Contraseña del correo
	 */
	public Mail(String user, String pass) {
		this();
		_user = user;
		_pass = pass;
	}

	/**
	 * Método que envia el correo
	 * 
	 * @return boolean True si se envia el correo bien False no
	 * @throws Exception
	 */
	public boolean send() throws Exception {
		Properties props = _setProperties();
		if (!_user.equals("") && !_pass.equals("") && _to.length > 0
				&& !_from.equals("") && !_subject.equals("")
				&& !_body.equals("")) {
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
			return false;
		}
	}

	/**
	 * Devuelve la autenticacion del usuario
	 * 
	 * @return PasswordAuthentication
	 */
	@Override
	public PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication(_user, _pass);
	}

	/**
	 * Inicializa las propiedades y las devuelve
	 * 
	 * @return Properties
	 */
	private Properties _setProperties() {
		Properties props = new Properties();
		props.put("mail.smtp.host", _host);
		if (_debuggable) {
			props.put("mail.debug", "true");
		}
		if (_auth) {
			props.put("mail.smtp.auth", "true");
		}
		props.put("mail.smtp.port", _port);
		props.put("mail.smtp.socketFactory.port", _sport);
		props.put("mail.smtp.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.socketFactory.fallback", "false");
		return props;
	}

	/**
	 * Devuelve el cuerpo del correo
	 * 
	 * @return String
	 */
	public String getBody() {
		return _body;
	}

	/**
	 * Guarda el cuerpo del correo
	 * 
	 * @param _body
	 *            String
	 */
	public void setBody(String _body) {
		this._body = _body;
	}

	/**
	 * Devuelve de donde es enviado el correo
	 * 
	 * @return String
	 */
	public String getFrom() {
		return _from;
	}

	/**
	 * Guarda de donde se envia el correo
	 * 
	 * @param _from
	 *            String
	 */
	public void setFrom(String _from) {
		this._from = _from;
	}

	/**
	 * Devuelve a donde se envia el correo
	 * 
	 * @return String[]
	 */
	public String[] getTo() {
		return _to;
	}

	/**
	 * Guarda a donde se envia el correo
	 * 
	 * @param _to
	 *            String[]
	 */
	public void setTo(String[] _to) {
		this._to = _to;
	}

	/**
	 * Devuelve el servidor
	 * 
	 * @return String
	 */
	public String getHost() {
		return _host;
	}

	/**
	 * Guarda el servidor
	 * 
	 * @param _host
	 *            String
	 */
	public void setHost(String _host) {
		this._host = _host;
	}

	/**
	 * Devuelve el puerto
	 * 
	 * @return String
	 */
	public String getPort() {
		return _port;
	}

	/**
	 * Guarda el puerto
	 * 
	 * @param _port
	 *            String
	 */
	public void setPort(String _port) {
		this._port = _port;
	}

	/**
	 * Devuelve el puerto del servidor
	 * 
	 * @return String
	 */
	public String getSport() {
		return _sport;
	}

	/**
	 * Guarda el puerto del servidor
	 * 
	 * @param _sport
	 *            String
	 */
	public void setSport(String _sport) {
		this._sport = _sport;
	}

	/**
	 * Devuelve el asunto del correo
	 * 
	 * @return String
	 */
	public String getSubject() {
		return _body;
	}

	/**
	 * Guarda el asunto del correo
	 * 
	 * @param _subject
	 *            String
	 */
	public void setSubject(String _subject) {
		this._subject = _subject;
	}

}
