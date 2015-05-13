package com.example.misvacasapp.iterator;

import com.example.misvacasapp.modelo.Usuario;

/**
 * Clase iterador de usuarios
 * 
 * @author sara
 * 
 */
public class IteratorListaUsuario implements Iterator {

	// ----------------------------Atributos--------------------------//
	/** Agregado */
	private Agregado agregado;
	/** Posici�n actual */
	private int actual;

	// ----------------------------M�todos---------------------------//
	/**
	 * Constructor de la clase
	 * 
	 * @param agregado
	 *            AgregadoUsuario
	 */
	public IteratorListaUsuario(AgregadoUsuario agregado) {
		this.agregado = agregado;
		first();
	}

	/**
	 * M�todo que coloca el puntero en la primera posici�n de la lista
	 */
	@Override
	public void first() {
		actual = 0;
	}

	/**
	 * M�todo que coloca el puntero en la siguiente posici�n a la que esta
	 * apuntando actualmente
	 */
	@Override
	public void next() {
		actual++;
	}

	/**
	 * M�todo que devuelve si en la siguiente posici�n de la lista hay mas
	 * objetos o no
	 * 
	 * @return boolean. True si hay mas, false si no
	 */
	@Override
	public boolean hasNext() {
		return actual < agregado.count();
	}

	/**
	 * M�todo que devuelve el elemento al que esta apuntando el puntero
	 * 
	 * @return Usuario. Usuario a devolver
	 */
	@Override
	public Usuario actualElement() {
		return (Usuario) this.agregado.get(actual);
	}

}
