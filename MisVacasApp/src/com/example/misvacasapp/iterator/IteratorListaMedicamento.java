package com.example.misvacasapp.iterator;

import com.example.misvacasapp.modelo.Medicamento;

public class IteratorListaMedicamento implements Iterator {

	private Agregado agregado;
	private int actual;
	
	public IteratorListaMedicamento(AgregadoMedicamento agregado){
		this.agregado = agregado;
		first();
	}
	
	@Override
	public void first() {
		actual = 0;
	}

	@Override
	public void next() {
		actual++;
	}

	@Override
	public boolean hasNext() {
			return actual<agregado.count();
	}

	@Override
	public Medicamento actualElement() {
		return (Medicamento)this.agregado.get(actual);
	}

}
