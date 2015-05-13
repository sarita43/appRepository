package com.example.misvacasapp.iterator;

/**
 * Intefaz del agregado
 * 
 * @author sara
 * 
 */
public interface Agregado {

	/** Crea el iterador */
	public Iterator createIterator();

	/** Devuelve un objeto */
	public Object get(int item);

	/** Devuelve el numero de objetos que hay */
	public int count();
}
