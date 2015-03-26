package com.example.misvacasapp.iterator;


import java.util.ArrayList;

import com.example.misvacasapp.modelo.Usuario;

public class AgregadoUsuario implements Agregado {
	
	private ArrayList<Usuario> listaUsuarios;
	
	public AgregadoUsuario(ArrayList<Usuario> listaUsuarios){
		this.listaUsuarios = listaUsuarios;
	}
	
	@Override
	public Iterator createIterator() {
		return new IteratorListaUsuario(this);
	}

	@Override
	public Usuario get(int item) {
		return this.listaUsuarios.get(item);
	}

	@Override
	public int count() {
		return this.listaUsuarios.size();
	}

}
