package web.service;

import basedatos.Usuario;

public class UsuarioWebService {
	
	public boolean usuarioExistente(String dni,String contraseņa){
		return new Usuario().usuarioExistente(dni, contraseņa);
	}
	
	public String Usuario(String dni,String contraseņa){
		return new Usuario().usuarioString(dni, contraseņa);
	}
	
}
