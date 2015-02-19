package basedatos;

import java.net.InetAddress;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.naming.java.javaURLContextFactory;

/**
 * Clase que hace la conexión a la base de datos
 * 
 * @author Sara Martinez Lopez
 * */
public class OracleConection {
	
	//Atributos
	/** Conexión */
	private Connection conexion;
	
	//Métodos

//	public static void main(String[] args) {
//		OracleConection obconeccion = new OracleConection();
//		obconeccion.Conectar();
//	}

	/**
	 * Método que devuelve la conexión 
	 * @return Connection Conexión
	 * */
	public Connection getConexion() {
		return conexion;
	}

	/**
	 * Guarda la conexión
	 * @param conexion
	 * */
	public void setConexion(Connection conexion) {
		this.conexion = conexion;
	}

	/**
	 * Método que hace la conexión a la base de datos
	 * Crea la conexión con el driver jdbc. Necesitamos la IP(localhost) el puerto en el que escucha(1521) y el 
	 * usuario y la contraseña a la base de datos
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
