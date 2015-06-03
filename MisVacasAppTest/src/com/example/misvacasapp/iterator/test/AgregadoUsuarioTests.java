package com.example.misvacasapp.iterator.test;

import java.util.ArrayList;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import com.example.misvacasapp.iterator.AgregadoUsuario;
import com.example.misvacasapp.iterator.IteratorListaUsuario;
import com.example.misvacasapp.modelo.Usuario;

public class AgregadoUsuarioTests extends TestCase{

	private ArrayList<Usuario> listaUsuarios;
	private Usuario u;
	private AgregadoUsuario agregado;
	
	@Before
	public void setUp(){
		listaUsuarios = new ArrayList<Usuario>();
		u =  new Usuario("usuario1", "apellido1", "apellido2", "direccion",
				"poblacion", 23455, "71460692Z", "usuario1", 0,
				"correo@gmail.com", "codigo_explotacion");
		listaUsuarios.add(u);
		u = new Usuario("nombre2", "apellido", "apellido", "direccion2",
				"poblacion2", 3874772, "23488530", "contraseña2", 0, "correo2",
				"codigo_explotacion2");
		listaUsuarios.add(u);
	}
	
	@Test
	public void testAgregadoUsuario() {
		agregado = new AgregadoUsuario(listaUsuarios);
		assertEquals(2, agregado.count());
	}

	@Test
	public void testCreateIterator() {
		agregado = new AgregadoUsuario(listaUsuarios);
		IteratorListaUsuario i = (IteratorListaUsuario) agregado.createIterator();
		assertEquals("usuario1", i.actualElement().getNombre());
		i.next();
		assertEquals("nombre2", i.actualElement().getNombre());
	}

	@Test
	public void testGet() {
		agregado = new AgregadoUsuario(listaUsuarios);
		Usuario u = agregado.get(1);
		assertEquals("nombre2", u.getNombre());
	}

	@Test
	public void testCount() {
		agregado = new AgregadoUsuario(listaUsuarios);
		assertEquals(2, agregado.count());
	}

}
