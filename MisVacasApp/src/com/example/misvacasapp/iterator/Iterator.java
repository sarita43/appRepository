package com.example.misvacasapp.iterator;

/**
 * Interfaz de los iteradores
 * 
 * @author Sara Mart�nez L�pez
 * 
 */
public interface Iterator {

	/** Coloca el puntero en la primera posici�n */
	public void first();

	/**
	 * Coloca el puntero en la siguiente posici�n a la que apuntaba
	 * anteriormente
	 */
	public void next();

	/** Devuelve si en la lista hay m�s objetos o no */
	public boolean hasNext();

	/** Devuelve el objeto al que esta apuntando el puntero */
	public Object actualElement();

}
