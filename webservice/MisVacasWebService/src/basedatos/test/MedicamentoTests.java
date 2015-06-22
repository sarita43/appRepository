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

import basedatos.Medicamento;
import basedatos.Usuario;
import basedatos.Vaca;

public class MedicamentoTests {

	private Medicamento m;
	private Usuario u;
	private Gson json;

	@Before
	public void setUp() throws Exception {
		json = new GsonBuilder().setPrettyPrinting()
				.setDateFormat("dd-MM-yyyy").create();

		u = new Usuario("nombre", "apellido1", "apellido2",
				"direccion", "poblacion", 9999999, "dni", "contraseña", 0,
				"correo", "codigo_explotacion");
		Vaca v = new Vaca("3333", "raza", new Date(212, 3, 1), "1111", "dni",
				"M", "");
		m = new Medicamento(1, new Date(210, 3, 2), "tipo", "descripcion",
				"3333");

		u.añadirUsuario(json.toJson(u));
		v.añadirVaca(json.toJson(v));
		m.añadirMedicamento(json.toJson(m));
	}
	
	@After
	public void tearDown() throws Exception {
		u.eliminarUsuario("dni");
	}

	@Test
	public void testListaMedicamentoString() {
		ArrayList<Medicamento> listaM = json.fromJson(
				m.listaMedicamentoString("3333"),
				new TypeToken<ArrayList<Medicamento>>() {
				}.getType());
		assertEquals(1, listaM.size());
	}

	@Test
	public void testMedicamentoString() {
		Medicamento med = json.fromJson(m.medicamentoString("3333", "1"), Medicamento.class);
		assertEquals(1, med.getId_medicamento());
		assertEquals("tipo", med.getTipo());
	}

	@Test
	public void testEliminarMedicamento() {
		m.eliminarMedicamento("1", "3333");
		ArrayList<Medicamento> listaM = json.fromJson(
				m.listaMedicamentoString("3333"),
				new TypeToken<ArrayList<Medicamento>>() {
				}.getType());
		assertEquals(0, listaM.size());
	}

	@Test
	public void testEliminarMedicamentos() {
		m.eliminarMedicamentos("3333");
		ArrayList<Medicamento> listaM = json.fromJson(
				m.listaMedicamentoString("3333"),
				new TypeToken<ArrayList<Medicamento>>() {
				}.getType());
		assertEquals(0, listaM.size());
	}

	@Test
	public void testAñadirMedicamento() {
		Medicamento med = new Medicamento(2, new Date(111, 2, 1), "tipo", "descripcion", "3333");
		ArrayList<Medicamento> listaM = json.fromJson(
				m.listaMedicamentoString("3333"),
				new TypeToken<ArrayList<Medicamento>>() {
				}.getType());
		assertEquals(2, listaM.size());
	}

	@Test
	public void testGetId_medicamento() {
		assertEquals(1, m.getId_medicamento());
	}

	@Test
	public void testSetId_medicamento() {
		m.setId_medicamento(3);
		assertEquals(3, m.getId_medicamento());
	}

	@Test
	public void testGetFecha() {
		assertEquals(new Date(210, 3, 2), m.getFecha());
	}

	@Test
	public void testSetFecha() {
		m.setFecha(new Date(222, 1, 1));
		assertEquals(new Date(222, 1, 1), m.getFecha());
	}

	@Test
	public void testGetTipo() {
		assertEquals("tipo", m.getTipo());
	}

	@Test
	public void testSetTipo() {
		m.setTipo("brucelosis");
		assertEquals("brucelosis", m.getTipo());
	}

	@Test
	public void testGetId_vaca() {
		assertEquals("3333", m.getId_vaca());
	}

	@Test
	public void testSetId_vaca() {
		m.setId_vaca("2222");
		assertEquals("2222", m.getId_vaca());
	}

	@Test
	public void testGetDescripcion() {
		assertEquals("descripcion", m.getDescripcion());
	}

	@Test
	public void testSetDescripcion() {
		m.setDescripcion("Vacuna");
		assertEquals("Vacuna", m.getDescripcion());
	}

}
