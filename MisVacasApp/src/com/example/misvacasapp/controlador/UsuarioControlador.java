package com.example.misvacasapp.controlador;

import java.util.ArrayList;

import com.example.misvacasapp.controlador.modelo.iterator.AgregadoUsuario;
import com.example.misvacasapp.controlador.modelo.iterator.IteratorListaUsuario;
import com.example.misvacasapp.controlador.modelo.llamadaWS.LlamadaUsuarioWS;
import com.example.misvacasapp.modelo.Usuario;
import com.example.misvacasapp.modelo.Vaca;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class UsuarioControlador {

	private boolean resultado;
	private ArrayList<Usuario> lista;
	private Usuario usuario;

	public UsuarioControlador() {
		resultado = false;
		lista = new ArrayList<Usuario>();
		usuario = new Usuario();
	}

	public boolean UsuarioExistente(final String id_usuario,
			final String contraseña) {
		Thread hilo = new Thread() {
			String res = "";
			LlamadaUsuarioWS llamada = new LlamadaUsuarioWS();

			public void run() {
				res = llamada.LlamadaUsuarioExistente(id_usuario, contraseña);
				System.out.println("RES"+res);
				if (res.compareTo("true") == 0) {
					resultado = true;
				} else if (res.compareTo("false") == 0) {
					resultado = false;
				}// TODO COMPROBAR CUANDO NO HAY CONEXION
			}
		};
		hilo.start();
		return false;

	}

	public Usuario getUsuario(final String id_usuario, final String contraseña) {
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

	public Usuario getUsuario(String correo) {
		AgregadoUsuario agregado = new AgregadoUsuario();
		IteratorListaUsuario i = (IteratorListaUsuario) agregado
				.createIterator();
		while (i.hasNext()) {
			if (i.actualElement().getCorreo().equals(correo))
				usuario = i.actualElement();
			i.next();
		}
		return usuario;
	}

	public Usuario getUsuarioPorId(String id_usuario) {
		AgregadoUsuario agregado = new AgregadoUsuario();
		IteratorListaUsuario i = (IteratorListaUsuario) agregado
				.createIterator();
		while (i.hasNext()) {
			if (i.actualElement().getCorreo().equals(id_usuario))
				usuario = i.actualElement();
			i.next();
		}
		return usuario;
	}

	public ArrayList<Usuario> listaUsuarios() {
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

	public boolean correoExistente(String correo) {
		AgregadoUsuario agregado = new AgregadoUsuario();
		IteratorListaUsuario i = (IteratorListaUsuario) agregado
				.createIterator();
		while (i.hasNext()) {
			if (i.actualElement().getCorreo().equals(correo))
				resultado = true;
			i.next();
		}
		return resultado;
	}

	public void añadirUsuario(final Usuario nuevoUsuario) {
		Thread hilo = new Thread() {
			Gson json = new Gson();
			LlamadaUsuarioWS llamada = new LlamadaUsuarioWS();
			String usuario;

			public void run() {
				usuario = json.toJson(nuevoUsuario);
				llamada.añadirUsuario(usuario);
			}
		};
		hilo.start();
	}

	public void eliminarUsuario(final String id_usuario) {
		Thread hilo = new Thread() {
			LlamadaUsuarioWS llamada = new LlamadaUsuarioWS();

			public void run() {
				llamada.eliminarUsuario(id_usuario);
			}
		};
		hilo.start();
	}

	public void actualizarUsuario(final String id_usuario, final String nombre,
			final String apellido1, final String apellido2,
			final String direccion, final String poblacion, final int telefono) {
		Thread hilo = new Thread() {
			LlamadaUsuarioWS llamada = new LlamadaUsuarioWS();

			public void run() {
				llamada.actualizarUsuario(id_usuario, nombre, apellido1,
						apellido2, direccion, poblacion, telefono);
			}
		};
		hilo.start();
	}
	
	public void cambiarContraseña(final String id_usuario,final String nuevaContraseña){
		Thread hilo = new Thread() {
			LlamadaUsuarioWS llamada = new LlamadaUsuarioWS();

			public void run() {
				llamada.actualizarContraseña(id_usuario, nuevaContraseña);
			}
		};
		hilo.start();
		
	}
}
