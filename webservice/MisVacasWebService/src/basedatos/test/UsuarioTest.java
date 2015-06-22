package basedatos.test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import jdk.nashorn.internal.scripts.JS;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import basedatos.Usuario;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class UsuarioTest {

	private Usuario u;
	private Gson json;

	@Before
	public void setUp() throws Exception {
		json = new GsonBuilder().setPrettyPrinting()
				.setDateFormat("dd-MM-yyyy").create();

		u = new Usuario("nombre", "apellido1", "apellido2", "direccion",
				"poblacion", 9999999, "dni", "contraseña", 0, "correo",
				"codigo_explotacion");
		u.añadirUsuario(json.toJson(u));
	}

	@After
	public void tearDown() throws Exception {
		u.eliminarUsuario("dni");
	}

	@Test
	public void testListaUsuariosString() {
		ArrayList<Usuario> lista = json.fromJson(u.listaUsuariosString(),
				new TypeToken<ArrayList<Usuario>>() {
				}.getType());
		assertNotSame(0, lista.size());
	}

	@Test
	public void testUsuarioString() {
		Usuario usuario = json.fromJson(u.usuarioString("dni", "contraseña"),
				Usuario.class);
		assertEquals("nombre", usuario.getNombre());
	}

	@Test
	public void testUsuarioExistente() {
		assertTrue(u.usuarioExistente("dni", "contraseña"));
	}

	@Test
	public void testActualizarUsuario() {
		u.actualizarUsuario("dni", "sara", "martinez", "lopez", "direccion",
				"poblacion", 999999, "correo", "codigo_explotacion");
		Usuario usuario = json.fromJson(u.usuarioString("dni", "contraseña"),
				Usuario.class);
		assertEquals("sara", usuario.getNombre());
		assertEquals("martinez", usuario.getApellido1());
		assertEquals("lopez", usuario.getApellido2());
	}

	@Test
	public void testActualizarContraseña() {
		u.actualizarContraseña("dni", "contraseñaaa");
		Usuario usuario = json.fromJson(u.usuarioString("dni", "contraseñaaa"),
				Usuario.class);
		assertEquals("contraseñaaa", usuario.getContraseña());
	}

	@Test
	public void testEliminarUsuario() {
		u.eliminarUsuario("dni");
		assertFalse( u.usuarioExistente("dni", "contraseña"));
	}

	@Test
	public void testAñadirUsuario() {
		Usuario usuario = new Usuario("nombre", "apellido1", "apellido2",
				"direccion", "poblacion", 9999999, "nuevo", "contraseña", 0,
				"correo", "codigo_explotacion");
		u.añadirUsuario(json.toJson(usuario));
		assertTrue(u.usuarioExistente("nuevo", "contraseña"));
	}

	@Test
	public void testGetNombre() {
		assertEquals("nombre", u.getNombre());
	}

	@Test
	public void testSetNombre() {
		u.setNombre("sara");
		assertEquals("sara", u.getNombre());
	}

	@Test
	public void testGetApellido1() {
		assertEquals("apellido1", u.getApellido1());
	}

	@Test
	public void testSetApellido1() {
		u.setApellido1("martinez");
		assertEquals("martinez", u.getApellido1());
	}

	@Test
	public void testGetApellido2() {
		assertEquals("apellido2", u.getApellido2());
	}

	@Test
	public void testSetApellido2() {
		u.setApellido2("lopez");
		assertEquals("lopez", u.getApellido2());
	}

	@Test
	public void testGetDireccion() {
		assertEquals("direccion", u.getDireccion());
	}

	@Test
	public void testSetDireccion() {
		u.setDireccion("calle");
		assertEquals("calle", u.getDireccion());
	}

	@Test
	public void testGetPoblacion() {
		assertEquals(u.getPoblacion(), "poblacion");
	}

	@Test
	public void testSetPoblacion() {
		u.setPoblacion("LEON");
		assertEquals(u.getPoblacion(), "LEON");
	}

	@Test
	public void testGetTelefono() {
		assertEquals(u.getTelefono(), 9999999);
	}

	@Test
	public void testSetTelefono() {
		u.setTelefono(987121212);
		assertEquals(u.getTelefono(), 987121212);
	}

	@Test
	public void testGetDni() {
		assertEquals(u.getDni(), "dni");
	}

	@Test
	public void testSetDni() {
		u.setDni("12121212X");
		assertEquals(u.getDni(), "12121212X");
	}

	@Test
	public void testGetContraseña() {
		assertEquals(u.getContraseña(), "contraseña");
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
		assertEquals(u.getCorreo(), "correo");
	}

	@Test
	public void testSetCorreo() {
		u.setCorreo("correo@hotmail.com");
		assertEquals(u.getCorreo(), "correo@hotmail.com");
	}

	@Test
	public void testGetCodigo_explotacion() {
		assertEquals(u.getCodigo_explotacion(), "codigo_explotacion");
	}

	@Test
	public void testSetCodigo_explotacion() {
		u.setCodigo_explotacion("ES121212");
		assertEquals(u.getCodigo_explotacion(), "ES121212");
	}

}
