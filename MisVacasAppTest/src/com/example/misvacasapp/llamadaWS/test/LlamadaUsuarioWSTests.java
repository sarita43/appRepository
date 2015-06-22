package com.example.misvacasapp.llamadaWS.test;

import java.util.ArrayList;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.example.misvacasapp.llamadaWS.LlamadaUsuarioWS;
import com.example.misvacasapp.modelo.Usuario;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class LlamadaUsuarioWSTests extends TestCase {

	private LlamadaUsuarioWS llamadaUsuario;
	private Gson json;

	@Before
	public void setUp() throws Exception {
		json = new GsonBuilder().setPrettyPrinting()
				.setDateFormat("dd-MM-yyyy").create();

		llamadaUsuario = new LlamadaUsuarioWS();
	}

	@After
	public void tearDown() throws Exception {
		llamadaUsuario.eliminarUsuario("dni");
	}

	@Test
	public void testLlamadaUsuario() {
		Usuario u = new Usuario("nombre", "apellido1", "apellido2",
				"direccion", "poblacion", 9999999, "dni", "contraseña", 0,
				"correo", "codigo_explotacion");
		llamadaUsuario.añadirUsuario(json.toJson(u));
		
		u = json.fromJson(llamadaUsuario.LlamadaUsuario("dni", "contraseña"), Usuario.class);
		assertEquals(u.getNombre(), "nombre");
		assertEquals(u.getApellido1(), "apellido1");
		assertEquals(u.getApellido2(), "apellido2");
	}

	@Test
	public void testLlamadaUsuarioExistente() {
		Usuario u = new Usuario("nombre", "apellido1", "apellido2",
				"direccion", "poblacion", 9999999, "dni", "contraseña", 0,
				"correo", "codigo_explotacion");
		llamadaUsuario.añadirUsuario(json.toJson(u));
		assertEquals("true",
				llamadaUsuario.LlamadaUsuarioExistente("dni", "contraseña"));
	}

	@Test
	public void testActualizarContraseña() {
		Usuario u = new Usuario("nombre", "apellido1", "apellido2",
				"direccion", "poblacion", 9999999, "dni", "contraseña", 0,
				"correo", "codigo_explotacion");
		llamadaUsuario.añadirUsuario(json.toJson(u));
		llamadaUsuario.actualizarContraseña("dni", "contraseñaaa");
		u = json.fromJson(llamadaUsuario.LlamadaUsuario("dni", "contraseñaaa"), Usuario.class);
		assertEquals(u.getContraseña(),"contraseñaaa");
	}

	@Test
	public void testEliminarUsuario() {
		Usuario u = new Usuario("nombre", "apellido1", "apellido2",
				"direccion", "poblacion", 9999999, "dni", "contraseña", 0,
				"correo", "codigo_explotacion");
		llamadaUsuario.añadirUsuario(json.toJson(u));
		assertEquals("true",
				llamadaUsuario.LlamadaUsuarioExistente("dni", "contraseña"));
		llamadaUsuario.eliminarUsuario("dni");
		assertEquals("false",
				llamadaUsuario.LlamadaUsuarioExistente("dni", "contraseña"));
	}

	@Test
	public void testAñadirUsuario() {
		Usuario u = new Usuario("nombre", "apellido1", "apellido2",
				"direccion", "poblacion", 9999999, "dni", "contraseña", 0,
				"correo", "codigo_explotacion");
		llamadaUsuario.añadirUsuario(json.toJson(u));
		assertEquals("true",
				llamadaUsuario.LlamadaUsuarioExistente("dni", "contraseña"));
	}

	@Test
	public void testActualizarUsuario() {
		Usuario u = new Usuario("nombre", "apellido1", "apellido2",
				"direccion", "poblacion", 9999999, "dni", "contraseña", 0,
				"correo", "codigo_explotacion");
		llamadaUsuario.añadirUsuario(json.toJson(u));
		llamadaUsuario.actualizarUsuario("dni", "sara", "martinez", "lopez",
				"direccion", "poblacion", 999999, "correo",
				"codigo_explotacion");
		u = json.fromJson(llamadaUsuario.LlamadaUsuario("dni", "contraseña"), Usuario.class);
		assertEquals(u.getNombre(), "sara");
		assertEquals(u.getApellido1(), "martinez");
		assertEquals(u.getApellido2(), "lopez");
	}

	@Test
	public void testLlamadaListaUsuarios() {
		Usuario u = new Usuario("nombre", "apellido1", "apellido2",
				"direccion", "poblacion", 9999999, "dni", "contraseña", 0,
				"correo", "codigo_explotacion");
		llamadaUsuario.añadirUsuario(json.toJson(u));
		
		ArrayList<Usuario> listaUsuarios = json.fromJson(llamadaUsuario.LlamadaListaUsuarios(),
				new TypeToken<ArrayList<Usuario>>() {
				}.getType());
		
		assertNotSame(0, listaUsuarios.size());
	}

}
