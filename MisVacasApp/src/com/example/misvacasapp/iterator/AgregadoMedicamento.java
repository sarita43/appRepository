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

	// -------------------------------M�todos--------------------------------//
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
	 * M�todo que crea el iterador de medicamentos
	 */
	@Override
	public Iterator createIterator() {
		return new IteratorListaMedicamento(this);
	}

	/**
	 * M�todo que devuelve un medicamento de la lista de medicamentos
	 * 
	 * @param item
	 *            int. Posici�n de la lista del medicamento que se quiere
	 * @return Medicamento. Medicamento a devolver
	 */
	@Override
	public Medicamento get(int item) {
		return this.listaMedicamentos.get(item);
	}

	/**
	 * M�todo que devuelve la cantidad de medicamentos que hay en la lista
	 * 
	 * @retur int. N�mero de medicamentos
	 */
	@Override
	public int count() {
		return this.listaMedicamentos.size();
	}
}
