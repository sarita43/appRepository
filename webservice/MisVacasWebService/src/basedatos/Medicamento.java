package basedatos;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.google.gson.Gson;

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

		Medicamento medicamento = new Medicamento();
		SimpleDateFormat formatoDeFecha = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss.S");
		if (c.getConexion() != null) {
			try {
				Statement select = c.getConexion().createStatement();
				ResultSet result = select
						.executeQuery("SELECT * from medicamento where id_vaca='"
								+ id_vaca + "'");

				while (result.next()) {
					try {
						medicamento = new Medicamento(Integer.parseInt(result
								.getString(1)), formatoDeFecha.parse(result
								.getString(2)), result.getString(3),
								result.getString(4), result.getString(5));
					} catch (NumberFormatException | ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					lista.add(medicamento);

				}
			} catch (SQLException e) {
			}
		}
		return lista;
	}
	
	public Medicamento getMedicamento(String id_vaca,String id_medicamento) {
		OracleConection c = new OracleConection();
		Medicamento medicamento= new Medicamento();
		c.Conectar();

		SimpleDateFormat formatoDeFecha = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss.S");
		if (c.getConexion() != null) {
			try {
				Statement select = c.getConexion().createStatement();
				ResultSet result = select
						.executeQuery("SELECT * from medicamento where id_vaca='"
								+ id_vaca + "' and id_medicamento='"+id_medicamento+"'");

				while (result.next()) {
					try {
						medicamento = new Medicamento(Integer.parseInt(result
								.getString(1)), formatoDeFecha.parse(result
								.getString(2)), result.getString(3),
								result.getString(4), result.getString(5));
					} catch (NumberFormatException | ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			} catch (SQLException e) {
			}
		}
		return medicamento;
	}

	public String listaMedicamentoString(String id_vaca) {
		ArrayList<Medicamento> lista = listaMedicamento(id_vaca);
		Gson json = new Gson();
		String listaMedicamentos = json.toJson(lista);
		return listaMedicamentos;
	}
	
	public String medicamentoString(String id_vaca,String id_medicamento) {
		Medicamento me = getMedicamento(id_vaca, id_medicamento);
		Gson json = new Gson();
		String medicamento = json.toJson(me);
		return medicamento;
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
