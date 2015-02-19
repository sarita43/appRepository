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
	/** Direcci�n del usuario */
	private String direccion;
	/** Poblaci�n del usuario */
	private String poblacion;
	/** Tel�fono del usuario */
	private int telefono;
	/** Dni o id del usuario */
	private String dni;
	/** Contrase�a del usuario */
	private String contrase�a;
	/** Rol que tiene el usuario. Usuario o administrador */
	private int rol;

	// M�todos
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
	 *            Direcci�n del usuario
	 * @param poblacion
	 *            Poblaci�n del usuario
	 * @param telefono
	 *            Tel�fono del usuario
	 * @param dni
	 *            DNI o Id del usuario
	 * @param contrase�a
	 *            Contrase�a del usuario
	 * @param rol
	 *            Rol que tiene el usuario. Usuario o administrador
	 * */
	public Usuario(String nombre, String apellido1, String apellido2,
			String direccion, String poblacion, String telefono, String dni,
			String contrase�a, int rol) {
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
		setContrase�a(contrase�a);
		setRol(rol);
	}

	// public Usuario(String dni, String contrase�a, int rol) {
	// setDni(dni);
	// setContrase�a(contrase�a);
	// setRol(rol);
	// }
	/**
	 * Devuelve el arrayList de los Usuarios que tiene hay en la base de datos.
	 * Crea la conexi�n a la base de datos, llama a la base de datos recogiendo
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
	 * Devuelve un usuario la base de datos. Crea la conexi�n a la base de
	 * datos, llama a la base de datos recogiendo el usuario que se quiere y lo
	 * guarda en un nuevo usuario
	 * 
	 * @param dni
	 *            DNI del usuario o id del usuario
	 * @param contrase�a
	 *            Contrase�a del usuario
	 * @return Usuario Usuario encontrado en la base de datos
	 * */
	public Usuario getUsuario(String dni, String contrase�a) {
		OracleConection c = new OracleConection();
		c.Conectar();
		Usuario usuario = new Usuario();
		if (c.getConexion() != null) {
			try {
				Statement select = c.getConexion().createStatement();
				ResultSet result = select
						.executeQuery("SELECT * from usuario where dni='" + dni
								+ "'and contrase�a='" + contrase�a + "'");
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
	 * M�todo que devuelve el usuario que hay en la base de datos como String.
	 * Recoge el usuario de getUsuario y lo serializa con json.toJson.
	 * 
	 * @param dni
	 *            DNI del usuario o Id del usuario
	 * @param contrase�a
	 *            Contrase�a del usuario
	 * @return String Usuario como String
	 * */
	public String usuarioString(String dni, String contrase�a) {
		Usuario u = getUsuario(dni, contrase�a);
		Gson gson = new Gson();
		String usuario = gson.toJson(u);
		return usuario;
	}

	public boolean usuarioExistente(String dni, String contrase�a) {
		OracleConection c = new OracleConection();
		c.Conectar();
		boolean existe = false;
		if (c.getConexion() != null) {
			try {
				Statement select = c.getConexion().createStatement();
				ResultSet result = select
						.executeQuery("SELECT * from usuario where dni='" + dni
								+ "'and contrase�a='" + contrase�a + "'");
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

	public void actualizarContrase�a(String dni, String contrase�a) {
		OracleConection c = new OracleConection();
		c.Conectar();
		if (c.getConexion() != null) {
			try {
				Statement select = c.getConexion().createStatement();
				select.executeQuery("UPDATE usuario SET contrase�a ='"
						+ contrase�a + "' where dni='" + dni + "'");
			} catch (SQLException e) {
			}
		}
	}

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
	 * Devuelve la direcci�n del usuario
	 * 
	 * @return String Direcci�n del ususario
	 * */
	public String getDireccion() {
		return direccion;
	}

	/**
	 * Guarda la direcci�n del usuario
	 * 
	 * @param direccion
	 *            Direcci�n del usuario
	 * */
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	/**
	 * Devuelve la poblaci�n del usuario
	 * 
	 * @return String Poblacion del usuario
	 * */
	public String getPoblacion() {
		return poblacion;
	}

	/**
	 * Guarda la poblaci�n
	 * 
	 * @param poblaci�n
	 * */
	public void setPoblacion(String poblacion) {
		this.poblacion = poblacion;
	}

	/**
	 * Devuelve el tel�fono
	 * 
	 * @return int Tel�fono del usuario
	 * */
	public int getTelefono() {
		return telefono;
	}

	/**
	 * Guarda el tel�fono
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
	 * Devuelve la contrase�a del usuario
	 * 
	 * @return String Contrase�a del usuario
	 * */
	public String getContrase�a() {
		return contrase�a;
	}

	/**
	 * Guarda la contrase�a del usuario
	 * 
	 * @param contrase�a
	 *            Contrase�a del usuario
	 * */
	public void setContrase�a(String contrase�a) {
		this.contrase�a = contrase�a;
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