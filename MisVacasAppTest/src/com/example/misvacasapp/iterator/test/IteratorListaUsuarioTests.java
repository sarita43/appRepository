package com.example.misvacasapp.iterator.test;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;

import com.example.misvacasapp.iterator.AgregadoUsuario;
import com.example.misvacasapp.iterator.IteratorListaUsuario;
import com.example.misvacasapp.modelo.Usuario;

public class IteratorListaUsuarioTests extends TestCase {

	private AgregadoUsuario agregado;
	private Usuario u;
	ArrayList<Usuario> listaUsuarios;

	@Before
	public void setUp() {
		listaUsuarios = new ArrayList<Usuario>();
		u = new Usuario("usuario1", "apellido1", "apellido2", "direccion",
				"poblacion", 23455, "71460692Z", "usuario1", 0,
				"correo@gmail.com", "codigo_explotacion");
		listaUsuarios.add(u);
		agregado = new AgregadoUsuario(listaUsuarios);
	}

	@Test
	public void testIteratorListaUsuario() {
		IteratorListaUsuario i = new IteratorListaUsuario(agregado);
		assertEquals(i.actualElement(), u);
	}

	@Test
	public void testFirst() {
		IteratorListaUsuario i = new IteratorListaUsuario(agregado);
		i.first();
		assertEquals(i.actualElement().getApellido1(), u.getApellido1());
	}

	@Test
	public void testNext() {
		u = new Usuario("nombre2", "apellido", "apellido", "direccion2",
				"poblacion2", 3874772, "23488530", "contraseña2", 0, "correo2",
				"codigo_explotacion2");
		listaUsuarios.add(u);
		agregado = new AgregadoUsuario(listaUsuarios);
		IteratorListaUsuario i = new IteratorListaUsuario(agregado);
		i.next();
		assertEquals("nombre2", i.actualElement().getNombre());
	}

	@Test
	public void testHasNextFalse() {
		IteratorListaUsuario i = new IteratorListaUsuario(agregado);
		assertFalse(i.hasNext());
	}

	@Test
	public void testHasNextTrue() {
		u = new Usuario("nombre2", "apellido", "apellido", "direccion2",
				"poblacion2", 3874772, "23488530", "contraseña2", 0, "correo2",
				"codigo_explotacion2");
		listaUsuarios.add(u);
		agregado = new AgregadoUsuario(listaUsuarios);
		IteratorListaUsuario i = new IteratorListaUsuario(agregado);
		assertTrue(i.hasNext());
	}

	@Test
	public void testActualElement() {
		IteratorListaUsuario i = new IteratorListaUsuario(agregado);
		assertEquals(i.actualElement(), u);

		u = new Usuario("nombre2", "apellido", "apellido", "direccion2",
				"poblacion2", 3874772, "23488530", "contraseña2", 0, "correo2",
				"codigo_explotacion2");
		listaUsuarios.add(u);
		agregado = new AgregadoUsuario(listaUsuarios);
		i = new IteratorListaUsuario(agregado);
		i.next();
		assertEquals(u, i.actualElement());
	}

}
