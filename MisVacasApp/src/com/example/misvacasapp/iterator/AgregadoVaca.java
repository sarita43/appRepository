package com.example.misvacasapp.iterator;

import java.util.ArrayList;

import com.example.misvacasapp.modelo.Vaca;

/**
 * Clase del agregado de los animales
 * 
 * @author Sara Mart�nez L�pez
 * 
 */
public class AgregadoVaca implements Agregado {

	// -------------------------------Atributos-----------------------------//
	/** Lista de animales */
	ArrayList<Vaca> listaVacas;

	// -------------------------------M�todos--------------------------------//
	/**
	 * Constructor de la clase
	 * 
	 * @param listaVacas
	 *            ArrayList. Lista de animales
	 */
	public AgregadoVaca(ArrayList<Vaca> listaVacas) {
		this.listaVacas = listaVacas;
	}

	/**
	 * M�todo que crea el iterador de animales
	 */
	@Override
	public Iterator createIterator() {
		return new IteratorListaVaca(this);
	}

	/**
	 * M�todo que devuelve un animal de la lista de animales
	 * 
	 * @param item
	 *            int. Posici�n de la lista del animale que se quiere
	 * @return Animal. Animal a devolver
	 */
	@Override
	public Vaca get(int item) {
		return this.listaVacas.get(item);
	}

	/**
	 * M�todo que devuelve la cantidad de animales que hay en la lista
	 * 
	 * @retur int. N�mero de animales
	 */
	@Override
	public int count() {
		return this.listaVacas.size();
	}

}
