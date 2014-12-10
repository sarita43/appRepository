package web.service;

import basedatos.Usuario;

public class UsuarioWebService {
	
	public boolean usuarioExistente(String dni,String contraseña){
		return new Usuario().usuarioExistente(dni, contraseña);
	}
	
	public String Usuario(String dni,String contraseña){
		return new Usuario().usuarioString(dni, contraseña);
	}
	
}
