package com.example.misvacasapp.llamadaWS.test;

import static org.junit.Assert.*;

import java.sql.Date;
import java.util.ArrayList;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.example.misvacasapp.llamadaWS.LlamadaUsuarioWS;
import com.example.misvacasapp.llamadaWS.LlamadaVacaWS;
import com.example.misvacasapp.modelo.Usuario;
import com.example.misvacasapp.modelo.Vaca;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class LlamadaVacaWSTests extends TestCase{

	private LlamadaUsuarioWS llamadaUsuario;
	private LlamadaVacaWS llamadaVaca;
	private Gson json;

	@Before
	public void setUp() throws Exception {
		json = new GsonBuilder().setPrettyPrinting()
				.setDateFormat("dd-MM-yyyy").create();

		Usuario u = new Usuario("nombre", "apellido1", "apellido2",
				"direccion", "poblacion", 9999999, "dni", "contraseña", 0,
				"correo", "codigo_explotacion");
		Vaca v = new Vaca("5555", "raza", new Date(111, 2, 2), "id_madre",
				"dni", "M", "");
		llamadaUsuario = new LlamadaUsuarioWS();
		llamadaVaca = new LlamadaVacaWS();

		llamadaUsuario.añadirUsuario(json.toJson(u));
		llamadaVaca.LLamadaAñadirVaca(json.toJson(v));
	}

	@After
	public void tearDown() throws Exception {
		llamadaUsuario.eliminarUsuario("dni");
	}

	@Test
	public void testLlamadaListaVacas() {
		ArrayList<Vaca> listaV = json.fromJson(
				llamadaVaca.LlamadaListaVacas("dni"),
				new TypeToken<ArrayList<Vaca>>() {
				}.getType());
		assertEquals(1, listaV.size());
	}

	@Test
	public void testLlamadaVaca() {
		Vaca v = json.fromJson(llamadaVaca.LlamadaVaca("5555", "dni"), Vaca.class);
		assertEquals("5555", v.getId_vaca());
		assertEquals("M", v.getSexo());
	}

	@Test
	public void testLLamadaAñadirVaca() {
		Vaca v = new Vaca("4444", "raza", new Date(111, 2, 2), "id_madre",
				"dni", "M", "");
		llamadaVaca.LLamadaAñadirVaca(json.toJson(v));
		ArrayList<Vaca> listaV = json.fromJson(
				llamadaVaca.LlamadaListaVacas("dni"),
				new TypeToken<ArrayList<Vaca>>() {
				}.getType());
		assertEquals(2, listaV.size());
	}

	@Test
	public void testLLamadaEliminarVaca() {
		llamadaVaca.LLamadaEliminarVaca("5555", "dni");
		Vaca v = json.fromJson(llamadaVaca.LlamadaVaca("5555", "dni"), Vaca.class);
		assertEquals("0", v.getId_vaca());
		assertNull(v.getSexo());	}

	@Test
	public void testLLamadaEliminarVacas() {
		llamadaVaca.LLamadaEliminarVacas("dni");
		ArrayList<Vaca> listaV = json.fromJson(
				llamadaVaca.LlamadaListaVacas("dni"),
				new TypeToken<ArrayList<Vaca>>() {
				}.getType());
		assertEquals("0", listaV.get(0).getId_vaca());
	}

}
