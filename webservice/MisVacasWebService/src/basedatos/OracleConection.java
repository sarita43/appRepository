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

public class OracleConection {
	private Connection conexion;

	public static void main(String[] args) {
		OracleConection obconeccion = new OracleConection();
		obconeccion.Conectar();
	}

	public Connection getConexion() {
		return conexion;
	}

	public void setConexion(Connection conexion) {
		this.conexion = conexion;
	}

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
