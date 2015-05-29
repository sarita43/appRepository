package basedatos;

import java.sql.Blob;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Clase vaca
 * 
 * @author Sara Martinez Lopez
 * */
public class Vaca {
	// Atributos
	/** Id del animal */
	private String id_vaca;
	/** Raza del animal */
	private String raza;
	/** Fecha de nacimiento del animal */
	private Date fecha_nacimiento;
	/** Id de la madre del animal */
	private String id_madre;
	/** Foto del animal o foto por defecto */
	private String foto;
	/** Id del usuario del animal */
	private String id_usuario;
	/** Sexo del animal */
	private String sexo;

	// Métodos
	/**
	 * Constructor del animal sin atributos Crea un animal conn id=0
	 * */
	public Vaca() {
		setId_vaca("0");
	}

	/**
	 * Constructor del animal con atributos
	 * 
	 * @param id_vaca
	 *            Id del animal
	 * @param raza
	 *            Raza del animal
	 * @param fecha_nacimiento
	 *            Fecha de nacimiento
	 * @param id_madre
	 *            Id de la madre ddel animal
	 * @param id_usuario
	 *            Id del usuario del animal
	 * @param sexo
	 *            Sexo del animal
	 * @param foto
	 *            Foto del animal
	 * */
	public Vaca(String id_vaca, String raza, Date fecha_nacimiento,
			String id_madre, String id_usuario, String sexo, String foto) {
		setId_vaca(id_vaca);
		setRaza(raza);
		setFecha_nacimiento(fecha_nacimiento);
		setId_madre(id_madre);
		setFoto(foto);
		setId_usuario(id_usuario);
		setSexo(sexo);
	}

	/**
	 * Devuelve el arrayList de los animales que tiene un usuario en la base de
	 * datos. Crea la conexión a la base de datos, llama a la base de datos
	 * recogiendo todos los animales de ese usuario y los guarda en un arrayList
	 * lista que contiene Vacas.
	 * 
	 * @param id_usuario
	 *            Id del usuario para buscar sus animales
	 * @return ArrayList<Vaca> Lista de animales del usuario
	 * */
	public ArrayList<Vaca> listaVacas(String id_usuario) {
		OracleConection c = new OracleConection();
		ArrayList<Vaca> lista = new ArrayList<Vaca>();
		c.Conectar();
		Vaca vaca = new Vaca();
		if (c.getConexion() != null) {
			try {
				Statement select = c.getConexion().createStatement();
				ResultSet result = select
						.executeQuery("SELECT * from vaca where id_usuario='"
								+ id_usuario + "'");
				while (result.next()) {
					try {
						Blob blobFoto = result.getBlob(7);
						byte[] dataFoto = blobFoto.getBytes(1,
								(int) blobFoto.length());
						String foto = new String(dataFoto);
						vaca = new Vaca(result.getString(1),
								result.getString(2), result.getDate(3),
								result.getString(4), result.getString(5),
								result.getString(6), foto);
					} catch (NumberFormatException e) {
						e.printStackTrace();
					}
					lista.add(vaca);
				}
			} catch (SQLException e) {
			}
		}
		return lista;
	}

	public ArrayList<Vaca> listaVacas() {
		OracleConection c = new OracleConection();
		ArrayList<Vaca> lista = new ArrayList<Vaca>();
		c.Conectar();
		Vaca vaca = new Vaca();
		if (c.getConexion() != null) {
			try {
				Statement select = c.getConexion().createStatement();
				ResultSet result = select.executeQuery("SELECT * from vaca");
				while (result.next()) {
					try {
						Blob blobFoto = result.getBlob(7);
						byte[] dataFoto = blobFoto.getBytes(1,
								(int) blobFoto.length());
						String foto = new String(dataFoto);
						vaca = new Vaca(result.getString(1),
								result.getString(2), result.getDate(3),
								result.getString(4), result.getString(5),
								result.getString(6), foto);
					} catch (NumberFormatException e) {
						e.printStackTrace();
					}
					lista.add(vaca);
				}
			} catch (SQLException e) {
			}
		}
		return lista;
	}

	/**
	 * Devuelve el animal que tiene un usuario en la base de datos. Crea la
	 * conexión a la base de datos, llama a la base de datos recogiendo el
	 * animal que se quiere y los guarda en un nuevo animal
	 * 
	 * @param id_vaca
	 *            Id del animal
	 * @param id_usuario
	 *            Id del usuario
	 * @return Vaca Animal encontrado en la base de datos
	 * */
	public Vaca getVaca(String id_vaca, String id_usuario) {
		OracleConection c = new OracleConection();
		c.Conectar();
		Vaca vaca = new Vaca();
		if (c.getConexion() != null) {
			try {
				PreparedStatement ps = c.getConexion().prepareStatement(
						"SELECT * from vaca where id_usuario=? and id_vaca=?");
				ps.setString(1, id_usuario);
				ps.setString(2, id_vaca);
				ResultSet result = ps.executeQuery();
				while (result.next()) {
					try {
						Blob blobFoto = result.getBlob(7);
						byte[] dataFoto = blobFoto.getBytes(1,
								(int) blobFoto.length());
						String foto = new String(dataFoto);
						vaca = new Vaca(result.getString(1),
								result.getString(2), result.getDate(3),
								result.getString(4), result.getString(5),
								result.getString(6), foto);
					} catch (NumberFormatException e) {
						e.printStackTrace();
					}
				}
			} catch (SQLException e) {
			}
		}
		return vaca;
	}

	/**
	 * Método que devuelve el animal que hay en la base de datos como String.
	 * Recoge el animal de getVaca y lo serializa con json.toJson.
	 * 
	 * @param id_vaca
	 *            Id del animal
	 * @param id_usuario
	 *            DNI del usuario o Id del usuario
	 * @return String Vaca como String
	 * */
	public String vacaString(String id_vaca, String id_usuario) {
		Gson json = new GsonBuilder().setPrettyPrinting()
				.setDateFormat("dd-MM-yyyy").create();
		Vaca v = getVaca(id_vaca, id_usuario);
		String vaca = json.toJson(v);
		return vaca;
	}

	/**
	 * Método que devuelve la lista de animales como String. Recoge los animales
	 * de listaVaca y los serializa con json.toJson. Si el usuario no tiene
	 * animales en la lista se le añade un animal a la lista con el id=0
	 * 
	 * @param id_vaca
	 *            Id de la vaca
	 * @return String Lista de medicamentos como String
	 * */
	public String listaVacasString(String id_usuario) {
		Gson json = new GsonBuilder().setPrettyPrinting()
				.setDateFormat("dd-MM-yyyy").create();
		ArrayList<Vaca> lista = listaVacas(id_usuario);
		if (lista.size() == 0) {
			lista.add(new Vaca());
			String listaVacas = json.toJson(lista);
			return listaVacas;
		} else {
			String listaVacas = json.toJson(lista);
			return listaVacas;
		}
	}

	public String listaVacasString() {
		Gson json = new GsonBuilder().setPrettyPrinting()
				.setDateFormat("dd-MM-yyyy").create();
		ArrayList<Vaca> lista = listaVacas();
		if (lista.size() == 0) {
			lista.add(new Vaca());
			String listaVacas = json.toJson(lista);
			return listaVacas;
		} else {
			String listaVacas = json.toJson(lista);
			return listaVacas;
		}
	}

	/**
	 * Método que añade un animal a la base de datos. Hace la conexión con la
	 * base de datos y añade el animal que se pasa por parametro como String. Se
	 * deserializa con json.fromJson y se introduce en la base de datos
	 * 
	 * @param vaca
	 *            Vaca que como String para deserializar
	 * */
	public void añadirVaca(String vaca) {
		Gson json = new GsonBuilder().setPrettyPrinting()
				.setDateFormat("dd-MM-yyyy").create();
		String INSERT_RECORD = "INSERT INTO vaca(id_vaca,raza,fecha_nacimiento,id_madre,id_usuario,sexo,foto) VALUES(?,?,?,?,?,?,?)";
		Vaca v = json.fromJson(vaca, Vaca.class);
		OracleConection c = new OracleConection();
		c.Conectar();
		if (c.getConexion() != null) {
			try {
				String is = v.getFoto();
				byte[] datafoto = is.getBytes();
				Blob blobfoto = c.getConexion().createBlob();
				blobfoto.setBytes(1, datafoto);
				PreparedStatement pstmt = c.getConexion().prepareStatement(
						INSERT_RECORD);
				pstmt.setString(1, v.getId_vaca());
				pstmt.setString(2, v.getRaza());
				pstmt.setDate(3, v.getFecha_nacimiento());
				pstmt.setString(4, v.getId_madre());
				pstmt.setString(5, v.getId_usuario());
				pstmt.setString(6, v.getSexo());
				pstmt.setBlob(7, blobfoto);
				pstmt.executeUpdate();
				pstmt.close();
			} catch (SQLException e) {
			}
		}
	}

	/**
	 * Elimina el animal de la base de datos. Hace la conexión con la base de
	 * datos y elimina el animal
	 * 
	 * @param id_vaca
	 *            Id de la vaca
	 * @param id_usuario
	 *            Id del usuario
	 * */
	public void eliminarVaca(String id_vaca, String id_usuario) {
		OracleConection c = new OracleConection();
		c.Conectar();
		if (c.getConexion() != null) {
			try {
				Statement select = c.getConexion().createStatement();
				select.executeQuery("DELETE FROM medicamento WHERE id_vaca='"
						+ id_vaca + "'");
				select.executeQuery("DELETE FROM vaca WHERE id_vaca='"
						+ id_vaca + "' AND id_usuario='" + id_usuario + "'");
			} catch (SQLException e) {
			}
		}
	}

	public void eliminarVacas(String id_usuario) {
		ArrayList<Vaca> listaVacas = listaVacas(id_usuario);
		for (int i = 0; i < listaVacas.size(); i++) {
			OracleConection c = new OracleConection();
			c.Conectar();
			if (c.getConexion() != null) {
				try {
					Statement select = c.getConexion().createStatement();
					select.executeQuery("DELETE FROM medicamento WHERE id_vaca='"
							+ listaVacas.get(i).getId_vaca() + "'");
					select.executeQuery("DELETE FROM vaca WHERE id_usuario='"
							+ id_usuario + "'");
				} catch (SQLException e) {
				}
			}
		}
	}

	/**
	 * Devuelve el id del animal
	 * 
	 * @return String Id del animal
	 * */
	public String getId_vaca() {
		return id_vaca;
	}

	/**
	 * Guarda el id del animal
	 * 
	 * @param id_vaca
	 *            Id del animal
	 * */
	public void setId_vaca(String id_vaca) {
		this.id_vaca = id_vaca;
	}

	/**
	 * Devuelve la raza del animal
	 * 
	 * @return String Raza del animal
	 * */
	public String getRaza() {
		return raza;
	}

	/**
	 * Guarda la raza del animal
	 * 
	 * @param raza
	 *            Raza del animal
	 * */
	public void setRaza(String raza) {
		this.raza = raza;
	}

	/**
	 * Devuelve la fecha de nacimiento
	 * 
	 * @return Date Fecha de nacimiento
	 * */
	public Date getFecha_nacimiento() {
		return fecha_nacimiento;
	}

	/**
	 * Guarda la fecha de nacimiento del animal
	 * 
	 * @param fecha_nacimiento
	 *            Guarda la fecha del nacimiento del animal
	 * */
	public void setFecha_nacimiento(Date fecha_nacimiento) {
		this.fecha_nacimiento = fecha_nacimiento;
	}

	/**
	 * Devuelve el id de la madre del animal
	 * 
	 * @return String Id de la madre del animal
	 * */
	public String getId_madre() {
		return id_madre;
	}

	/**
	 * Guarda el id de la madre del animal
	 * 
	 * @param id_madre
	 *            Id de la madre del animal
	 * */
	public void setId_madre(String id_madre) {
		this.id_madre = id_madre;
	}

	/**
	 * Devuelve la foto del animal
	 * 
	 * @return byte[] Foto del animal
	 * */
	public String getFoto() {
		return foto;
	}

	/**
	 * Guarda la foto del animal
	 * 
	 * @param foto2
	 *            Foto del animal
	 * */
	public void setFoto(String foto2) {
		this.foto = foto2;
	}

	/**
	 * Devuelve el id del usuario del animal
	 * 
	 * @return String Id del usuario
	 * */
	public String getId_usuario() {
		return id_usuario;
	}

	/**
	 * Guarda el id del usuario del animal
	 * 
	 * @param id_usuario
	 *            Usuario del animal
	 * */
	public void setId_usuario(String id_usuario) {
		this.id_usuario = id_usuario;
	}

	/**
	 * Devuelve el sexo del animal
	 * 
	 * @return String Sexo del animal
	 * */
	public String getSexo() {
		return sexo;
	}

	/**
	 * Guarda el sexo del animal
	 * 
	 * @param sexo
	 *            Sexo del animal
	 * */
	public void setSexo(String sexo) {
		this.sexo = sexo;
	}
}