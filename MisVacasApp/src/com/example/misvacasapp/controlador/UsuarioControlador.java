package com.example.misvacasapp.controlador;

import java.util.ArrayList;


import com.example.misvacasapp.controlador.modelo.llamadaWS.LlamadaUsuarioWS;
import com.example.misvacasapp.modelo.Usuario;
import com.example.misvacasapp.modelo.Vaca;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class UsuarioControlador {

	private boolean resultado;
	private ArrayList<Usuario> lista;
	private Usuario usuario;
	
	public UsuarioControlador(){
		resultado = false;
		lista = new ArrayList<Usuario>();
		usuario = new Usuario();
	}
	
	public boolean UsuarioExistente(final String id_usuario,final String contraseña){
		Thread hilo = new Thread() {
			String res = "";
			LlamadaUsuarioWS llamada = new LlamadaUsuarioWS();

			public void run() {
				res = llamada.LlamadaUsuarioExistente(id_usuario, contraseña);
				if (res.compareTo("true") == 0) {
					resultado = true;
				} else if (res.compareTo("false") == 0) {
					resultado = false;
				}//TODO COMPROBAR CUANDO NO HAY CONEXION
			}
		};
		hilo.start();
		return false;
		
	}
	
	public Usuario getUsuario(final String id_usuario,final String contraseña){
		Thread hilo = new Thread() {
			String res = "";
			Gson json = new Gson();
			LlamadaUsuarioWS llamada = new LlamadaUsuarioWS();

			public void run() {
				res = llamada.LlamadaUsuario(id_usuario, contraseña);
				usuario = json.fromJson(res, Usuario.class);
			}
		};
		hilo.start();
		return usuario;
	}
	
	public ArrayList<Usuario> listaUsuarios(){
		Thread hilo = new Thread() {
			String res = "";
			Gson json = new Gson();
			LlamadaUsuarioWS llamada = new LlamadaUsuarioWS();

			public void run() {
				res = llamada.LlamadaListaUsuarios();
				lista = json.fromJson(res, new TypeToken<ArrayList<Vaca>>() {
				}.getType());
			}
		};
		hilo.start();
		return lista;
	}
	
	public boolean correoExistente(String correo){
		//TODO CON EL ITERATOR
		return resultado;
	}
}
