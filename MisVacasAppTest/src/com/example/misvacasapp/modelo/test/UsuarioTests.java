package com.example.misvacasapp.modelo.test;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import com.example.misvacasapp.modelo.Usuario;

public class UsuarioTests extends TestCase{

	Usuario u;
	
	@Before
	public void setUp() throws Exception {
		u = new Usuario("sara", "martinez", "lopez", "calle", "leon", 987656565, "71460692Z", "sara", 0, "correo@correo.es", "ES2415100245");
	}

	@Test
	public void testUsuario() {
		u = new Usuario("usuario", "apellido1", "apellido2", "calle", "leon", 98854486, "11111111A", "xxxx", 0, "correo2@correo.es", "ES2466500245");
		assertEquals(u.getNombre(), "usuario");
	}

	
	@Test
	public void testGetNombre() {
		assertEquals(u.getNombre(), "sara");
	}

	@Test
	public void testSetNombre() {
		u.setNombre("SARA");
		assertEquals(u.getNombre(), "SARA");
	}

	@Test
	public void testGetApellido1() {
		assertEquals(u.getApellido1(), "martinez");
	}

	@Test
	public void testSetApellido1() {
		u.setApellido1("MARTINEZ");
		assertEquals(u.getApellido1(), "MARTINEZ");
	}

	@Test
	public void testGetApellido2() {
		assertEquals(u.getApellido2(), "lopez");
	}

	@Test
	public void testSetApellido2() {
		u.setApellido2("LOPEZ");
		assertEquals(u.getApellido2(), "LOPEZ");
	}

	@Test
	public void testGetDireccion() {
		assertEquals(u.getDireccion(), "calle");
	}

	@Test
	public void testSetDireccion() {
		u.setDireccion("calle principal");
		assertEquals(u.getDireccion(), "calle principal");
	}

	@Test
	public void testGetPoblacion() {
		assertEquals(u.getPoblacion(), "leon");
	}

	@Test
	public void testSetPoblacion() {
		u.setPoblacion("LEON");
		assertEquals(u.getPoblacion(), "LEON");
	}

	@Test
	public void testGetTelefono() {
		assertEquals(u.getTelefono(), 987656565);
	}

	@Test
	public void testSetTelefono() {
		u.setTelefono(987121212);
		assertEquals(u.getTelefono(), 987121212);
	}

	@Test
	public void testGetDni() {
		assertEquals(u.getDni(), "71460692Z");
	}

	@Test
	public void testSetDni() {
		u.setDni("12121212X");
		assertEquals(u.getDni(), "12121212X");
	}

	@Test
	public void testGetContraseña() {
		assertEquals(u.getContraseña(), "sara");
	}

	@Test
	public void testSetContraseña() {
		u.setContraseña("12s12s");
		assertEquals(u.getContraseña(), "12s12s");
	}

	@Test
	public void testGetRol() {
		assertEquals(u.getRol(), 0);
	}

	@Test
	public void testSetRol() {
		u.setRol(1);
		assertEquals(u.getRol(), 1);
	}

	@Test
	public void testGetCorreo() {
		assertEquals(u.getCorreo(), "correo@correo.es");
	}

	@Test
	public void testSetCorreo() {
		u.setCorreo("correo@hotmail.com");
		assertEquals(u.getCorreo(), "correo@hotmail.com");
	}

	@Test
	public void testGetCodigo_explotacion() {
		assertEquals(u.getCodigo_explotacion(), "ES2415100245");
	}

	@Test
	public void testSetCodigo_explotacion() {
		u.setCodigo_explotacion("ES121212");
		assertEquals(u.getCodigo_explotacion(), "ES121212");
	}

}
