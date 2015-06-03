package com.example.misvacasapp.iterator;

/**
 * Interfaz de los iteradores
 * 
 * @author Sara Martínez López
 * 
 */
public interface Iterator {

	/** Coloca el puntero en la primera posición */
	public void first();

	/**
	 * Coloca el puntero en la siguiente posición a la que apuntaba
	 * anteriormente
	 */
	public void next();

	/** Devuelve si en la lista hay más objetos o no */
	public boolean hasNext();

	/** Devuelve el objeto al que esta apuntando el puntero */
	public Object actualElement();

}
