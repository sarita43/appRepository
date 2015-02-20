package web.service;

import basedatos.Usuario;

public class UsuarioWebService {

	/**
	 * Método que si existe el usuario devuelve 
	 * un true y si no un false
	 * @param dni DNI o id del usuario
	 * @param contraseña Contraseña del usuario
	 * @return boolean Existe o no el usuario
	 * */
	public boolean usuarioExistente(String dni, String contraseña) {
		return new Usuario().usuarioExistente(dni, contraseña);
	}

	/**
	 * Método que devuelve el usuario como String. 
	 * @param dni
	 *            DNI del usuario o Id del usuario
	 * @param contraseña
	 *            Contraseña del usuario
	 * @return String Usuario como String
	 * */
	public String Usuario(String dni, String contraseña) {
		return new Usuario().usuarioString(dni, contraseña);
	}

	/**
	 * Actualiza los parámetros del usuario cuyo id se pasa por parámetro.
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
		new Usuario().actualizarUsuario(dni, nombre, apellido1, apellido2,
				direccion, poblacion, telefono);
	}

	/**
	 * Cambia la contraseña del usuario.
	 * @param dni DNI o Id del usuario
	 * @param contraseña Contraseña del usuario
	 * */
	public void actualizarContraseña(String dni, String contraseña) {
		new Usuario().actualizarContraseña(dni, contraseña);
	}

	/**
	 * Elimina el usuario.
	 * @param id_usuario Id del usuario
	 * */
	public void eliminarUsuario(String id_usuario){
		new Usuario().eliminarUsuario(id_usuario);
	}
}
