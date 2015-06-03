package com.example.misvacasapp.iterator.test;

import java.sql.Date;
import java.util.ArrayList;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import com.example.misvacasapp.iterator.AgregadoVaca;
import com.example.misvacasapp.iterator.IteratorListaVaca;
import com.example.misvacasapp.modelo.Vaca;

public class IteratorListaVacaTest extends TestCase{

	AgregadoVaca agregado;
	ArrayList<Vaca> listaVacas;
	IteratorListaVaca iterador;
	
	@SuppressWarnings("deprecation")
	@Before
	public void setUp() {
		listaVacas = new ArrayList<Vaca>();
		Vaca v = new Vaca("ES23455", "raza", new Date(2, 4, 2014), "ES24673", "1212", "M", null);
		listaVacas.add(v);
		v = new Vaca("ES4354", "raza", new Date(1, 5, 1999), "ES2455", "1212", "H", null);
		listaVacas.add(v);
		agregado = new AgregadoVaca(listaVacas);
	}

	@Test
	public void testIteratorListaVaca() {
		iterador =  new IteratorListaVaca(agregado);
		iterador.first();
		assertEquals("ES23455", iterador.actualElement().getId_vaca());
	}

	@Test
	public void testFirst() {
		iterador =  new IteratorListaVaca(agregado);
		iterador.first();
		assertEquals("ES23455", iterador.actualElement().getId_vaca());
	}
	
	@Test
	public void testFirstOtro() {
		iterador =  new IteratorListaVaca(agregado);
		iterador.next();
		assertEquals("ES4354", iterador.actualElement().getId_vaca());
		iterador.first();
		assertEquals("ES23455", iterador.actualElement().getId_vaca());
	}

	@Test
	public void testNext() {
		iterador =  new IteratorListaVaca(agregado);
		iterador.next();
		assertEquals("ES4354", iterador.actualElement().getId_vaca());
	}

	@Test
	public void testHasNextFalse() {
		iterador =  new IteratorListaVaca(agregado);
		iterador.next();
		assertFalse(iterador.hasNext());
	}
	
	@Test
	public void testHasNextTrue() {
		iterador =  new IteratorListaVaca(agregado);
		assertTrue(iterador.hasNext());
	}

	@Test
	public void testActualElement() {
		iterador =  new IteratorListaVaca(agregado);iterador =  new IteratorListaVaca(agregado);
		assertEquals("ES23455", iterador.actualElement().getId_vaca());
		iterador.next();
		assertEquals("ES4354", iterador.actualElement().getId_vaca());
	}

}
