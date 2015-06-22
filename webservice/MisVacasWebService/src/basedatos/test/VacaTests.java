package basedatos.test;

import static org.junit.Assert.*;

import java.sql.Date;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import basedatos.Usuario;
import basedatos.Vaca;

public class VacaTests {

	private Vaca v;
	private Usuario u;
	private Gson json;

	@Before
	public void setUp() throws Exception {
		json = new GsonBuilder().setPrettyPrinting()
				.setDateFormat("dd-MM-yyyy").create();

		u = new Usuario("nombre", "apellido1", "apellido2", "direccion",
				"poblacion", 9999999, "dni", "contraseña", 0, "correo",
				"codigo_explotacion");
		v = new Vaca("ES11111", "frisona", new Date(1, 4, 2012), "ES22222",
				"71460692Z", "M", "");

		u.añadirUsuario(json.toJson(u));
		v.añadirVaca(json.toJson(v));
	}

	@After
	public void tearDown() throws Exception {
		u.eliminarUsuario("dni");
	}

	@Test
	public void testListaVacasStringString() {
		ArrayList<Vaca> lista = json.fromJson(v.listaVacasString("dni"),
				new TypeToken<ArrayList<Vaca>>() {
				}.getType());
		assertEquals(1, lista.size());
	}

	@Test
	public void testVacaString() {
		Vaca vaca = json.fromJson(v.vacaString("ES11111", "dni"), Vaca.class);
		assertEquals("ES11111", vaca.getId_vaca());
	}

	@Test
	public void testAñadirVaca() {
		Vaca vaca = new Vaca("3333", "raza", new Date(111, 1, 1), "345", "dni",
				"M", "");
		v.añadirVaca(json.toJson(vaca));
		ArrayList<Usuario> lista = json.fromJson(v.listaVacasString("dni"),
				new TypeToken<ArrayList<Vaca>>() {
				}.getType());
		assertEquals(2, lista.size());
	}

	@Test
	public void testEliminarVaca() {
		v.eliminarVaca("ES11111", "dni");
		ArrayList<Vaca> lista = json.fromJson(v.listaVacasString("dni"),
				new TypeToken<ArrayList<Vaca>>() {
				}.getType());
		assertEquals(1, lista.size());
		assertEquals("0", lista.get(0).getId_vaca());
	}

	@Test
	public void testEliminarVacas() {
		v.eliminarVacas("dni");
		ArrayList<Vaca> lista = json.fromJson(v.listaVacasString("dni"),
				new TypeToken<ArrayList<Vaca>>() {
				}.getType());
		assertEquals(1, lista.size());
		assertEquals("0", lista.get(0).getId_vaca());
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
		assertEquals(v.getFoto(), "");
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
