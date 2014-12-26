package web.service;

import basedatos.Usuario;

public class UsuarioWebService {

	public boolean usuarioExistente(String dni, String contraseña) {
		return new Usuario().usuarioExistente(dni, contraseña);
	}

	public String Usuario(String dni, String contraseña) {
		return new Usuario().usuarioString(dni, contraseña);
	}

	public void actualizarUsuario(String dni, String nombre, String apellido1,
			String apellido2, String direccion, String poblacion, int telefono) {
		new Usuario().actualizarUsuario(dni, nombre, apellido1, apellido2,
				direccion, poblacion, telefono);
	}

	public void actualizarContraseña(String dni, String contraseña) {
		new Usuario().actualizarContraseña(dni, contraseña);
	}

}
