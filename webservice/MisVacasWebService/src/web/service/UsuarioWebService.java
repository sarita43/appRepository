package web.service;

import basedatos.Usuario;

public class UsuarioWebService {
	/**
	 * M�todo que si existe el usuario devuelve un true y si no un false
	 * 
	 * @param dni
	 *            DNI o id del usuario
	 * @param contrase�a
	 *            Contrase�a del usuario
	 * @return boolean Existe o no el usuario
	 * */
	public boolean usuarioExistente(String dni, String contrase�a) {
		return new Usuario().usuarioExistente(dni, contrase�a);
	}

	/**
	 * M�todo que devuelve el usuario como String.
	 * 
	 * @param dni
	 *            DNI del usuario o Id del usuario
	 * @param contrase�a
	 *            Contrase�a del usuario
	 * @return String Usuario como String
	 * */
	public String Usuario(String dni, String contrase�a) {
		return new Usuario().usuarioString(dni, contrase�a);
	}

	/**
	 * Actualiza los par�metros del usuario cuyo id se pasa por par�metro.
	 * 
	 * @param dni
	 *            DNI o id del ususario
	 * @param nombre
	 *            Nombre del usuario
	 * @param apellido1
	 *            Primer apellido
	 * @param apellido2
	 *            Segundo apellido
	 * @param direccion
	 *            Direcci�n del usuario
	 * @param poblacion
	 *            Poblaci�n del usuario
	 * @param telefono
	 *            Tel�fono del usuario
	 * */
	public void actualizarUsuario(String dni, String nombre, String apellido1,
			String apellido2, String direccion, String poblacion, int telefono,
			String correo, String codigo_explotacion) {
		new Usuario().actualizarUsuario(dni, nombre, apellido1, apellido2,
				direccion, poblacion, telefono, correo, codigo_explotacion);
	}

	/**
	 * Cambia la contrase�a del usuario.
	 * 
	 * @param dni
	 *            DNI o Id del usuario
	 * @param contrase�a
	 *            Contrase�a del usuario
	 * */
	public void actualizarContrase�a(String dni, String contrase�a) {
		new Usuario().actualizarContrase�a(dni, contrase�a);
	}

	/**
	 * Elimina el usuario.
	 * 
	 * @param id_usuario
	 *            Id del usuario
	 * */
	public void eliminarUsuario(String id_usuario) {
		new Usuario().eliminarUsuario(id_usuario);
	}

	/**
	 * M�todo que devuelve la lista de usuarios como String
	 * 
	 * @return String Lista de usuarios
	 */
	public String listaUsuarios() {
		return new Usuario().listaUsuariosString();
	}

	/**
	 * M�todo que a�ade un nuevo usuario
	 * 
	 * @param usuario
	 *            Usuario como String
	 */
	public void a�adirUsuario(String usuario) {
		new Usuario().a�adirUsuario(usuario);
	}
	
	public void recordarUsuario(String correo){
		new Usuario().recordarContrase�a(correo);
	}
}