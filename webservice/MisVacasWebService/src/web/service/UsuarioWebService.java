package web.service;

import basedatos.Usuario;

public class UsuarioWebService {

	public boolean usuarioExistente(String dni, String contrase�a) {
		return new Usuario().usuarioExistente(dni, contrase�a);
	}

	public String Usuario(String dni, String contrase�a) {
		return new Usuario().usuarioString(dni, contrase�a);
	}

	public void actualizarUsuario(String dni, String nombre, String apellido1,
			String apellido2, String direccion, String poblacion, int telefono) {
		new Usuario().actualizarUsuario(dni, nombre, apellido1, apellido2,
				direccion, poblacion, telefono);
	}

	public void actualizarContrase�a(String dni, String contrase�a) {
		new Usuario().actualizarContrase�a(dni, contrase�a);
	}

}
