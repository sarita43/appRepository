package basedatos;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Blob;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Vaca {

	private String id_vaca;
	private String raza;
	private Date fecha_nacimiento;
	private String id_madre;
	private byte[] foto;
	private String id_usuario;
	private String sexo;

	public Vaca() {
		setId_vaca("0");
	}

	public Vaca(String id_vaca, String raza, Date fecha_nacimiento,
			String id_madre, String id_usuario, String sexo, byte[] foto) {
		setId_vaca(id_vaca);
		setRaza(raza);
		setFecha_nacimiento(fecha_nacimiento);
		setId_madre(id_madre);
		setFoto(foto);
		setId_usuario(id_usuario);
		setSexo(sexo);
	}

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
						vaca = new Vaca(result.getString(1),
								result.getString(2), result.getDate(3),
								result.getString(4), result.getString(5),
								result.getString(6),result.getBytes(7));
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

	public Vaca getVaca(String id_vaca, String id_usuario) {
		OracleConection c = new OracleConection();
		c.Conectar();

		Vaca vaca = new Vaca();
		if (c.getConexion() != null) {
			try {
				PreparedStatement ps = c.getConexion().prepareStatement( "SELECT * from vaca where id_usuario=? and id_vaca=?" );
				ps.setString( 1, id_usuario);
				ps.setString( 2, id_vaca);
				ResultSet result = ps.executeQuery();
				
				while (result.next()) {
					try {
						vaca = new Vaca(result.getString(1),
								result.getString(2), result.getDate(3),
								result.getString(4), result.getString(5),
								result.getString(6), result.getBytes(7));
					} catch (NumberFormatException e) {
						e.printStackTrace();
					}
				}
			} catch (SQLException e) {
			}
		}
		return vaca;
	}

	public String vacaString(String id_vaca, String id_usuario) {
		Gson json = new GsonBuilder().setPrettyPrinting()
				.setDateFormat("dd-MM-yyyy").create();
		Vaca v = getVaca(id_vaca, id_usuario);
		String vaca = json.toJson(v);
		return vaca;
	}

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

	public void añadirVaca(String vaca) {
		Gson json = new GsonBuilder().setPrettyPrinting()
				.setDateFormat("dd-MM-yyyy").create();
		String INSERT_RECORD = "INSERT INTO vaca(id_vaca,raza,fecha_nacimiento,id_madre,id_usuario,sexo,foto) VALUES(?,?,?,?,?,?,?)";
		Vaca v = json.fromJson(vaca, Vaca.class);
		OracleConection c = new OracleConection();
		c.Conectar();

		if (c.getConexion() != null) {
			try {
				File fichero = new File("C:\\Users\\sara\\Desktop\\vaca1.gif");
				FileInputStream streamEntrada = new FileInputStream(fichero);
				PreparedStatement pstmt = c.getConexion().prepareStatement(
						INSERT_RECORD);
				pstmt.setString(1, v.getId_vaca());
				pstmt.setString(2, v.getRaza());
				pstmt.setDate(3, v.getFecha_nacimiento());
				pstmt.setString(4, v.getId_madre());
				pstmt.setString(5, v.getId_usuario());
				pstmt.setString(6, v.getSexo());
				pstmt.setBlob(7, streamEntrada);
				pstmt.executeUpdate();

			} catch (SQLException | FileNotFoundException e) {
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

	public byte[] getFoto() {
		return foto;
	}

	public void setFoto(byte[] foto) {
		this.foto = foto;
	}

	public String getId_usuario() {
		return id_usuario;
	}

	public void setId_usuario(String id_usuario) {
		this.id_usuario = id_usuario;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

}
