package basedatos.test;

import static org.junit.Assert.*;

import java.sql.Date;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import basedatos.Produccion;
import basedatos.Usuario;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class ProduccionTests {

	private Produccion p;
	private Usuario u;
	private Gson json;
	
	@Before
	public void setUp() throws Exception {
		json = new GsonBuilder().setPrettyPrinting()
				.setDateFormat("dd-MM-yyyy").create();

		u = new Usuario("nombre", "apellido1", "apellido2",
				"direccion", "poblacion", 9999999, "dni", "contraseña", 0,
				"correo", "codigo_explotacion");
		p = new Produccion(1, new Date(111, 2, 2), "tipo", "dni", 34);
		
		ArrayList<Produccion> listaProducciones = new ArrayList<Produccion>();
		listaProducciones.add(p);
		u.añadirUsuario(json.toJson(u));
		p.setProducciones(json.toJson(listaProducciones), "dni");
	}

	@After
	public void tearDown() throws Exception {
		u.eliminarUsuario("dni");
	}

	@Test
	public void testGetProducciones() {
		ArrayList<Produccion> listaP = json.fromJson(p.getProducciones("dni")
				,
				new TypeToken<ArrayList<Produccion>>() {
				}.getType());
		assertEquals(1, listaP.size());
		assertEquals(1, listaP.get(0).getId_produccion());
	}

	@Test
	public void testSetProducciones() {
		ArrayList<Produccion> listaProducciones = new ArrayList<Produccion>();
		Produccion prod = new Produccion(2, new Date(122, 2, 2), "tipo", "dni", 21);
		listaProducciones.add(prod);
		p.setProducciones(json.toJson(listaProducciones), "dni");
		ArrayList<Produccion> listaP = json.fromJson(p.getProducciones("dni")
				,
				new TypeToken<ArrayList<Produccion>>() {
				}.getType());
		assertEquals(2, listaP.size());
	}

	@Test
	public void testEliminarProduccion() {
		p.eliminarProduccion("dni");
		ArrayList<Produccion> listaP = json.fromJson(p.getProducciones("dni")
				,
				new TypeToken<ArrayList<Produccion>>() {
				}.getType());
		assertEquals(0, listaP.get(0).getId_produccion());
	}

	@Test
	public void testGetId_produccion() {
		assertEquals(1, p.getId_produccion());
	}

	@Test
	public void testSetId_produccion() {
		p.setId_produccion(2);
		assertEquals(2, p.getId_produccion());
	}

	@Test
	public void testGetFecha() {
		assertEquals(new Date(111, 2, 2), p.getFecha());
	}

	@Test
	public void testSetFecha() {
		p.setFecha(new Date(112, 2, 2));
		assertEquals(new Date(112, 2, 2), p.getFecha());
	}

	@Test
	public void testGetTipo() {
		assertEquals("tipo", p.getTipo());
	}

	@Test
	public void testSetTipo() {
		p.setTipo("Leche");
		assertEquals("Leche", p.getTipo());
	}

	@Test
	public void testGetCantidad() {
		assertEquals(34, p.getCantidad());
	}

	@Test
	public void testSetCantidad() {
		p.setCantidad(98);
		assertEquals(98, p.getCantidad());
	}

	@Test
	public void testGetId_usuario() {
		assertEquals("dni", p.getId_usuario());
	}

	@Test
	public void testSetId_usuario() {
		p.setId_usuario("234");
		assertEquals("234", p.getId_usuario());
	}

}
