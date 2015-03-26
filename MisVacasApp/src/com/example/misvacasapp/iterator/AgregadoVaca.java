package com.example.misvacasapp.iterator;

import java.util.ArrayList;

import com.example.misvacasapp.modelo.Vaca;

public class AgregadoVaca implements Agregado {
	
	ArrayList<Vaca> listaVacas;
	
	public AgregadoVaca(){
		listaVacas = new ArrayList<Vaca>();
	}

	@Override
	public Iterator createIterator() {
		return new IteratorListaVaca(this);
	}

	@Override
	public Vaca get(int item) {
		return this.listaVacas.get(item);
	}

	@Override
	public int count() {
		return this.listaVacas.size();
	}

}
