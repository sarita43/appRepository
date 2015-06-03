package com.example.misvacasapp.modelo.test;

import java.sql.Date;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import com.example.misvacasapp.modelo.Medicamento;

public class MedicamentoTests extends TestCase{

	Medicamento m;
	
	@SuppressWarnings("deprecation")
	@Before
	public void setUp() throws Exception {
		m =  new Medicamento(1111, new Date(5, 2, 150), "antibiotico", "antibiotico descripcion", "ES1212");
	}

	@SuppressWarnings("deprecation")
	@Test
	public void testMedicamento() {
		m = new Medicamento(1244,new Date(6, 6, 2001), "tipo", "descripcion", "ES4343");
		assertEquals(m.getId_medicamento(), 1244);
		assertEquals(m.getFecha(), new Date(6, 6, 2001));
		assertEquals(m.getTipo(), "tipo");
		assertEquals(m.getDescripcion(), "descripcion");
		assertEquals(m.getId_vaca(), "ES4343");
	}

	@Test
	public void testGetId_medicamento() {
		assertEquals(m.getId_medicamento(), 1111);
	}

	@Test
	public void testSetId_medicamento() {
		m.setId_medicamento(2222);
		assertEquals(m.getId_medicamento(), 2222);
	}

	@SuppressWarnings("deprecation")
	@Test
	public void testGetFecha() {
		assertEquals(m.getFecha(), new Date(5, 2, 150));
	}

	@SuppressWarnings("deprecation")
	@Test
	public void testSetFecha() {
		m.setFecha(new Date(12, 5, 2001));
		assertEquals(m.getFecha(), new Date(12, 5, 2001));
	}

	@Test
	public void testGetTipo() {
		assertEquals(m.getTipo(), "antibiotico");
	}

	@Test
	public void testSetTipo() {
		m.setTipo("analgesico");
		assertEquals(m.getTipo(), "analgesico");
	}

	@Test
	public void testGetId_vaca() {
		assertEquals(m.getId_vaca(), "ES1212");
	}

	@Test
	public void testSetId_vaca() {
		m.setId_vaca("ES6666");
		assertEquals(m.getId_vaca(), "ES6666");
	}

	@Test
	public void testGetDescripcion() {
		assertEquals(m.getDescripcion(), "antibiotico descripcion");
	}

	@Test
	public void testSetDescripcion() {
		m.setDescripcion("descripcion del medicamento");
		assertEquals(m.getDescripcion(), "descripcion del medicamento");
	}

}
