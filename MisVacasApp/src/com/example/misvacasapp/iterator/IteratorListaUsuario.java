package com.example.misvacasapp.iterator;

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
		if(actual==agregado.count()-1){
			return false;
		}else{
			return actual<agregado.count()-1;
		}
	}

	@Override
	public Usuario actualElement() {
		return (Usuario) this.agregado.get(actual);
	}

}
