package com.example.misvacasapp.modelo.test;


import java.sql.Date;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import com.example.misvacasapp.modelo.Vaca;


public class VacaTests extends TestCase{

	Vaca v;
	
	@SuppressWarnings("deprecation")
	@Before
	public void setUp() throws Exception {
		v = new Vaca("ES11111", "frisona", new Date(1, 4, 2012), "ES22222", "71460692Z", "M", null);
	}

	@SuppressWarnings("deprecation")
	@Test
	public void testVaca() {
		v = new Vaca("ES1212", "belga", new Date(2, 5, 2009), "ES2323", "71460692Z", "M", null);
		assertEquals(v.getId_vaca(), "ES1212");
	}

	@Test
	public void testGetId_vaca() {
		assertEquals(v.getId_vaca(), "ES11111");
	}

	@Test
	public void testSetId_vaca() {
		v.setId_vaca("ES44444");
		assertEquals(v.getId_vaca(), "ES44444");
	}

	@Test
	public void testGetRaza() {
		assertEquals(v.getRaza(), "frisona");
	}

	@Test
	public void testSetRaza() {
		v.setRaza("belga");
		assertEquals(v.getRaza(), "belga");
	}

	@SuppressWarnings("deprecation")
	@Test
	public void testGetFecha_nacimiento() {
		assertEquals(v.getFecha_nacimiento(), new Date(1, 4, 2012));
	}

	@SuppressWarnings("deprecation")
	@Test
	public void testSetFecha_nacimiento() {
		v.setFecha_nacimiento(new Date(5, 3, 2011));
		assertEquals(v.getFecha_nacimiento(), new Date(5, 3, 2011));
	}

	@Test
	public void testGetId_madre() {
		assertEquals(v.getId_madre(), "ES22222");
	}

	@Test
	public void testSetId_madre() {
		v.setId_madre("ES55555");
		assertEquals(v.getId_madre(), "ES55555");
	}

	@Test
	public void testGetFoto() {
		assertNull(v.getFoto());
	}

	@Test
	public void testSetFoto() {
		v.setFoto("");
		assertEquals(v.getFoto(), "");
	}

	@Test
	public void testGetId_usuario() {
		assertEquals(v.getId_usuario(), "71460692Z");
	}

	@Test
	public void testSetId_usuario() {
		v.setId_usuario("11111111X");
		assertEquals(v.getId_usuario(), "11111111X");
	}

	@Test
	public void testGetSexo() {
		assertEquals(v.getSexo(), "M");
	}

	@Test
	public void testSetSexo() {
		v.setSexo("H");
		assertEquals(v.getSexo(), "H");
	}

}
