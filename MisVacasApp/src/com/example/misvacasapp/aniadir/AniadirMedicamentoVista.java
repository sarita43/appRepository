package com.example.misvacasapp.aniadir;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;

import com.example.misvacasapp.MedicamentosVista;
import com.example.misvacasapp.R;
import com.example.misvacasapp.llamadaWS.LlamadaMedicamentoWS;
import com.example.misvacasapp.modelo.Medicamento;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import android.content.Intent;
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
 * Clase de la actividad de aniadir medicamento En ella se implementan los
 * m�todos que se utilizan para manejar la vista de a�adir medicamento
 * 
 * @author Sara Martinez Lopez
 * */
public class AniadirMedicamentoVista extends ActionBarActivity {

	// Atributos
	/** Id del animal */
	private String id_vaca;
	/** Lista de medicamentos que tiene el animal */
	private ArrayList<Medicamento> lista;
	/** Tipo que serializa o deserializa para enviar a trav�s del servicio web */
	private Gson json;
	/**
	 * Lista desplegable que muestra los tipos de medicamentos o vacunas que
	 * puedes introducir
	 */
	private Spinner spinnerMedicamento;

	// M�todos
	/**
	 * A�ade la vista de a�adir medicamento. Recoge el id de la vaca de la vista
	 * de la vaca. Inicializa parametros
	 * */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_aniadir_medicamento);
		json = new GsonBuilder().setPrettyPrinting()
				.setDateFormat("dd-MM-yyyy").create();
		Bundle bundle = getIntent().getExtras();
		id_vaca = bundle.getString("id_vaca");
		lista = json.fromJson(bundle.getString("listaMedicamentos"),
				new TypeToken<ArrayList<Medicamento>>() {
				}.getType());
		rellenarSpinner();
	}

	/**
	 * Rellena la lista de desplegable de los tipos medicamentos o vacunas
	 * 
	 * @see onCreate
	 * */
	private void rellenarSpinner() {
		spinnerMedicamento = (Spinner) findViewById(R.id.tipo_medicamento_texto);
		ArrayList<String> listaMedicamentos = new ArrayList<String>(
				Arrays.asList("Brucelosis", "Leptospirosis", "Rinotraqueitis",
						"Parainfluenza 3 (PI 3)", "Diarrea viral bovina (DVB)",
						"Analg�sico", "Antibi�tico", " Desparasitante",
						" Diur�tico", "Expectorante",
						"Anab�licos y Hormonales", " Miscel�neos",
						" Vitaminas", "Otros"));
		ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
				android.R.layout.simple_spinner_dropdown_item,
				listaMedicamentos);
		spinnerMedicamento.setAdapter(adapter);
	}

	/**
	 * LLama a crear el id aleatorio y comprueba que ese id no exista Si existe
	 * vuelve a crear otro id aleatorio
	 * */
	private int crearIdMedicamento() {
		int id = idAleatorio();
		for (int i = 0; lista.size() > i; i++) {
			if (lista.get(i).getId_medicamento() == id) {
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
	 * @see Integer.MAX_VALUE
	 * @return int Id aleatorio
	 * */
	private int idAleatorio() {
		return (int) Math.round(Math.random() * Integer.MAX_VALUE);
	}

	/**
	 * M�todo que crea el medicamento con los valores introducidos por el
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
		int a�o = Integer
				.parseInt(((TextView) findViewById(R.id.fecha_medicamento_anio))
						.getText().toString()) - 1900;
		@SuppressWarnings("deprecation")
		Date fecha = new Date(a�o, mes, dia);
		String tipo = spinnerMedicamento.getSelectedItem().toString();
		String descripcion = ((TextView) findViewById(R.id.descripcion_medicamento_texto))
				.getText().toString()+" ";
		Medicamento medicamento = new Medicamento();
		medicamento = new Medicamento(crearIdMedicamento(), fecha, tipo,
				descripcion, id_vaca);
		return medicamento;
	}

	/**
	 * M�todo que comprueba si la fecha introducida por el usuario es correcta.
	 * Comprueba que este entre los valores de los dias posibles, los meses
	 * posibles y el a�o que no supere al a�o actual. Tambi�n comprueba que se
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
							.getText().toString())-1;
			int a�o = Integer
					.parseInt(((TextView) findViewById(R.id.fecha_medicamento_anio))
							.getText().toString())-1900;
			int a�oActual=new java.util.Date().getYear();
			int mesActual=new java.util.Date().getMonth();
			int diaActual=new java.util.Date().getDate();
			if (dia <= 31 && mes <= 12 && a�o < a�oActual) {
				fechaOk = true;
				
			} else if (a�o == a�oActual) {
				if (dia <= diaActual && mes<= mesActual) {
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
	 * M�todo que a�ade el medicamento a la lista de medicamentos de un animal
	 * Si las comprobaciones que hace son correctas a�ade el medicamento
	 * 
	 * @param view
	 *            Vista
	 */
	public void nuevoMedicamento(View view) {
		Thread hilo = new Thread() {
			LlamadaMedicamentoWS llamada = new LlamadaMedicamentoWS();

			public void run() {
				if (comprobarFecha()) {
					String m = json.toJson(crearMedicamento());
					llamada.LLamadaA�adirMedicamento(m);
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							Toast.makeText(AniadirMedicamentoVista.this,
									"A�adido correctamente", Toast.LENGTH_SHORT)
									.show();
							Intent i = new Intent(getApplicationContext(),
									MedicamentosVista.class);
							i.putExtra("id_vaca", id_vaca);
							startActivity(i);
							finish();
						}
					});
				}
			}
		};
		hilo.start();

	}

	/**
	 * A�ade el menu a la vista aniadirVaca
	 * 
	 * @param menu
	 * */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.login, menu);
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
		if (id == R.id.ayuda) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}