package com.example.misvacasapp.iterator.test;

import java.sql.Date;
import java.util.ArrayList;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import com.example.misvacasapp.iterator.AgregadoMedicamento;
import com.example.misvacasapp.iterator.IteratorListaMedicamento;
import com.example.misvacasapp.modelo.Medicamento;

public class AgregadoMedicamentoTests extends TestCase{

	ArrayList<Medicamento> listaMedicamentos;
	AgregadoMedicamento agregado;
	
	@SuppressWarnings("deprecation")
	@Before 
	public void setUp() throws Exception {
		listaMedicamentos = new ArrayList<Medicamento>();
		Medicamento m = new Medicamento(1224, new Date(2, 4, 2001), "tipo", "descripcion", "ES25653");
		listaMedicamentos.add(m);
		m = new Medicamento(3452, new Date(5, 4, 2012), "brucelosis", "prevencion de brucelosis", "ES25653");
		listaMedicamentos.add(m);
	}

	@Test
	public void testAgregadoMedicamento() {
		agregado = new AgregadoMedicamento(listaMedicamentos);
		assertEquals(agregado.count(), 2);
	}

	@Test
	public void testCreateIterator() {
		agregado = new AgregadoMedicamento(listaMedicamentos);
		IteratorListaMedicamento i = (IteratorListaMedicamento) agregado.createIterator();
		assertEquals(i.actualElement().getId_medicamento(),1224);
	}

	@Test
	public void testGet() {
		agregado = new AgregadoMedicamento(listaMedicamentos);
		assertEquals(agregado.get(0).getId_medicamento(), 1224);
	}

	@Test
	public void testCount() {
		agregado = new AgregadoMedicamento(listaMedicamentos);
		assertEquals(agregado.count(), 2);
	}

}
