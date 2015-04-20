package com.example.misvacasapp.iterator;

import com.example.misvacasapp.modelo.Vaca;

public class IteratorListaVaca implements Iterator {

	private Agregado agregado;
	private int actual;

	public IteratorListaVaca(AgregadoVaca agregado) {
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
		return actual < agregado.count();
	}

	@Override
	public Vaca actualElement() {
		return (Vaca) this.agregado.get(actual);
	}

}
