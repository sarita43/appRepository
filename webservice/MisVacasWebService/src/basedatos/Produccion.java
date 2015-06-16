package basedatos;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

/**
 * Clase producción
 * 
 * @author Sara Martinez Lopez
 * 
 */
public class Produccion {

	// -----------------------------Atributos---------------------------//
	/** Id de la producción */
	private int id_produccion;
	/** Fecha de la producción */
	private Date fecha;
	/** Tipo de producción (Leche/Carne) */
	private String tipo;
	/** Cantida de producción (litros/Kg) */
	private int cantidad;
	/** Id del usuario */
	private String id_usuario;

	// ---------------------------Métodos------------------------//
	/**
	 * Constructor sin argumentos
	 */
	public Produccion() {
		setId_produccion(0);
	}

	/**
	 * Constructor con argumentos
	 * 
	 * @param id_produccion
	 * @param fecha
	 * @param tipo
	 * @param id_usuario
	 * @param cantidad
	 */
	public Produccion(int id_produccion, Date fecha, String tipo,
			String id_usuario, int cantidad) {
		setId_produccion(id_produccion);
		setFecha(fecha);
		setTipo(tipo);
		setCantidad(cantidad);
		setId_usuario(id_usuario);
	}

	/**
	 * Devuelve la lista de producción que hay en la base de datos cloud como
	 * String
	 * 
	 * @param id_usuario
	 * @return String Lista de produccion
	 */
	public String getProducciones(String id_usuario) {
		OracleConection c = new OracleConection();
		ArrayList<Produccion> listaProduccion = new ArrayList<Produccion>();
		Produccion p = new Produccion();

		c.Conectar();
		if (c.getConexion() != null) {
			try {
				Statement select = c.getConexion().createStatement();
				ResultSet result = select
						.executeQuery("SELECT * from produccion where id_usuario='"
								+ id_usuario + "'");
				while (result.next()) {
					try {
						p = new Produccion(result.getInt(1), result.getDate(2),
								result.getString(3), id_usuario,
								result.getInt(5));
					} catch (NumberFormatException e) {
						e.printStackTrace();
					}
					listaProduccion.add(p);
				}
			} catch (SQLException e) {
			}
		}
		if (listaProduccion.size() == 0) {
			listaProduccion.add(new Produccion());
		}
		Gson json = new GsonBuilder().setPrettyPrinting()
				.setDateFormat("dd-MM-yyyy").create();
		return json.toJson(listaProduccion);
	}

	/**
	 * Guarda en la base de datos cloud la lista de producción que se pasa por
	 * parámetro como String
	 * 
	 * @param listaProducciones
	 *            String Lista de producción
	 * @param id_usuario
	 *            String id usuario
	 */
	public void setProducciones(String listaProducciones, String id_usuario) {
		Gson json = new GsonBuilder().setPrettyPrinting()
				.setDateFormat("dd-MM-yyyy").create();
		String INSERT_RECORD = "INSERT INTO PRODUCCION(ID_PRODUCCION, FECHA, TIPO, ID_USUARIO,CANTIDAD) VALUES(?,?,?,?,?)";
		ArrayList<Produccion> lista = json.fromJson(listaProducciones,
				new TypeToken<ArrayList<Produccion>>() {
				}.getType());

		for (int i = 0; i < lista.size(); i++) {
			OracleConection c = new OracleConection();
			c.Conectar();
			if (c.getConexion() != null) {
				try {
					PreparedStatement pstmt = c.getConexion().prepareStatement(
							INSERT_RECORD);
					pstmt.setInt(1, lista.get(i).getId_produccion());
					pstmt.setDate(2, lista.get(i).getFecha());
					pstmt.setString(3, lista.get(i).getTipo());
					pstmt.setString(4, id_usuario);
					pstmt.setInt(5, lista.get(i).getCantidad());
					pstmt.executeUpdate();
				} catch (SQLException e) {
				}
			}
		}

	}
	
	/**
	 * Método que elimina la producción del usuario de la base de datos
	 * @param id_usuario String 
	 */
	public void eliminarProduccion(String id_usuario){
		OracleConection c = new OracleConection();
		c.Conectar();
		if (c.getConexion() != null) {
			try {
				Statement select = c.getConexion().createStatement();
				select.executeQuery("DELETE produccion where id_usuario='" + id_usuario
						+ "'");
			} catch (SQLException e) {
			}
		}
	}

	/**
	 * Devuelve el identificador de la producción
	 * 
	 * @return int. Id de la produción
	 */
	public int getId_produccion() {
		return id_produccion;
	}

	/**
	 * Guarda el identificador de la producción
	 * 
	 * @param id_produccion
	 *            int
	 */
	public void setId_produccion(int id_produccion) {
		this.id_produccion = id_produccion;
	}

	/**
	 * Devuelve la fecha de la producción
	 * 
	 * @return Date. Fecha de la producción
	 */
	public Date getFecha() {
		return fecha;
	}

	/**
	 * Guarda la fecha de la producción
	 * 
	 * @param fecha
	 *            Date. Fecha
	 */
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	/**
	 * Devuelve el tipo de la producción
	 * 
	 * @return String. Tipo de la producción
	 */
	public String getTipo() {
		return tipo;
	}

	/**
	 * Guarda el tipo de la producción
	 * 
	 * @param tipo
	 *            String. Tipo
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	/**
	 * Devuelve la cantidad de producción
	 * 
	 * @return int. Cantidad
	 */
	public int getCantidad() {
		return cantidad;
	}

	/**
	 * Guarda la cantidad de producción
	 * 
	 * @param cantidad
	 *            int
	 */
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	/**
	 * Devuelve el id del usuario
	 * 
	 * @return String id usuario
	 */
	public String getId_usuario() {
		return id_usuario;
	}

	/**
	 * Guarda el id de usuario
	 * 
	 * @param id_usuario
	 *            String
	 */
	public void setId_usuario(String id_usuario) {
		this.id_usuario = id_usuario;
	}
}
