package com.example.misvacasapp.llamadaWS.test;

import java.sql.Date;
import java.util.ArrayList;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.example.misvacasapp.llamadaWS.LlamadaProduccionWS;
import com.example.misvacasapp.llamadaWS.LlamadaUsuarioWS;
import com.example.misvacasapp.modelo.Produccion;
import com.example.misvacasapp.modelo.Usuario;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class LlamadaProduccionWSTests extends TestCase{

	private LlamadaUsuarioWS llamadaUsuario;
	private LlamadaProduccionWS llamadaProduccion;
	private Gson json;
	private ArrayList<Produccion> lista;
	
	@Before
	public void setUp() throws Exception {
		json = new GsonBuilder().setPrettyPrinting()
				.setDateFormat("dd-MM-yyyy").create();

		Usuario u = new Usuario("nombre", "apellido1", "apellido2",
				"direccion", "poblacion", 9999999, "dni", "contraseña", 0,
				"correo", "codigo_explotacion");
		Produccion p = new Produccion(1, new Date(123, 2, 3), "leche", "dni", 244);
		lista = new ArrayList<Produccion>();
		lista.add(p);
		
		llamadaUsuario = new LlamadaUsuarioWS();
		llamadaProduccion = new LlamadaProduccionWS();
		
		llamadaUsuario.añadirUsuario(json.toJson(u));
		llamadaProduccion.setProducciones(json.toJson(lista), "dni");
	}

	@After
	public void tearDown() throws Exception {
		llamadaUsuario.eliminarUsuario("dni");
	}

	@Test
	public void testGetProducciones() {
		String resultado = llamadaProduccion.getProducciones("dni");
		ArrayList<Produccion> listaP = json.fromJson(resultado,
				new TypeToken<ArrayList<Produccion>>() {
				}.getType());
		assertEquals(listaP.size(), 1);
		assertEquals(listaP.get(0).getId_produccion(), 1);
		assertEquals(lista.get(0).getCantidad(), 244);
		assertEquals(listaP.get(0).getTipo(), "leche");

	}

	@Test
	public void testSetProducciones() {
		Produccion p = new Produccion(21, new Date(123, 2, 3), "Carne", "dni", 356);
		lista.add(p);
		p = new Produccion(31, new Date(123, 2, 3), "Carne", "dni", 356);
		lista.add(p);
		
		llamadaProduccion.setProducciones(json.toJson(lista), "dni");
		String resultado = llamadaProduccion.getProducciones("dni");
		ArrayList<Produccion> listaP = json.fromJson(resultado,
				new TypeToken<ArrayList<Produccion>>() {
				}.getType());
		assertEquals(listaP.size(), 3);
	}
}
