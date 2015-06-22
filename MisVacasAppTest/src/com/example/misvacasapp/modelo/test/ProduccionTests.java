package com.example.misvacasapp.modelo.test;

import java.sql.Date;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import com.example.misvacasapp.modelo.Produccion;

public class ProduccionTests extends TestCase {

	private Produccion p;

	@Before
	public void setUp() throws Exception {
		p = new Produccion(1, new Date(112, 2, 2), "Leche", "4321", 56);
	}

	@Test
	public void testProduccion() {
		assertEquals(p.getId_produccion(), 1);
		p = new Produccion();
		assertEquals(p.getId_produccion(), 0);
		assertNull(p.getFecha());
		assertNull(p.getTipo());
		assertNull(p.getId_usuario());
		assertEquals(p.getCantidad(), 0);
	}

	@Test
	public void testProduccionIntDateStringStringInt() {
		assertEquals(p.getId_produccion(), 1);
		p = new Produccion(2, new Date(112, 2, 2), "Leche", "4321", 56);
		assertEquals(p.getId_produccion(), 2);
	}

	@Test
	public void testGetId_produccion() {
		assertEquals(p.getId_produccion(), 1);
	}

	@Test
	public void testSetId_produccion() {
		p.setId_produccion(2);
		assertEquals(p.getId_produccion(), 2);
	}

	@Test
	public void testGetFecha() {
		assertEquals(p.getFecha(), new Date(112, 2, 2));
	}

	@Test
	public void testSetFecha() {
		p.setFecha(new Date(113, 3, 3));
		assertEquals(p.getFecha(), new Date(113, 3, 3));
	}

	@Test
	public void testGetTipo() {
		assertEquals(p.getTipo(), "Leche");
	}

	@Test
	public void testSetTipo() {
		p.setTipo("Carne");
		assertEquals(p.getTipo(), "Carne");
	}

	@Test
	public void testGetCantidad() {
		assertEquals(p.getCantidad(), 56);
	}

	@Test
	public void testSetCantidad() {
		p.setCantidad(12);
		assertEquals(p.getCantidad(), 12);
	}

	@Test
	public void testGetId_usuario() {
		assertEquals(p.getId_usuario(), "4321");
	}

	@Test
	public void testSetId_usuario() {
		p.setId_usuario("1234");
		assertEquals(p.getId_usuario(), "1234");
	}

}
