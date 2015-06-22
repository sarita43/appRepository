package com.example.misvacasapp.llamadaWS.test;

import java.sql.Date;
import java.util.ArrayList;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.example.misvacasapp.llamadaWS.LlamadaMedicamentoWS;
import com.example.misvacasapp.llamadaWS.LlamadaUsuarioWS;
import com.example.misvacasapp.llamadaWS.LlamadaVacaWS;
import com.example.misvacasapp.modelo.Medicamento;
import com.example.misvacasapp.modelo.Usuario;
import com.example.misvacasapp.modelo.Vaca;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class LlamadaMedicamentoWSTests extends TestCase {

	private LlamadaMedicamentoWS llamadaMedicamento;
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
		Medicamento m = new Medicamento(1, new Date(113, 1, 1), "tipo",
				"descripcion", "5555");

		llamadaMedicamento = new LlamadaMedicamentoWS();
		llamadaUsuario = new LlamadaUsuarioWS();
		llamadaVaca = new LlamadaVacaWS();

		llamadaUsuario.añadirUsuario(json.toJson(u));
		llamadaVaca.LLamadaAñadirVaca(json.toJson(v));
		llamadaMedicamento.LLamadaAñadirMedicamento(json.toJson(m));
	}

	@After
	public void tearDown() throws Exception {
		llamadaUsuario.eliminarUsuario("dni");
	}

	@Test
	public void testLlamadaListaMedicamentos() {
		String resultado = llamadaMedicamento.LlamadaListaMedicamentos("5555");
		ArrayList<Medicamento> listaM = json.fromJson(resultado,
				new TypeToken<ArrayList<Medicamento>>() {
				}.getType());
		assertEquals(listaM.size(), 1);
		assertEquals(listaM.get(0).getId_medicamento(), 1);
	}

	@Test
	public void testLLamadaAñadirMedicamento() {
		Medicamento med = new Medicamento(2, new Date(123, 2, 2), "", "", "5555");
		llamadaMedicamento.LLamadaAñadirMedicamento(json.toJson(med));
		String resultado = llamadaMedicamento.LlamadaMedicamento("5555", "2");
		med = json.fromJson(resultado,Medicamento.class);
		assertEquals(med.getId_medicamento(), 2);
	}

	@Test
	public void testLLamadaEliminarMedicamento() {
		Medicamento med = new Medicamento(2, new Date(123, 2, 2), "", "", "5555");
		llamadaMedicamento.LLamadaAñadirMedicamento(json.toJson(med));
		
		String resultado = llamadaMedicamento.LlamadaListaMedicamentos("5555");
		ArrayList<Medicamento> listaM = json.fromJson(resultado,
				new TypeToken<ArrayList<Medicamento>>() {
				}.getType());
		assertEquals(listaM.size(), 2);
		
		llamadaMedicamento.LLamadaEliminarMedicamento("2", "5555");
		
		resultado = llamadaMedicamento.LlamadaListaMedicamentos("5555");
		listaM = json.fromJson(resultado,
				new TypeToken<ArrayList<Medicamento>>() {
				}.getType());
		assertEquals(listaM.size(), 1);
	}


	@Test
	public void testLLamadaEliminarMedicamentos() {
		llamadaMedicamento.LLamadaEliminarMedicamentos("5555");
		String resultado = llamadaMedicamento.LlamadaListaMedicamentos("5555");
		ArrayList<Medicamento> listaM = json.fromJson(resultado,
				new TypeToken<ArrayList<Medicamento>>() {
				}.getType());
		assertEquals(listaM.size(), 1);
		assertEquals(listaM.get(0).getId_medicamento(), 0);
		assertNull(listaM.get(0).getFecha());
		assertNull(listaM.get(0).getDescripcion());
		assertNull(listaM.get(0).getTipo());
		assertNull(listaM.get(0).getId_vaca());
	}

}
