package basedatos;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import com.google.gson.Gson;

/**
 * Clase usuario
 * 
 * @author Sara Martinez Lopez
 * */
public class Usuario {

	// Atributos
	/** Nombre del usuario */
	private String nombre;
	/** Primer apellido */
	private String apellido1;
	/** Segundo apellido */
	private String apellido2;
	/** Dirección del usuario */
	private String direccion;
	/** Población del usuario */
	private String poblacion;
	/** Teléfono del usuario */
	private int telefono;
	/** Dni o id del usuario */
	private String dni;
	/** Contraseña del usuario */
	private String contraseña;
	/** Rol que tiene el usuario. Usuario o administrador */
	private int rol;

	// Métodos
	/** Constructor del usuario sin atributos */
	public Usuario() {
	}

	/**
	 * Constructor del usuario con atributos
	 * 
	 * @param nombre
	 *            Nombre del usuario
	 * @param apellido1
	 *            Primer apellido del usuario
	 * @param apellido2
	 *            Segundo apellido del usuario
	 * @param direccion
	 *            Dirección del usuario
	 * @param poblacion
	 *            Población del usuario
	 * @param telefono
	 *            Teléfono del usuario
	 * @param dni
	 *            DNI o Id del usuario
	 * @param contraseña
	 *            Contraseña del usuario
	 * @param rol
	 *            Rol que tiene el usuario. Usuario o administrador
	 * */
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

	// public Usuario(String dni, String contraseña, int rol) {
	// setDni(dni);
	// setContraseña(contraseña);
	// setRol(rol);
	// }
	/**
	 * Devuelve el arrayList de los Usuarios que tiene hay en la base de datos.
	 * Crea la conexión a la base de datos, llama a la base de datos recogiendo
	 * todos los usuarios de ella y los guarda en un arrayList lista que
	 * contiene ususarios.
	 * 
	 * @return ArrayList<EUsuario> Lista de de usuarios
	 * */
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
						e.printStackTrace();
					}
					lista.add(usuario);
				}
			} catch (SQLException e) {
			}
		}
		return lista;
	}

	/**
	 * Devuelve un usuario la base de datos. Crea la conexión a la base de
	 * datos, llama a la base de datos recogiendo el usuario que se quiere y lo
	 * guarda en un nuevo usuario
	 * 
	 * @param dni
	 *            DNI del usuario o id del usuario
	 * @param contraseña
	 *            Contraseña del usuario
	 * @return Usuario Usuario encontrado en la base de datos
	 * */
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
						e.printStackTrace();
					}
				}
			} catch (SQLException e) {
			}
		}
		return usuario;
	}

	/**
	 * Método que devuelve el usuario que hay en la base de datos como String.
	 * Recoge el usuario de getUsuario y lo serializa con json.toJson.
	 * 
	 * @param dni
	 *            DNI del usuario o Id del usuario
	 * @param contraseña
	 *            Contraseña del usuario
	 * @return String Usuario como String
	 * */
	public String usuarioString(String dni, String contraseña) {
		Usuario u = getUsuario(dni, contraseña);
		Gson gson = new Gson();
		String usuario = gson.toJson(u);
		return usuario;
	}

	/**
	 * Método que busca en la base de datos el usuario y comprueba que coincidan usuario y contraseña. Si existe devuelve 
	 * un true y si no un false
	 * @param dni DNI o id del usuario
	 * @param contraseña Contraseña del usuario
	 * @return boolean Existe o no el usuario
	 * */
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
						e.printStackTrace();
					}
				}
			} catch (SQLException e) {
			}
		}
		return existe;
	}

	/**
	 * Actualiza los parámetros del usuario cuyo id, pasado por parámetro, exista en la base de datos.
	 * Hace la conexión con la base de datos, busca el usuario con ese id y cambia los parámetros.
	 * @param dni DNI o id del ususario
	 * @param nombre Nombre del usuario
	 * @param apellido1 Primer apellido
	 * @param apellido2 Segundo apellido
	 * @param direccion Dirección del usuario
	 * @param poblacion Población del usuario
	 * @param telefono Teléfono del usuario
	 * */
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

	/**
	 * Cambia la contraseña del usuario. Para ello tiene que hacer la conexión con la base de datos, buscar al usuario
	 * y cambiar la contraseña
	 * @param dni DNI o Id del usuario
	 * @param contraseña Contraseña del usuario
	 * */
	public void actualizarContraseña(String dni, String contraseña) {
		OracleConection c = new OracleConection();
		c.Conectar();
		if (c.getConexion() != null) {
			try {
				Statement select = c.getConexion().createStatement();
				select.executeQuery("UPDATE usuario SET contraseña ='"
						+ contraseña + "' where dni='" + dni + "'");
			} catch (SQLException e) {
			}
		}
	}

	/**
	 * Elimina el usuario de la base de datos. Hace la conexión con la base de datos y elimina el usuario
	 * @param id_usuario Id del usuario
	 * */
	public void eliminarUsuario(String id_usuario) {
		OracleConection c = new OracleConection();
		c.Conectar();
		if (c.getConexion() != null) {
			try {
				Statement select = c.getConexion().createStatement();
				select.executeQuery("DELETE usuario where dni='" + id_usuario
						+ "'");
			} catch (SQLException e) {
			}
		}
	}

	/**
	 * Devuelve el nombre del usuario
	 * 
	 * @return String nombre
	 * */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Guarda el nombre del usuario
	 * 
	 * @param nombre
	 *            Nombre del usuario
	 * */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * Devuelve el primer apellido
	 * 
	 * @return String Primer apellido
	 * */
	public String getApellido1() {
		return apellido1;
	}

	/**
	 * Guarda el primero apellido
	 * 
	 * @param apellido1
	 *            Primer apellido
	 * */
	public void setApellido1(String apellido1) {
		this.apellido1 = apellido1;
	}

	/**
	 * Devuelve el segundo apellido
	 * 
	 * @param String
	 *            Segundo apellido
	 * */
	public String getApellido2() {
		return apellido2;
	}

	/**
	 * Guarda el segundo apellido
	 * 
	 * @param apellido2
	 *            Segundo apellido
	 * */
	public void setApellido2(String apellido2) {
		this.apellido2 = apellido2;
	}

	/**
	 * Devuelve la dirección del usuario
	 * 
	 * @return String Dirección del ususario
	 * */
	public String getDireccion() {
		return direccion;
	}

	/**
	 * Guarda la dirección del usuario
	 * 
	 * @param direccion
	 *            Dirección del usuario
	 * */
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	/**
	 * Devuelve la población del usuario
	 * 
	 * @return String Poblacion del usuario
	 * */
	public String getPoblacion() {
		return poblacion;
	}

	/**
	 * Guarda la población
	 * 
	 * @param población
	 * */
	public void setPoblacion(String poblacion) {
		this.poblacion = poblacion;
	}

	/**
	 * Devuelve el teléfono
	 * 
	 * @return int Teléfono del usuario
	 * */
	public int getTelefono() {
		return telefono;
	}

	/**
	 * Guarda el teléfono
	 * 
	 * @param telefono
	 *            Telefono del usuario
	 * */
	public void setTelefono(int telefono) {
		this.telefono = telefono;
	}

	/**
	 * Devuelve el dni o id del usuario
	 * 
	 * @return String Dni o id del usuario
	 * */
	public String getDni() {
		return dni;
	}

	/**
	 * Guarda el dni o id del usuario
	 * 
	 * @param dni
	 *            Dni o Id del usuario
	 * */
	public void setDni(String dni) {
		this.dni = dni;
	}

	/**
	 * Devuelve la contraseña del usuario
	 * 
	 * @return String Contraseña del usuario
	 * */
	public String getContraseña() {
		return contraseña;
	}

	/**
	 * Guarda la contraseña del usuario
	 * 
	 * @param contraseña
	 *            Contraseña del usuario
	 * */
	public void setContraseña(String contraseña) {
		this.contraseña = contraseña;
	}

	/**
	 * Devuelve el rol del usuario
	 * 
	 * @return int Rol del usuario
	 * */
	public int getRol() {
		return rol;
	}

	/**
	 * Guarda el rol del usuario
	 * 
	 * @param rol
	 *            Rol del usuario
	 * */
	public void setRol(int rol) {
		this.rol = rol;
	}
}
