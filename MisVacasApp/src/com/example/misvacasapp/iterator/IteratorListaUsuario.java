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
	/** Posición actual */
	private int actual;

	// ----------------------------Métodos---------------------------//
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
	 * Método que coloca el puntero en la primera posición de la lista
	 */
	@Override
	public void first() {
		actual = 0;
	}

	/**
	 * Método que coloca el puntero en la siguiente posición a la que esta
	 * apuntando actualmente
	 */
	@Override
	public void next() {
		actual++;
	}

	/**
	 * Método que devuelve si en la siguiente posición de la lista hay mas
	 * objetos o no
	 * 
	 * @return boolean. True si hay mas, false si no
	 */
	@Override
	public boolean hasNext() {
		return actual < agregado.count();
	}

	/**
	 * Método que devuelve el elemento al que esta apuntando el puntero
	 * 
	 * @return Usuario. Usuario a devolver
	 */
	@Override
	public Usuario actualElement() {
		return (Usuario) this.agregado.get(actual);
	}

}
