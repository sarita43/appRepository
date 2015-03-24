package com.example.misvacasapp.controlador.modelo.iterator;

import com.example.misvacasapp.modelo.Usuario;

public class IteratorListaUsuario implements Iterator {
	
	private Agregado agregado;
	private int actual;
	
	public IteratorListaUsuario(AgregadoUsuario agregado){
		this.agregado = agregado;
		first();
	}

	@Override
	public void first() {
		this.actual = 0;		
	}

	@Override
	public void next() {
		actual++;
	}

	@Override
	public boolean hasNext() {
		return actual<=agregado.count();
	}

	@Override
	public Usuario actualElement() {
		return (Usuario) this.agregado.get(actual);
	}

}
