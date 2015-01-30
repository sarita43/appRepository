package basedatos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.Date;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

//Clase en la que se describe el medicamento

public class Medicamento {

	private int id_medicamento;
	private Date fecha;
	private String tipo;
	private String descripcion;
	private String id_vaca;

	public Medicamento() {

	}

	public Medicamento(int id_medicamento, Date fecha, String tipo,
			String descripcion, String id_vaca) {
		setId_medicamento(id_medicamento);
		setFecha(fecha);
		setTipo(tipo);
		setDescripcion(descripcion);
		setId_vaca(id_vaca);
	}

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

	public String listaMedicamentoString(String id_vaca) {
		Gson json = new GsonBuilder().setPrettyPrinting().setDateFormat("dd-MM-yyyy").create();
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

	public String medicamentoString(String id_vaca, String id_medicamento) {
		Gson json = new GsonBuilder().setPrettyPrinting().setDateFormat("dd-MM-yyyy").create();
		Medicamento me = getMedicamento(id_vaca, id_medicamento);
		String medicamento = json.toJson(me);
		return medicamento;
	}
	
	public void eliminarMedicamento(String id_medicamento, String id_vaca) {
		OracleConection c = new OracleConection();
		c.Conectar();

		if (c.getConexion() != null) {
			try {
				Statement select = c.getConexion().createStatement();
				select.executeQuery("DELETE FROM medicamento WHERE id_medicamento='"
						+ id_medicamento + "' AND id_vaca='" + id_vaca + "'");

			} catch (SQLException e) {
			}
		}
	}

	public void añadirMedicamento(String medicamento) {
		Gson json = new GsonBuilder().setPrettyPrinting().setDateFormat("dd-MM-yyyy").create();
		String INSERT_RECORD = "INSERT INTO medicamento(id_medicamento, fecha, tipo,descripcion,id_vaca) VALUES(?,?,?,?,?)";
		Medicamento m = json.fromJson(medicamento, Medicamento.class);
		OracleConection c = new OracleConection();
		c.Conectar();

		if (c.getConexion() != null) {
			try {
				PreparedStatement pstmt = c.getConexion().prepareStatement(INSERT_RECORD);
				 pstmt.setInt(1, m.getId_medicamento());
				 pstmt.setDate(2,m.getFecha() );
				 pstmt.setString(3, m.getTipo());
				 pstmt.setString(4, m.getDescripcion());
				 pstmt.setString(5, m.getId_vaca());
				 pstmt.executeUpdate();

			} catch (SQLException e) {
			}
		}
	}
	
	public int getId_medicamento() {
		return id_medicamento;
	}

	public void setId_medicamento(int id_medicamento) {
		this.id_medicamento = id_medicamento;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getId_vaca() {
		return id_vaca;
	}

	public void setId_vaca(String id_vaca) {
		this.id_vaca = id_vaca;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
}
