package com.example.misvacasapp;

import java.util.ArrayList;

import com.example.misvacasapp.bbddinterna.MedicamentoDatosBbdd;
import com.example.misvacasapp.bbddinterna.ProduccionDatosBbdd;
import com.example.misvacasapp.bbddinterna.VacaDatosBbdd;
import com.example.misvacasapp.llamadaWS.LlamadaMedicamentoWS;
import com.example.misvacasapp.llamadaWS.LlamadaProduccionWS;
import com.example.misvacasapp.llamadaWS.LlamadaVacaWS;
import com.example.misvacasapp.modelo.Medicamento;
import com.example.misvacasapp.modelo.Produccion;
import com.example.misvacasapp.modelo.Vaca;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Clase de la actividad de la vista de la vaca. En ella se implementan los
 * m�todos que se utilizan para la vista de la vaca
 * 
 * @author Sara Mart�nez L�pez
 * */
public class VacaVista extends ActionBarActivity {
	// ---------------------Atributos-------------------------------//
	/** Id de la vaca */
	private String id_vaca;
	/** Base de datos interna de los animales */
	private VacaDatosBbdd vdatos;

	// ----------------------M�todos--------------------------------//
	/**
	 * Rellena los campos de la vaca que se ha seleccionado anteriormente.
	 * Recoge los datos de la base de datos interna
	 * */
	@SuppressWarnings("deprecation")
	private void rellenarCamposVaca() {

		Vaca vaca = vdatos.getVaca(id_vaca);

		// Divisi�n del id del animal en 2 xxxxxxxxxx-xxxx
		TextView preidVaca = (TextView) findViewById(R.id.preidVaca);
		preidVaca.setText(vaca.getId_vaca().substring(0,
				vaca.getId_vaca().length() - 4));
		TextView idVaca = (TextView) findViewById(R.id.idVaca);
		idVaca.setText(vaca.getId_vaca().substring(
				vaca.getId_vaca().length() - 4, vaca.getId_vaca().length()));
		TextView raza = (TextView) findViewById(R.id.raza);
		raza.setText("RAZA: " + vaca.getRaza());
		TextView sexo = (TextView) findViewById(R.id.sexo);
		sexo.setText("SEXO: " + vaca.getSexo());
		TextView fechaNacimiento = (TextView) findViewById(R.id.fechaNacimiento);
		fechaNacimiento.setText("FECHA DE NACIMIENTO: "
				+ vaca.getFecha_nacimiento());
		TextView idMadre = (TextView) findViewById(R.id.idMadre);
		idMadre.setText("ID MADRE: " + vaca.getId_madre());
		Button imagen = (Button) findViewById(R.id.imageView1);
		byte[] decodedString = Base64.decode(vaca.getFoto(), Base64.DEFAULT);
		Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0,
				decodedString.length);
		Drawable i = new BitmapDrawable(decodedByte);
		imagen.setBackground(i);
	}

	/**
	 * Crea la vista para seleccionar una foto y cambiar la foto del animal
	 * 
	 * @param v
	 *            Vista
	 */
	public void cargarFoto(View v) {
		new LanzarVista(this).lanzarCambiarFoto(id_vaca);
	}

	/**
	 * M�todo que se ejecuta al hacer click en el bot�n de los medicamentos.
	 * Cambia a la vista de medicamentos de la vaca
	 * 
	 * @param v
	 * */
	public void onClickMedicamentos(View v) {
		new LanzarVista(this).lanzarMedicamentos(id_vaca);
	}

	/**
	 * M�todo que sincroniza base de datos interna con la cloud. Deja la base de
	 * datos cloud como la base de datos interna.
	 * 
	 * @param usuario
	 *            String id_usuario
	 */
	public void sincronizar(final String usuario) {
		Thread hilo = new Thread() {
			public void run() {
				//Base de datos interna
				VacaDatosBbdd vdatos = new VacaDatosBbdd(
						getApplicationContext());
				ArrayList<Vaca> listaVacas = new ArrayList<Vaca>();
				listaVacas = vdatos.getListaVacas(usuario);

				MedicamentoDatosBbdd mdatos = new MedicamentoDatosBbdd(
						getApplicationContext());
				ArrayList<Medicamento> listaMedicamentosUsuario = new ArrayList<Medicamento>();

				ProduccionDatosBbdd pdbbdd = new ProduccionDatosBbdd(getApplicationContext());
				ArrayList<Produccion> listaProduccion = new ArrayList<Produccion>();
				
				
				Gson json = new GsonBuilder().setPrettyPrinting()
						.setDateFormat("dd-MM-yyyy").create();
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						Toast.makeText(
								getApplicationContext(),
								"La cuenta esta siendo sincronizada. Esto puede tardar unos minutos",
								Toast.LENGTH_LONG).show();
					}
				});
				
				for (int i = 0; i < listaVacas.size(); i++) {
					// Guarda los medicamentos en una lista
					ArrayList<Medicamento> listaAux = mdatos
							.getMedicamentos(listaVacas.get(i).getId_vaca());
					for (int j = 0; j < listaAux.size(); j++) {
						listaMedicamentosUsuario.add(listaAux.get(j));
					}
				}

				// Eliminar vaca base de datos cloud
				LlamadaVacaWS llamadaVaca = new LlamadaVacaWS();
				LlamadaMedicamentoWS llamadaMedicamento = new LlamadaMedicamentoWS();
				llamadaVaca.LLamadaEliminarVacas(usuario);

				// A�adir vaca a base de datos cloud
				for (int i = 0; i < listaVacas.size(); i++) {
					String vaca = json.toJson(listaVacas.get(i));
					llamadaVaca.LLamadaA�adirVaca(vaca);
				}

				// A�adir medicamentos a base de datos cloud
				for (int i = 0; i < listaMedicamentosUsuario.size(); i++) {
					Medicamento m = listaMedicamentosUsuario.get(i);
					String medicamento = json.toJson(m);
					llamadaMedicamento.LLamadaA�adirMedicamento(medicamento);
				}
				
				//A�adir produccion a la base de datos cloud
				listaProduccion = pdbbdd.getProducciones();
				LlamadaProduccionWS llamadaProducciones = new LlamadaProduccionWS();
				llamadaProducciones.setProducciones(json.toJson(listaProduccion),usuario);
				
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						Toast.makeText(getApplicationContext(),
								"La cuenta ha siendo sincronizada",
								Toast.LENGTH_LONG).show();
					}
				});
			}
		};
		hilo.start();
	}

	/**
	 * A�ade la vista de la vaca. Inicializa atributos
	 * 
	 * @param savedInstanceState
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_vaca_vista);
		Bundle bundle = getIntent().getExtras();
		id_vaca = bundle.getString("id_vaca");
		vdatos = new VacaDatosBbdd(getApplicationContext());
		rellenarCamposVaca();
	}

	/**
	 * A�ade el menu a la vista vaca
	 * 
	 * @param menu
	 * */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_vaca, menu);
		return true;
	}

	/**
	 * A�ade los item al menu
	 * 
	 * @param item
	 * */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		SharedPreferences settings = getSharedPreferences("MisDatos",
				Context.MODE_PRIVATE);
		if (id == R.id.cerrar_sesion) {
			SharedPreferences.Editor editor = settings.edit();
			editor.putString("id_usuario", "");
			editor.putString("contrase�a", "");
			editor.commit();
			new LanzarVista(this).lanzarLogin();
			finish();
		} else if (id == R.id.produccion) {
			new LanzarVista(this).lanzarProduccion();
			finish();
			return true;
		} else if (id == R.id.mis_vacas) {
			String id_usuarioGuardado = settings.getString("id_usuario", "");
			String contrase�aGuardado = settings.getString("contrase�a", "");
			new LanzarVista(this).lanzarUsuarioVista(id_usuarioGuardado,
					contrase�aGuardado);
			finish();
			return true;
		} else if (id == R.id.administrar_cuenta) {
			String id_usuarioGuardado = settings.getString("id_usuario", "");
			String contrase�aGuardado = settings.getString("contrase�a", "");
			new LanzarVista(this).lanzarAdministrarCuenta(id_usuarioGuardado,
					contrase�aGuardado);
			return true;
		} else if (id == R.id.sincronizar_cuenta) {
			sincronizar(settings.getString("id_usuario", ""));
		} else if (id == R.id.ayuda) {
			AlertDialog.Builder alerta = new AlertDialog.Builder(this);
			alerta.setTitle("Ayuda");
			alerta.setMessage("Ficha del animal."
					+ "\n\n"
					+ "Para ver sus medicamentos, pulse sobre el bot�n de medicamentos."
					+ "\n\n"
					+ "Para cambiar la foto del animal pulsa sobre la foto, busca en la galeria " +
					"la nueva foto y guarda los cambios");
			alerta.show();
			return true;
		}
		return super.onOptionsItemSelected(item);

	}
}