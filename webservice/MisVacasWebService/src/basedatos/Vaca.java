package basedatos;

import java.util.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import com.google.gson.Gson;

//http://www.adictosaltrabajo.com/tutoriales/tutoriales.php?pagina=GsonJavaJSON

//Clase que se comunica con la base de datos para obtener los datos

public class Vaca {

	private String id_vaca;
	private String raza;
	private Date fecha_nacimiento;
	private String id_madre;
	private String foto;
	private String id_usuario;

	public Vaca() {

	}

	public Vaca(String id_usuario) {
		setId_usuario(id_usuario);

	}

	public Vaca(String id_vaca, String raza, Date fecha_nacimiento,
			String id_madre, String foto, String id_usuario) {
		setId_vaca(id_vaca);
		setRaza(raza);
		setFecha_nacimiento(fecha_nacimiento);
		setId_madre(id_madre);
		setFoto(foto);
		setId_usuario(id_usuario);
	}

	public ArrayList<Vaca> listaVacas(String id_usuario) {
		OracleConection c = new OracleConection();
		ArrayList<Vaca> lista = new ArrayList<Vaca>();
		c.Conectar();

		Vaca vaca = new Vaca();
		SimpleDateFormat formatoDeFecha = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss.S");
		if (c.getConexion() != null) {
			try {
				Statement select = c.getConexion().createStatement();
				ResultSet result = select
						.executeQuery("SELECT * from vaca where id_usuario='"
								+ id_usuario + "'");

				while (result.next()) {
					try {
						vaca = new Vaca(result.getString(1),
								result.getString(2),
								formatoDeFecha.parse(result.getString(3)),
								result.getString(4), result.getString(5),
								result.getString(6));
					} catch (NumberFormatException | ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					lista.add(vaca);

				}
			} catch (SQLException e) {
			}
		}
		return lista;
	}

	public Vaca getVaca(String id_vaca, String id_usuario) {
		OracleConection c = new OracleConection();
		c.Conectar();

		Vaca vaca = new Vaca();
		SimpleDateFormat formatoDeFecha = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss.S");
		if (c.getConexion() != null) {
			try {
				Statement select = c.getConexion().createStatement();
				ResultSet result = select
						.executeQuery("SELECT * from vaca where id_usuario='"
								+ id_usuario + "' and id_vaca='" + id_vaca
								+ "'");

				while (result.next()) {
					try {
						vaca = new Vaca(result.getString(1),
								result.getString(2),
								formatoDeFecha.parse(result.getString(3)),
								result.getString(4), result.getString(5),
								result.getString(6));
					} catch (NumberFormatException | ParseException e) {
						e.printStackTrace();
					}
				}
			} catch (SQLException e) {
			}
		}
		return vaca;
	}

	public String vacaString(String id_vaca, String id_usuario) {
		Vaca v = getVaca(id_vaca, id_usuario);
		Gson gson = new Gson();
		String vaca = gson.toJson(v);
		return vaca;
	}

	public String listaVacasString(String id_usuario) {
		ArrayList<Vaca> lista = listaVacas(id_usuario);
		Gson json = new Gson();
		String listaVacas = json.toJson(lista);
		return listaVacas;
	}

	public void añadirVaca(String id_vaca, String raza, Date fecha_nacimiento,
			String id_madre, String foto, String id_usuario) {
		OracleConection c = new OracleConection();
		c.Conectar();

		if (c.getConexion() != null) {
			try {
				Statement select = c.getConexion().createStatement();
				select.executeQuery("INSERT INTO vaca(id_vaca,raza,fecha_nacimiento,id_madre,foto,id_usuario) VALUES('"
						+ id_vaca
						+ "','"
						+ raza
						+ "','"
						+ fecha_nacimiento
						+ "','"
						+ id_madre
						+ "','"
						+ foto
						+ "','"
						+ id_usuario
						+ "')");

			} catch (SQLException e) {
			}
		}
	}

	public void eliminarVaca(String id_vaca, String id_usuario) {
		OracleConection c = new OracleConection();
		c.Conectar();

		if (c.getConexion() != null) {
			try {
				Statement select = c.getConexion().createStatement();
				select.executeQuery("DELETE FROM vaca WHERE id_vaca='"
						+ id_vaca + "' AND id_usuario='" + id_usuario + "'");

			} catch (SQLException e) {
			}
		}
	}

	public String getId_vaca() {
		return id_vaca;
	}

	public void setId_vaca(String id_vaca) {
		this.id_vaca = id_vaca;
	}

	public String getRaza() {
		return raza;
	}

	public void setRaza(String raza) {
		this.raza = raza;
	}

	public Date getFecha_nacimiento() {
		return fecha_nacimiento;
	}

	public void setFecha_nacimiento(Date fecha_nacimiento) {
		this.fecha_nacimiento = fecha_nacimiento;
	}

	public String getId_madre() {
		return id_madre;
	}

	public void setId_madre(String id_madre) {
		this.id_madre = id_madre;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public String getId_usuario() {
		return id_usuario;
	}

	public void setId_usuario(String id_usuario) {
		this.id_usuario = id_usuario;
	}

}
