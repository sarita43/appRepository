package com.example.misvacasapp;

import java.util.Hashtable;

public class TableSeleccionado {

	private Hashtable<Integer, Boolean> seleccionado;
	private static TableSeleccionado _uniqueInstance;

	public TableSeleccionado() {
		this.seleccionado = new Hashtable<Integer, Boolean>();
	}

	public static TableSeleccionado getInstance() {
		if (_uniqueInstance == null)
			_uniqueInstance = new TableSeleccionado();
		return _uniqueInstance;
	}
	
	public Hashtable<Integer, Boolean> getTable(){
		return seleccionado;
	}
}
