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
				"direccion", "poblacion", 9999999, "dni", "contrase�a", 0,
				"correo", "codigo_explotacion");
		llamadaUsuario.a�adirUsuario(json.toJson(u));
		
		u = json.fromJson(llamadaUsuario.LlamadaUsuario("dni", "contrase�a"), Usuario.class);
		assertEquals(u.getNombre(), "nombre");
		assertEquals(u.getApellido1(), "apellido1");
		assertEquals(u.getApellido2(), "apellido2");
	}

	@Test
	public void testLlamadaUsuarioExistente() {
		Usuario u = new Usuario("nombre", "apellido1", "apellido2",
				"direccion", "poblacion", 9999999, "dni", "contrase�a", 0,
				"correo", "codigo_explotacion");
		llamadaUsuario.a�adirUsuario(json.toJson(u));
		assertEquals("true",
				llamadaUsuario.LlamadaUsuarioExistente("dni", "contrase�a"));
	}

	@Test
	public void testActualizarContrase�a() {
		Usuario u = new Usuario("nombre", "apellido1", "apellido2",
				"direccion", "poblacion", 9999999, "dni", "contrase�a", 0,
				"correo", "codigo_explotacion");
		llamadaUsuario.a�adirUsuario(json.toJson(u));
		llamadaUsuario.actualizarContrase�a("dni", "contrase�aaa");
		u = json.fromJson(llamadaUsuario.LlamadaUsuario("dni", "contrase�aaa"), Usuario.class);
		assertEquals(u.getContrase�a(),"contrase�aaa");
	}

	@Test
	public void testEliminarUsuario() {
		Usuario u = new Usuario("nombre", "apellido1", "apellido2",
				"direccion", "poblacion", 9999999, "dni", "contrase�a", 0,
				"correo", "codigo_explotacion");
		llamadaUsuario.a�adirUsuario(json.toJson(u));
		assertEquals("true",
				llamadaUsuario.LlamadaUsuarioExistente("dni", "contrase�a"));
		llamadaUsuario.eliminarUsuario("dni");
		assertEquals("false",
				llamadaUsuario.LlamadaUsuarioExistente("dni", "contrase�a"));
	}

	@Test
	public void testA�adirUsuario() {
		Usuario u = new Usuario("nombre", "apellido1", "apellido2",
				"direccion", "poblacion", 9999999, "dni", "contrase�a", 0,
				"correo", "codigo_explotacion");
		llamadaUsuario.a�adirUsuario(json.toJson(u));
		assertEquals("true",
				llamadaUsuario.LlamadaUsuarioExistente("dni", "contrase�a"));
	}

	@Test
	public void testActualizarUsuario() {
		Usuario u = new Usuario("nombre", "apellido1", "apellido2",
				"direccion", "poblacion", 9999999, "dni", "contrase�a", 0,
				"correo", "codigo_explotacion");
		llamadaUsuario.a�adirUsuario(json.toJson(u));
		llamadaUsuario.actualizarUsuario("dni", "sara", "martinez", "lopez",
				"direccion", "poblacion", 999999, "correo",
				"codigo_explotacion");
		u = json.fromJson(llamadaUsuario.LlamadaUsuario("dni", "contrase�a"), Usuario.class);
		assertEquals(u.getNombre(), "sara");
		assertEquals(u.getApellido1(), "martinez");
		assertEquals(u.getApellido2(), "lopez");
	}

	@Test
	public void testLlamadaListaUsuarios() {
		Usuario u = new Usuario("nombre", "apellido1", "apellido2",
				"direccion", "poblacion", 9999999, "dni", "contrase�a", 0,
				"correo", "codigo_explotacion");
		llamadaUsuario.a�adirUsuario(json.toJson(u));
		
		ArrayList<Usuario> listaUsuarios = json.fromJson(llamadaUsuario.LlamadaListaUsuarios(),
				new TypeToken<ArrayList<Usuario>>() {
				}.getType());
		
		assertNotSame(0, listaUsuarios.size());
	}

}
