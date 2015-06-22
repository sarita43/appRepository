package fachada.test;

import static org.junit.Assert.*;

import java.sql.Date;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import basedatos.Medicamento;
import basedatos.Produccion;
import basedatos.Usuario;
import basedatos.Vaca;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import fachada.FachadaWebService;

public class FachadaWebServiceTests {

	private Medicamento m;
	private Vaca v;
	private Produccion p;
	private Usuario u;
	private Gson json;

	private FachadaWebService fachada;

	@Before
	public void setUp() throws Exception {
		fachada = new FachadaWebService();
		json = new GsonBuilder().setPrettyPrinting()
				.setDateFormat("dd-MM-yyyy").create();

		u = new Usuario("nombre", "apellido1", "apellido2", "direccion",
				"poblacion", 9999999, "dni", "contraseña", 0, "correo",
				"codigo_explotacion");
		v = new Vaca("3333", "raza", new Date(212, 3, 1), "1111", "dni", "M",
				"");
		p = new Produccion(1, new Date(112, 2, 2), "tipo", "dni", 54);
		m = new Medicamento(1, new Date(210, 3, 2), "tipo", "descripcion",
				"3333");

		ArrayList<Produccion> listaP = new ArrayList<Produccion>();
		listaP.add(p);

		u.añadirUsuario(json.toJson(u));
		v.añadirVaca(json.toJson(v));
		m.añadirMedicamento(json.toJson(m));
		p.setProducciones(json.toJson(listaP), "dni");

	}

	@After
	public void tearDown() throws Exception {
		u.eliminarUsuario("dni");
	}

	@Test
	public void testListaUsuarios() {
		ArrayList<Usuario> lista = json.fromJson(fachada.listaUsuarios(),
				new TypeToken<ArrayList<Usuario>>() {
				}.getType());
		assertNotSame(0, lista.size());
	}

	@Test
	public void testUsuario() {
		Usuario usuario = json.fromJson(fachada.Usuario("dni", "contraseña"),
				Usuario.class);
		assertEquals("nombre", usuario.getNombre());
	}

	@Test
	public void testUsuarioExistente() {
		assertTrue(fachada.usuarioExistente("dni", "contraseña"));
	}

	@Test
	public void testActualizarUsuario() {
		fachada.actualizarUsuario("dni", "sara", "martinez", "lopez",
				"direccion", "poblacion", 999999, "correo",
				"codigo_explotacion");
		Usuario usuario = json.fromJson(u.usuarioString("dni", "contraseña"),
				Usuario.class);
		assertEquals("sara", usuario.getNombre());
		assertEquals("martinez", usuario.getApellido1());
		assertEquals("lopez", usuario.getApellido2());

	}

	@Test
	public void testActualizarContraseña() {
		fachada.actualizarContraseña("dni", "contraseñaaa");
		Usuario usuario = json.fromJson(fachada.Usuario("dni", "contraseñaaa"),
				Usuario.class);
		assertEquals("contraseñaaa", usuario.getContraseña());
	}

	@Test
	public void testEliminarUsuario() {
		fachada.eliminarUsuario("dni");
		assertFalse(u.usuarioExistente("dni", "contraseña"));
	}

	@Test
	public void testAñadirUsuario() {
		Usuario usuario = new Usuario("nombre", "apellido1", "apellido2",
				"direccion", "poblacion", 9999999, "nuevo", "contraseña", 0,
				"correo", "codigo_explotacion");
		fachada.añadirUsuario(json.toJson(usuario));
		assertTrue(u.usuarioExistente("nuevo", "contraseña"));
	}

	@Test
	public void testListaVacas() {
		ArrayList<Vaca> lista = json.fromJson(fachada.listaVacas("dni"),
				new TypeToken<ArrayList<Vaca>>() {
				}.getType());
		assertEquals(1, lista.size());
	}

	@Test
	public void testVaca() {
		Vaca vaca = json.fromJson(fachada.Vaca("3333", "dni"), Vaca.class);
		assertEquals("3333", vaca.getId_vaca());
	}

	@Test
	public void testAñadirVaca() {
		Vaca vaca = new Vaca("3333", "raza", new Date(111, 1, 1), "345", "dni",
				"M", "");
		fachada.añadirVaca(json.toJson(vaca));
		ArrayList<Usuario> lista = json.fromJson(fachada.listaVacas("dni"),
				new TypeToken<ArrayList<Vaca>>() {
				}.getType());
		assertEquals(2, lista.size());
	}

	@Test
	public void testEliminarVaca() {
		fachada.eliminarVaca("3333", "dni");
		ArrayList<Vaca> lista = json.fromJson(fachada.listaVacas("dni"),
				new TypeToken<ArrayList<Vaca>>() {
				}.getType());
		assertEquals(1, lista.size());
		assertEquals("0", lista.get(0).getId_vaca());
	}

	@Test
	public void testEliminarVacas() {
		fachada.eliminarVacas("dni");
		ArrayList<Vaca> lista = json.fromJson(fachada.listaVacas("dni"),
				new TypeToken<ArrayList<Vaca>>() {
				}.getType());
		assertEquals(1, lista.size());
		assertEquals("0", lista.get(0).getId_vaca());
	}

	@Test
	public void testListaMedicamentos() {
		ArrayList<Medicamento> listaM = json.fromJson(
				fachada.listaMedicamentos("3333"),
				new TypeToken<ArrayList<Medicamento>>() {
				}.getType());
		assertEquals(1, listaM.size());
	}

	@Test
	public void testMedicamento() {
		Medicamento med = json.fromJson(fachada.Medicamento("3333", "1"),
				Medicamento.class);
		assertEquals(1, med.getId_medicamento());
		assertEquals("tipo", med.getTipo());
	}

	@Test
	public void testAñadirMedicamento() {
		Medicamento med = new Medicamento(2, new Date(111, 2, 1), "tipo",
				"descripcion", "3333");
		ArrayList<Medicamento> listaM = json.fromJson(
				fachada.listaMedicamentos("3333"),
				new TypeToken<ArrayList<Medicamento>>() {
				}.getType());
		assertEquals(2, listaM.size());
	}

	@Test
	public void testEliminarMedicamento() {
		fachada.eliminarMedicamento("1", "3333");
		ArrayList<Medicamento> listaM = json.fromJson(
				fachada.listaMedicamentos("3333"),
				new TypeToken<ArrayList<Medicamento>>() {
				}.getType());
		assertEquals(0, listaM.size());
	}

	@Test
	public void testEliminarMedicamentos() {
		fachada.eliminarMedicamentos("3333");
		ArrayList<Medicamento> listaM = json.fromJson(
				fachada.listaMedicamentos("3333"),
				new TypeToken<ArrayList<Medicamento>>() {
				}.getType());
		assertEquals(0, listaM.size());
	}

	@Test
	public void testGetProducciones() {
		ArrayList<Produccion> listaP = json.fromJson(
				fachada.getProducciones("dni"),
				new TypeToken<ArrayList<Produccion>>() {
				}.getType());
		assertEquals(1, listaP.size());
		assertEquals(1, listaP.get(0).getId_produccion());
	}

	@Test
	public void testSetProducciones() {
		ArrayList<Produccion> listaProducciones = new ArrayList<Produccion>();
		Produccion prod = new Produccion(2, new Date(122, 2, 2), "tipo", "dni",
				21);
		listaProducciones.add(prod);
		fachada.setProducciones(json.toJson(listaProducciones), "dni");
		ArrayList<Produccion> listaP = json.fromJson(
				fachada.getProducciones("dni"),
				new TypeToken<ArrayList<Produccion>>() {
				}.getType());
		assertEquals(2, listaP.size());
	}

}
