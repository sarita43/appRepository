package com.example.misvacasapp.iterator.test;


import java.sql.Date;
import java.util.ArrayList;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import com.example.misvacasapp.iterator.AgregadoVaca;
import com.example.misvacasapp.iterator.IteratorListaVaca;
import com.example.misvacasapp.modelo.Vaca;

public class AgregadoVacaTests extends TestCase {

	private AgregadoVaca agregado;
	private ArrayList<Vaca> listaVacas;
	
	@SuppressWarnings("deprecation")
	@Before
	public void setUp() throws Exception {
		listaVacas = new ArrayList<Vaca>();
		Vaca v = new Vaca("ES23455", "raza", new Date(2, 4, 2014), "ES24673", "1212", "M", null);
		listaVacas.add(v);
		v = new Vaca("ES4354", "raza", new Date(1, 5, 1999), "ES2455", "1212", "H", null);
		listaVacas.add(v);
	}

	@Test
	public void testAgregadoVaca() {
		agregado = new AgregadoVaca(listaVacas);
		assertEquals(agregado.count(), 2);
	}

	@Test
	public void testCreateIterator() {
		agregado = new AgregadoVaca(listaVacas);
		IteratorListaVaca i = (IteratorListaVaca) agregado.createIterator();
		assertEquals(i.actualElement().getId_vaca(), "ES23455");
	}

	@Test
	public void testGet() {
		agregado = new AgregadoVaca(listaVacas);
		assertEquals(agregado.get(1).getId_vaca(), "ES4354");
	}

	@Test
	public void testCount() {
		agregado = new AgregadoVaca(listaVacas);
		assertEquals(agregado.count(), 2);
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void testCountOtro() {
		Vaca v = new Vaca("ES6756", "raza", new Date(7, 1, 2001), "ES4566", "71460692", "M", null);
		listaVacas.add(v);
		agregado = new AgregadoVaca(listaVacas);
		assertEquals(agregado.count(), 3);
	}

}
