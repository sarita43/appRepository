package com.example.misvacasapp.iterator.test;


import java.sql.Date;
import java.util.ArrayList;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import com.example.misvacasapp.iterator.AgregadoMedicamento;
import com.example.misvacasapp.iterator.IteratorListaMedicamento;
import com.example.misvacasapp.modelo.Medicamento;

public class IteratorListaMedicamentoTests extends TestCase{

	AgregadoMedicamento agregado;
	ArrayList<Medicamento> listaMedicamentos;
	IteratorListaMedicamento iterador;
	
	@SuppressWarnings("deprecation")
	@Before
	public void setUp() throws Exception {
		listaMedicamentos = new ArrayList<Medicamento>();
		Medicamento m = new Medicamento(1224, new Date(2, 4, 2001), "tipo", "descripcion", "ES25653");
		listaMedicamentos.add(m);
		m = new Medicamento(3452, new Date(5, 4, 2012), "brucelosis", "prevencion de brucelosis", "ES25653");
		listaMedicamentos.add(m);
		agregado = new AgregadoMedicamento(listaMedicamentos);
	}

	@Test
	public void testIteratorListaMedicamento() {
		iterador = new IteratorListaMedicamento(agregado);
		iterador.first();
		assertEquals(iterador.actualElement().getId_medicamento(), 1224);
	}

	@Test
	public void testFirst() {
		iterador = new IteratorListaMedicamento(agregado);
		iterador.first();
		assertEquals(iterador.actualElement().getId_medicamento(), 1224);
	}
	@Test
	public void testFirstOtro() {
		iterador = new IteratorListaMedicamento(agregado);
		iterador.next();
		assertEquals(iterador.actualElement().getId_medicamento(), 3452);
		iterador.first();
		assertEquals(iterador.actualElement().getId_medicamento(), 1224);
	}

	@Test
	public void testNext() {
		iterador = new IteratorListaMedicamento(agregado);
		iterador.next();
		assertEquals(iterador.actualElement().getId_medicamento(), 3452);
	}

	@Test
	public void testHasNextFalse() {
		iterador = new IteratorListaMedicamento(agregado);
		iterador.next();
		assertFalse(iterador.hasNext());
	}
	
	@Test
	public void testHasNextTrue() {
		iterador = new IteratorListaMedicamento(agregado);
		assertTrue(iterador.hasNext());
	}

	@Test
	public void testActualElement() {
		iterador = new IteratorListaMedicamento(agregado);
		iterador.next();
		assertEquals(iterador.actualElement().getId_medicamento(), 3452);
		iterador.first();
		assertEquals(iterador.actualElement().getId_medicamento(), 1224);
	}

}
