package com.example.misvacasapp.singleton;

import java.util.Hashtable;

/**
 * Clase que crea una tabla hash para saber que elementos han sido seleccionados
 * en la lista
 * 
 * @author Sara Martinez Lopez
 * */
public class TableSeleccionado {
	// ---------------------------Atributos-----------------------------//
	/** Tabla hash de seleccionados */
	private Hashtable<Integer, Boolean> seleccionado;

	// private static TableSeleccionado _uniqueInstance;
	// ---------------------------Métodos-------------------------------//
	/**
	 * Constructor Crea una nueva tabla hash vacia que introduce en el primer
	 * valor un entero (el indice en la lista) y el segundo valor un boolean, si
	 * esta seleccionado en la vista de la lista se pone un true y si no un
	 * false. Al principio todos los valores de la lsta serán false.
	 * */
	public TableSeleccionado() {
		this.seleccionado = new Hashtable<Integer, Boolean>();
	}

	// public static TableSeleccionado getInstance() {
	// if (_uniqueInstance == null)
	// _uniqueInstance = new TableSeleccionado();
	// return _uniqueInstance;
	// }
	/**
	 * Devuelve la tabla hash de seleccionados
	 * 
	 * @return HashTable<Integer,Boolean> Tabla hash de seleccionados
	 * */
	public Hashtable<Integer, Boolean> getTable() {
		return seleccionado;
	}
}