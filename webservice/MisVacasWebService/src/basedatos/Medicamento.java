package basedatos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.Date;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Clase medicameno
 * 
 * @author Sara Martínez López
 * */
public class Medicamento {
	// ----------------------------Atributos-----------------------------//
	/** Id medicamento */
	private int id_medicamento;
	/** Fecha de medicamentos */
	private Date fecha;
	/** Tipo de medicamento */
	private String tipo;
	/** Descripcion del medicamento */
	private String descripcion;
	/** Id de la vaca */
	private String id_vaca;

	// ----------------------------------Métodos---------------------------------//
	/** Constructor del medicamento sin atributos */
	public Medicamento() {
	}

	/**
	 * Constructor del medicamento con atributos
	 * 
	 * @param id_medicamento
	 *            Id medicamento
	 * @param fecha
	 *            Fecha del medicamento
	 * @param tipo
	 *            Tipo de medicamento
	 * @param descripcion
	 *            Descripción del medicamento
	 * @param id_vaca
	 *            Id de la vaca
	 * */
	public Medicamento(int id_medicamento, Date fecha, String tipo,
			String descripcion, String id_vaca) {
		setId_medicamento(id_medicamento);
		setFecha(fecha);
		setTipo(tipo);
		setDescripcion(descripcion);
		setId_vaca(id_vaca);
	}

	/**
	 * Devuelve el arrayList de los medicamentos que tiene un animal en la base
	 * de datos. Crea la conexión a la base de datos, llama a la base de datos
	 * recogiendo todos los medicamentos de ella y los guarda en un arrayList
	 * lista que contiene medicamentos.
	 * 
	 * @param id_vaca
	 *            Id de la vaca para buscar sus medicamentos
	 * @return ArrayList<Medicamento> Lista de medicamentos del animal
	 * */
	public ArrayList<Medicamento> listaMedicamento(String id_vaca) {
		OracleConection c = new OracleConection();
		ArrayList<Medicamento> lista = new ArrayList<Medicamento>();
		c.Conectar();
		if (c.getConexion() != null) {
			try {
				Statement select = c.getConexion().createStatement();
				ResultSet result = select
						.executeQuery("SELECT * from medicamento where id_vaca='"
								+ id_vaca + "'");
				while (result.next()) {
					Medicamento medicamento = new Medicamento();
					try {
						medicamento.setId_medicamento(result.getInt(1));
						medicamento.setFecha(result.getDate(2));
						medicamento.setTipo(result.getString(3));
						medicamento.setDescripcion(result.getString(4));
						medicamento.setId_vaca(result.getString(5));
					} catch (NumberFormatException e) {
						e.printStackTrace();
					}
					lista.add(medicamento);
				}
			} catch (SQLException e) {
			}
		}
		return lista;
	}

	/**
	 * Devuelve el medicamento que tiene un animal en la base de datos. Crea la
	 * conexión a la base de datos, llama a la base de datos recogiendo el
	 * medicamento que se queire y los guarda en un nuevo medicamento
	 * 
	 * @param id_vaca
	 *            Id del animal
	 * @param id_medicamento
	 *            Id del medicamento
	 * @return Medicamento Medicamento encontrado en la base de datos
	 * */
	public Medicamento getMedicamento(String id_vaca, String id_medicamento) {
		OracleConection c = new OracleConection();
		Medicamento medicamento = new Medicamento();
		c.Conectar();
		if (c.getConexion() != null) {
			try {
				Statement select = c.getConexion().createStatement();
				ResultSet result = select
						.executeQuery("SELECT * from medicamento where id_vaca='"
								+ id_vaca
								+ "' and id_medicamento='"
								+ id_medicamento + "'");
				while (result.next()) {
					try {
						medicamento.setId_medicamento(result.getInt(1));
						medicamento.setFecha(result.getDate(2));
						medicamento.setTipo(result.getString(3));
						medicamento.setDescripcion(result.getString(4));
						medicamento.setId_vaca(result.getString(5));
					} catch (NumberFormatException e) {
						e.printStackTrace();
					}
				}
			} catch (SQLException e) {
			}
		}
		return medicamento;
	}

	/**
	 * Método que devuelve la lista de medicamentos como String. Recoge los
	 * medicamentos de listaMedicamento y los serializa con json.toJson. Si el
	 * animal no tiene medicamentos en la lista se le añade un medicamento a la
	 * lista vacio
	 * 
	 * @param id_vaca
	 *            Id de la vaca
	 * @return String Lista de medicamentos como String
	 * */
	public String listaMedicamentoString(String id_vaca) {
		Gson json = new GsonBuilder().setPrettyPrinting()
				.setDateFormat("dd-MM-yyyy").create();
		ArrayList<Medicamento> lista = listaMedicamento(id_vaca);
		if (lista.size() == 0) {
			lista.add(new Medicamento());
			String listaMedicamentos = json.toJson(lista);
			return listaMedicamentos;
		} else {
			String listaMedicamentos = json.toJson(lista);
			return listaMedicamentos;
		}
	}

	/**
	 * Método que devuelve el medicamento que hay en la base de datos como
	 * String. Recoge el medicamento de getMedicamento y lo serializa con
	 * json.toJson.
	 * 
	 * @param id_vaca
	 *            Id del animal
	 * @param id_medicamento
	 *            Id del medicamento
	 * @return String Medicamento como String
	 * */
	public String medicamentoString(String id_vaca, String id_medicamento) {
		Gson json = new GsonBuilder().setPrettyPrinting()
				.setDateFormat("dd-MM-yyyy").create();
		Medicamento me = getMedicamento(id_vaca, id_medicamento);
		String medicamento = json.toJson(me);
		return medicamento;
	}

	/**
	 * Elimina el medicamento de la base de datos. Hace la conexión con la base
	 * de datos y elimina el medicamento
	 * 
	 * @param id_medicamento
	 *            Id del medicamento
	 * @param id_vaca
	 *            Id de la vaca
	 * */
	public void eliminarMedicamento(String id_medicamento, String id_vaca) {
		OracleConection c = new OracleConection();
		c.Conectar();
		if (c.getConexion() != null) {
			try {
				Statement select = c.getConexion().createStatement();
				select.executeQuery("DELETE FROM medicamento WHERE id_medicamento='"
						+ id_medicamento + "' AND id_vaca='" + id_vaca + "'");
				select.close();
			} catch (SQLException e) {
			}
		}
	}

	/**
	 * Método que elimina todos los medicamentos de un animal de la base de
	 * datos
	 * 
	 * @param id_vaca
	 */
	public void eliminarMedicamentos(String id_vaca) {
		OracleConection c = new OracleConection();
		c.Conectar();
		if (c.getConexion() != null) {
			try {
				Statement select = c.getConexion().createStatement();
				select.executeQuery("DELETE FROM medicamento WHERE id_vaca='"
						+ id_vaca + "'");
			} catch (SQLException e) {
			}
		}
	}

	/**
	 * Método que añade un medicamento a la base de datos. Hace la conexión con
	 * la base de datos y añade el medicamento que se pasa por parametro como
	 * String. Se deserializa con json.fromJson y se introduce en la base de
	 * datos
	 * 
	 * @param medicamento
	 *            Medicamento como String para deserializar
	 * */
	public void añadirMedicamento(String medicamento) {
		System.out.println("MEDICAMENTO WEB SERVICE  " + medicamento);
		Gson json = new GsonBuilder().setPrettyPrinting()
				.setDateFormat("dd-MM-yyyy").create();
		String INSERT_RECORD = "INSERT INTO medicamento(id_medicamento,fecha ,tipo ,descripcion ,id_vaca) VALUES(?,?,?,?,?)";
		Medicamento m = json.fromJson(medicamento, Medicamento.class);
		OracleConection c = new OracleConection();
		c.Conectar();
		if (c.getConexion() != null) {
			try {
				PreparedStatement pstmt = c.getConexion().prepareStatement(
						INSERT_RECORD);
				pstmt.setInt(1, m.getId_medicamento());
				pstmt.setDate(2, m.getFecha());
				pstmt.setString(3, m.getTipo());
				pstmt.setString(4, m.getDescripcion());
				pstmt.setString(5, m.getId_vaca());
				pstmt.executeUpdate();
				pstmt.close();
			} catch (SQLException e) {
			}
		}
	}

	/**
	 * Devuelve el id del medicamento
	 * 
	 * @return int Id medicamento
	 * */
	public int getId_medicamento() {
		return id_medicamento;
	}

	/**
	 * Guarda el id del medicamento
	 * 
	 * @param id_medicamento
	 *            Id del medicamento
	 * */
	public void setId_medicamento(int id_medicamento) {
		this.id_medicamento = id_medicamento;
	}

	/**
	 * Devuelve la fecha del medicamento
	 * 
	 * @return java.sql.Date Fecha del medicamento
	 * */
	public Date getFecha() {
		return fecha;
	}

	/**
	 * Guarda la fecha del medicamento
	 * 
	 * @param fecha
	 *            Fecha del medicamento
	 * */
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	/**
	 * Devuelve el tipo del medicamento
	 * 
	 * @return String Tipo del medicamento
	 * */
	public String getTipo() {
		return tipo;
	}

	/**
	 * Guarda el tipo del medicamento
	 * 
	 * @param tipo
	 *            Tipo del medicamento
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	/**
	 * Devuelve el id de la vaca
	 * 
	 * @return String Id de la vaca
	 * */
	public String getId_vaca() {
		return id_vaca;
	}

	/**
	 * Guarda el id de la vaca
	 * 
	 * @param id_vaca
	 *            Id de la vaca
	 * */
	public void setId_vaca(String id_vaca) {
		this.id_vaca = id_vaca;
	}

	/**
	 * Devuelve la descripción del medicamento
	 * 
	 * @return String Descripción del medicamento
	 * */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * Guarda la descripción del medicamento
	 * 
	 * @param descripcion
	 *            Descripción del medicamento
	 * */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
}