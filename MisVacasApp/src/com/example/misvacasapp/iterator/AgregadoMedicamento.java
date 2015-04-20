package com.example.misvacasapp.iterator;

import java.util.ArrayList;

import com.example.misvacasapp.modelo.Medicamento;

public class AgregadoMedicamento implements Agregado {
	
	ArrayList<Medicamento> listaMedicamentos;
	public AgregadoMedicamento(ArrayList<Medicamento> listaMedicamentos){
		this.listaMedicamentos = listaMedicamentos;
	}

	@Override
	public Iterator createIterator() {
		return new IteratorListaMedicamento(this);
	}

	@Override
	public Medicamento get(int item) {
		return this.listaMedicamentos.get(item);
	}

	@Override
	public int count() {
		return this.listaMedicamentos.size();
	}

}