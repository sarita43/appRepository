package basedatos;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.google.gson.Gson;

public class Usuario {

	private String nombre;
	private String apellido1;
	private String apellido2;
	private String direccion;
	private String poblacion;
	private int telefono;
	private String dni;
	private String contraseña;
	private int rol;

	public Usuario() {
	}

	public Usuario(String nombre, String apellido1, String apellido2,
			String direccion, String poblacion, String telefono, String dni,
			String contraseña, int rol) {
		setNombre(nombre);
		setApellido1(apellido1);
		setApellido2(apellido2);
		setDireccion(direccion);
		setPoblacion(poblacion);
		if (telefono != null) {
			setTelefono(Integer.parseInt(telefono));
		} else {
			setTelefono(0);
		}
		setDni(dni);
		setContraseña(contraseña);
		setRol(rol);
	}

	public Usuario(String dni, String contraseña, int rol) {
		setDni(dni);
		setContraseña(contraseña);
		setRol(rol);
	}

	public ArrayList<Usuario> listaUsuarios() {
		OracleConection c = new OracleConection();
		ArrayList<Usuario> lista = new ArrayList<Usuario>();
		c.Conectar();

		Usuario usuario = new Usuario();

		if (c.getConexion() != null) {
			try {
				Statement select = c.getConexion().createStatement();
				ResultSet result = select.executeQuery("SELECT * from usuario");

				while (result.next()) {
					try {
						usuario = new Usuario(result.getString(1),
								result.getString(2), result.getString(3),
								result.getString(4), result.getString(5),
								result.getString(6), result.getString(7),
								result.getString(8), Integer.parseInt(result
										.getString(9)));
					} catch (NumberFormatException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					lista.add(usuario);

				}
			} catch (SQLException e) {
			}
		}
		return lista;
	}

	public Usuario getUsuario(String dni, String contraseña) {
		OracleConection c = new OracleConection();
		c.Conectar();
		Usuario usuario = new Usuario();

		if (c.getConexion() != null) {
			try {
				Statement select = c.getConexion().createStatement();
				ResultSet result = select
						.executeQuery("SELECT * from usuario where dni='" + dni
								+ "'and contraseña='" + contraseña + "'");

				while (result.next()) {
					try {
						usuario = new Usuario(result.getString(1),
								result.getString(2), result.getString(3),
								result.getString(4), result.getString(5),
								result.getString(6), result.getString(7),
								result.getString(8), Integer.parseInt(result
										.getString(9)));
					} catch (NumberFormatException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			} catch (SQLException e) {
			}
		}
		return usuario;
	}

	public String usuarioString(String dni, String contraseña) {
		Usuario u = getUsuario(dni, contraseña);
		Gson gson = new Gson();
		String usuario = gson.toJson(u);
		return usuario;
	}

	public boolean usuarioExistente(String dni, String contraseña) {
		OracleConection c = new OracleConection();
		c.Conectar();
		boolean existe = false;

		if (c.getConexion() != null) {
			try {
				Statement select = c.getConexion().createStatement();
				ResultSet result = select
						.executeQuery("SELECT * from usuario where dni='" + dni
								+ "'and contraseña='" + contraseña + "'");

				while (result.next()) {
					try {
						existe = true;
					} catch (NumberFormatException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			} catch (SQLException e) {
			}
		}
		return existe;
	}

	public void actualizarUsuario(String dni, String nombre, String apellido1,
			String apellido2, String direccion, String poblacion, int telefono) {
		OracleConection c = new OracleConection();
		c.Conectar();

		if (c.getConexion() != null) {
			try {
				Statement select = c.getConexion().createStatement();
				ResultSet result = select
						.executeQuery("UPDATE usuario SET nombre ='" + nombre
								+ "',apellido1 ='" + apellido1
								+ "',apellido2 ='" + apellido2
								+ "',direccion ='" + direccion
								+ "',poblacion ='" + poblacion
								+ "',telefono ='" + telefono + "'where dni='"
								+ dni + "'");
				while (result.next()) {

				}

			} catch (SQLException e) {
			}
		}
	}

	public void actualizarContraseña(String dni, String contraseña) {
		OracleConection c = new OracleConection();
		c.Conectar();

		if (c.getConexion() != null) {
			try {
				Statement select = c.getConexion().createStatement();
				ResultSet result = select
						.executeQuery("UPDATE usuario SET contraseña ='"
								+ contraseña + "' where dni='" + dni + "'");
				while (result.next()) {

				}

			} catch (SQLException e) {
			}
		}
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido1() {
		return apellido1;
	}

	public void setApellido1(String apellido1) {
		this.apellido1 = apellido1;
	}

	public String getApellido2() {
		return apellido2;
	}

	public void setApellido2(String apellido2) {
		this.apellido2 = apellido2;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getPoblacion() {
		return poblacion;
	}

	public void setPoblacion(String poblacion) {
		this.poblacion = poblacion;
	}

	public int getTelefono() {
		return telefono;
	}

	public void setTelefono(int telefono) {
		this.telefono = telefono;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getContraseña() {
		return contraseña;
	}

	public void setContraseña(String contraseña) {
		this.contraseña = contraseña;
	}

	public int getRol() {
		return rol;
	}

	public void setRol(int rol) {
		this.rol = rol;
	}
}
