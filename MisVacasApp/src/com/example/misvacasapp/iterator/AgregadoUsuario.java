package com.example.misvacasapp.iterator;

import java.util.ArrayList;

import com.example.misvacasapp.modelo.Usuario;

/**
 * Clase del agregado de los usuarios
 * 
 * @author Sara Mart�nez L�pez
 * 
 */
public class AgregadoUsuario implements Agregado {

	// -------------------------------Atributos-----------------------------//
	/** Lista de usuarios */
	private ArrayList<Usuario> listaUsuarios;

	// -------------------------------M�todos--------------------------------//
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
	 * M�todo que crea el iterador de usuarios
	 */
	@Override
	public Iterator createIterator() {
		return new IteratorListaUsuario(this);
	}

	/**
	 * M�todo que devuelve un usuario de la lista de usuarios
	 * 
	 * @param item
	 *            int. Posici�n de la lista del usuario que se quiere
	 * @return Usuario. Usuario a devolver
	 */
	@Override
	public Usuario get(int item) {
		return this.listaUsuarios.get(item);
	}

	/**
	 * M�todo que devuelve la cantidad de usuarios que hay en la lista
	 * 
	 * @retur int. N�mero de usuarios
	 */
	@Override
	public int count() {
		return this.listaUsuarios.size();
	}

}
