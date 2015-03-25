package basedatos;

import java.net.InetAddress;
import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Clase que hace la conexi�n a la base de datos
 * 
 * @author Sara Martinez Lopez
 * */
public class OracleConection {
	// Atributos
	/** Conexi�n */
	private Connection conexion;

//	 M�todos
	 public static void main(String[] args) {
		 enviar("sara_c43@hotmail.com", "", "misvacasapp@gmail.com", "Mis Vacas APP", "Correo de autenticacion", "Correo enviado desde la aplicacion :)");
		 }
		 private static void enviar(String emailTo, String nameTo, String emailFrom, String nameFrom,
		 String subject, String body){
		 Mail m = new Mail("misvacasapp@gmail.com", "sara130490");
		 String[] toArr = {emailTo};
		 m.setTo(toArr);
		 m.setFrom(emailFrom);
		 m.setSubject(subject);
		 m.setBody(body);
		 try {
		 m.send();
		 } catch (Exception e) {
		 e.printStackTrace();
		 }
		 System.out.println("Correo enviado");
		 }

	
	/**
	 * M�todo que devuelve la conexi�n
	 * 
	 * @return Connection Conexi�n
	 * */
	public Connection getConexion() {
		return conexion;
	}

	/**
	 * Guarda la conexi�n
	 * 
	 * @param conexion
	 * */
	public void setConexion(Connection conexion) {
		this.conexion = conexion;
	}

	/**
	 * M�todo que hace la conexi�n a la base de datos Crea la conexi�n con el
	 * driver jdbc. Necesitamos la IP(localhost) el puerto en el que
	 * escucha(1521) y el usuario y la contrase�a a la base de datos
	 * 
	 * @return OracleConection
	 * */
	public OracleConection Conectar() {
		try {
			InetAddress addr = InetAddress.getLocalHost();
			String ip = addr.getHostAddress();
			System.out.println(ip);
			Class.forName("oracle.jdbc.OracleDriver");
			String BaseDeDatos = "jdbc:oracle:thin:@" + ip + ":1521:XE";
			conexion = DriverManager.getConnection(BaseDeDatos, "bbdd", "bbdd");
			if (conexion != null) {
				System.out.println("Conexion exitosa");
			} else {
				System.out.println("Conexion fallida");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return this;
	}
}