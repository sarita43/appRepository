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
		//TODO Si es el primer elemento en un while no funciona
		if(actual ==0 && agregado.count()==1){
			return true;
		}else if(actual==agregado.count()-1){
			return false;
		}else{
			return actual<agregado.count()-1;
		}
	}

	@Override
	public Medicamento actualElement() {
		return (Medicamento)this.agregado.get(actual);
	}

}
