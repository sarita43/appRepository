package com.example.misvacasapp.aniadir;

import java.sql.Date;
import java.util.ArrayList;

import com.example.misvacasapp.LanzarVista;
import com.example.misvacasapp.MedicamentosVista;
import com.example.misvacasapp.R;
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
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Clase de la actividad de aniadir medicamento. En ella se implementan los
 * métodos que se utilizan para manejar la vista de añadir medicamento
 * 
 * @author Sara Martínez López
 * */
public class AniadirMedicamentoVista extends ActionBarActivity {
	// ----------------------------Atributos--------------------------------//
	/** Id del animal */
	private String id_vaca;
	/** Lista de medicamentos que tiene el animal */
	private ArrayList<Medicamento> listaMedicamentos;
	/**
	 * Lista desplegable que muestra los tipos de medicamentos o vacunas que
	 * puedes introducir
	 */
	private Spinner spinnerMedicamento;
	/** Base de datos interna de los medicamentos del animal */
	private MedicamentoDatosBbdd mdatos;

	// -----------------------------Métodos-----------------------------------//
	/**
	 * Rellena la lista de desplegable de los tipos medicamentos o vacunas
	 * */
	@SuppressWarnings("rawtypes")
	private void rellenarSpinner() {
		spinnerMedicamento = (Spinner) findViewById(R.id.tipo_medicamento_texto);
		ArrayAdapter adapter = ArrayAdapter.createFromResource(this,
				R.array.lista_medicamentos,
				android.R.layout.simple_spinner_dropdown_item);
		spinnerMedicamento.setAdapter(adapter);
	}

	/**
	 * Método que devuelve la lista de medicamentos de un animal de la base de
	 * datos interna, pasando como parámetro el id del animal
	 * 
	 * @param id_vaca
	 *            String id del animal
	 * @return ArrayList<Medicamento> Lista de medicamentos
	 */
	private ArrayList<Medicamento> getMedicamentos(String id_vaca) {
		return mdatos.getMedicamentos(id_vaca);
	}

	/**
	 * Método que añade el medicamento a la lista de medicamentos de un animal
	 * Si las comprobaciones que hace son correctas añade el medicamento
	 * 
	 * @param view
	 *            Vista
	 */
	public void nuevoMedicamento(View view) {

		if (comprobarFecha()) {
			Medicamento m = crearMedicamento();
			mdatos.añadirMedicamento(m);
			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					Toast.makeText(AniadirMedicamentoVista.this,
							"Añadido correctamente", Toast.LENGTH_SHORT).show();
					Intent i = new Intent(getApplicationContext(),
							MedicamentosVista.class);
					i.putExtra("id_vaca", id_vaca);
					startActivity(i);
					finish();
				}
			});
		}
	}

	/**
	 * Método que crea el medicamento con los valores introducidos por el
	 * usuario en la pantalla
	 * 
	 * @return Medicamento Nuevo medicamento
	 */
	private Medicamento crearMedicamento() {
		int dia = Integer
				.parseInt(((TextView) findViewById(R.id.fecha_medicamento_dia))
						.getText().toString());
		int mes = Integer
				.parseInt(((TextView) findViewById(R.id.fecha_medicamento_mes))
						.getText().toString()) - 1;
		int año = Integer
				.parseInt(((TextView) findViewById(R.id.fecha_medicamento_anio))
						.getText().toString()) - 1900;
		@SuppressWarnings("deprecation")
		Date fecha = new Date(año, mes, dia);
		String tipo = spinnerMedicamento.getSelectedItem().toString();
		String descripcion = ((TextView) findViewById(R.id.descripcion_medicamento_texto))
				.getText().toString();
		Medicamento medicamento = new Medicamento();
		medicamento = new Medicamento(crearIdMedicamento(), fecha, tipo,
				descripcion, id_vaca);
		return medicamento;
	}

	/**
	 * LLama a crear el id aleatorio y comprueba que ese id no exista. Si existe
	 * vuelve a crear otro id aleatorio
	 * */
	private int crearIdMedicamento() {
		int id = idAleatorio();
		for (int i = 0; listaMedicamentos.size() > i; i++) {
			if (listaMedicamentos.get(i).getId_medicamento() == id) {
				id = idAleatorio();
				i = 0;
			}
		}
		return id;
	}

	/**
	 * Crea un id aleatorio que puede ser el maximo valor que puede tener un
	 * entero
	 * 
	 * @return int Id aleatorio
	 * */
	private int idAleatorio() {
		return (int) Math.round(Math.random() * Integer.MAX_VALUE);
	}

	/**
	 * Método que comprueba si la fecha introducida por el usuario es correcta.
	 * Comprueba que este entre los valores de los dias posibles, los meses
	 * posibles y el año que no supere al año actual. También comprueba que se
	 * introduzca algun valor en la fecha
	 * 
	 * @return boolean Fecha correcta true fecha incorrecta false
	 */
	@SuppressWarnings("deprecation")
	private boolean comprobarFecha() {
		boolean fechaOk = false;
		if (((TextView) findViewById(R.id.fecha_medicamento_dia)).getText()
				.toString().equals("")
				|| ((TextView) findViewById(R.id.fecha_medicamento_mes))
						.getText().toString().equals("")
				|| ((TextView) findViewById(R.id.fecha_medicamento_anio))
						.getText().toString().equals("")) {
			fechaOk = false;
			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					Toast.makeText(AniadirMedicamentoVista.this,
							"Fecha incorrecta", Toast.LENGTH_SHORT).show();
				}
			});
		} else {
			int dia = Integer
					.parseInt(((TextView) findViewById(R.id.fecha_medicamento_dia))
							.getText().toString());
			int mes = Integer
					.parseInt(((TextView) findViewById(R.id.fecha_medicamento_mes))
							.getText().toString()) - 1;
			int año = Integer
					.parseInt(((TextView) findViewById(R.id.fecha_medicamento_anio))
							.getText().toString()) - 1900;
			int añoActual = new java.util.Date().getYear();
			int mesActual = new java.util.Date().getMonth();
			int diaActual = new java.util.Date().getDate();
			if (dia <= 31 && mes <= 12 && año < añoActual) {
				fechaOk = true;
			} else if (año == añoActual) {
				if (dia <= diaActual && mes == mesActual) {
					fechaOk = true;
				} else if (mes < mesActual && dia <= 31) {
					fechaOk = true;
				} else {
					fechaOk = false;
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							Toast.makeText(AniadirMedicamentoVista.this,
									"Fecha incorrecta", Toast.LENGTH_SHORT)
									.show();
						}
					});
				}
			} else {
				fechaOk = false;
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						Toast.makeText(AniadirMedicamentoVista.this,
								"Fecha incorrecta", Toast.LENGTH_SHORT).show();
					}
				});
			}
		}
		return fechaOk;
	}

	/**
	 * Método que sincroniza base de datos interna con la cloud. Deja la base de
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

				// Añadir vaca a base de datos cloud
				for (int i = 0; i < listaVacas.size(); i++) {
					String vaca = json.toJson(listaVacas.get(i));
					llamadaVaca.LLamadaAñadirVaca(vaca);
				}

				// Añadir medicamentos a base de datos cloud
				for (int i = 0; i < listaMedicamentosUsuario.size(); i++) {
					Medicamento m = listaMedicamentosUsuario.get(i);
					String medicamento = json.toJson(m);
					llamadaMedicamento.LLamadaAñadirMedicamento(medicamento);
				}
				
				//Añadir produccion a la base de datos cloud
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
	 * Añade la vista de añadir medicamento. Recoge el id de la vaca de la vista
	 * de la vaca. Inicializa parametros
	 * */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_aniadir_medicamento);
		mdatos = new MedicamentoDatosBbdd(getApplicationContext());

		Bundle bundle = getIntent().getExtras();
		id_vaca = bundle.getString("id_vaca");
		listaMedicamentos = getMedicamentos(id_vaca);
		rellenarSpinner();
	}

	/**
	 * Añade el menu a la vista aniadirVaca
	 * 
	 * @param menu
	 * */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_vaca, menu);
		return true;
	}

	/**
	 * Añade los item al menu
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
			editor.putString("contraseña", "");
			editor.commit();
			new LanzarVista(this).lanzarLogin();
			finish();
		} else if (id == R.id.sincronizar_cuenta) {
			sincronizar(settings.getString("id_usuario", ""));
		} else if (id == R.id.mis_vacas) {
			new LanzarVista(this).lanzarUsuarioVista(
					settings.getString("id_usuario", ""),
					settings.getString("contraseña", ""));
			finish();
			return true;
		} else if (id == R.id.administrar_cuenta) {
			new LanzarVista(this).lanzarAdministrarCuenta(
					settings.getString("id_usuario", ""),
					settings.getString("contraseña", ""));
			return true;
		} else if (id == R.id.ayuda) {
			AlertDialog.Builder alerta = new AlertDialog.Builder(this);
			alerta.setTitle("Ayuda");
			alerta.setMessage("Para añadir un nuevo animal rellene los campos."
					+ "\n"
					+ "Pincha sobre la imagen del logo para añadir una foto al animal."
					+ "\n"
					+ "Cuando haya rellenado todos los campos pulse en aceptar par añadir el animal");
			alerta.show();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}