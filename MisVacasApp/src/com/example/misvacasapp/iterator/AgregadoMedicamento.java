package com.example.misvacasapp.iterator;

import java.util.ArrayList;

import com.example.misvacasapp.modelo.Medicamento;

/**
 * Clase del agregado de los medicamentos
 * 
 * @author sara
 * 
 */
public class AgregadoMedicamento implements Agregado {

	// -------------------------------Atributos-----------------------------//
	/** Lista de medicamentos */
	ArrayList<Medicamento> listaMedicamentos;

	// -------------------------------Métodos--------------------------------//
	/**
	 * Constructor de la clase
	 * 
	 * @param listaMedicamentos
	 *            ArrayList. Lista de medicamentos
	 */
	public AgregadoMedicamento(ArrayList<Medicamento> listaMedicamentos) {
		this.listaMedicamentos = listaMedicamentos;
	}

	/**
	 * Método que crea el iterador de medicamentos
	 */
	@Override
	public Iterator createIterator() {
		return new IteratorListaMedicamento(this);
	}

	/**
	 * Método que devuelve un medicamento de la lista de medicamentos
	 * 
	 * @param item
	 *            int. Posición de la lista del medicamento que se quiere
	 * @return Medicamento. Medicamento a devolver
	 */
	@Override
	public Medicamento get(int item) {
		return this.listaMedicamentos.get(item);
	}

	/**
	 * Método que devuelve la cantidad de medicamentos que hay en la lista
	 * 
	 * @retur int. Número de medicamentos
	 */
	@Override
	public int count() {
		return this.listaMedicamentos.size();
	}
}
