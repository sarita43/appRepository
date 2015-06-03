package com.example.misvacasapp.iterator;

import java.util.ArrayList;

import com.example.misvacasapp.modelo.Usuario;

/**
 * Clase del agregado de los usuarios
 * 
 * @author Sara Martínez López
 * 
 */
public class AgregadoUsuario implements Agregado {

	// -------------------------------Atributos-----------------------------//
	/** Lista de usuarios */
	private ArrayList<Usuario> listaUsuarios;

	// -------------------------------Métodos--------------------------------//
	/**
	 * Constructor de la clase
	 * 
	 * @param listaUsuarios
	 *            ArrayList. Lista de usuarios
	 */
	public AgregadoUsuario(ArrayList<Usuario> listaUsuarios) {
		this.listaUsuarios = listaUsuarios;
	}

	/**
	 * Método que crea el iterador de usuarios
	 */
	@Override
	public Iterator createIterator() {
		return new IteratorListaUsuario(this);
	}

	/**
	 * Método que devuelve un usuario de la lista de usuarios
	 * 
	 * @param item
	 *            int. Posición de la lista del usuario que se quiere
	 * @return Usuario. Usuario a devolver
	 */
	@Override
	public Usuario get(int item) {
		return this.listaUsuarios.get(item);
	}

	/**
	 * Método que devuelve la cantidad de usuarios que hay en la lista
	 * 
	 * @retur int. Número de usuarios
	 */
	@Override
	public int count() {
		return this.listaUsuarios.size();
	}

}
