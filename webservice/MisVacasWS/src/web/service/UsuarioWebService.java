package web.service;

import basedatos.Usuario;

public class UsuarioWebService {
	
	public boolean usuarioExistente(String dni,String contrase�a){
		return new Usuario().usuarioExistente(dni, contrase�a);
	}
	
	public String Usuario(String dni,String contrase�a){
		return new Usuario().usuarioString(dni, contrase�a);
	}
	
}
