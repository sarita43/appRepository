package com.example.misvacasapp.aniadir;

import java.sql.Date;
import java.util.ArrayList;

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
import android.widget.TextView;

/**
 * Clase de la actividad de aniadir medicamento En ella se implementan los métodos que se
 * utilizan para manejar la vista de añadir medicamento
 * 
 * @author Sara Martinez Lopez
 * */
public class AniadirMedicamentoVista extends ActionBarActivity {

	//Atributos
	/**Id del animal*/
	private String id_vaca;
	/**Lista de medicamentos que tiene el animal*/
	private ArrayList<Medicamento> lista;
	/** Tipo que serializa o deserializa para enviar a través del servicio web*/
	private Gson json;
	
	//Métodos
	
	/**
	 * Añade la vista de añadir medicamento. Recoge el id de la vaca de la vista de la vaca. Inicializa parametros
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

	}

	/**
	 * LLama a crear el id aleatorio y comprueba que ese id no exista
	 * Si existe vuelve a crear otro id aleatorio
	 * */
	private int crearIdMedicamento() {
		int id = idAleatorio();
		for (int i = 0; lista.size() > i; i++) {
			if (lista.get(i).getId_medicamento() == id) {
				id= idAleatorio();
				i = 0;
			}
		}
		return id;
	}
	
	/**
	 * Crea un id aleatorio que puede ser el maximo valor que puede tener un entero
	 * @see Integer.MAX_VALUE
	 * @return int Id aleatorio
	 * */
	private int idAleatorio() {
		return (int) Math.round(Math.random() * Integer.MAX_VALUE);
	}

	//FALTAN TODAS LAS COMPROBACIONES DE LOS DATOS INTRODUCIDOS
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
		String tipo = ((TextView) findViewById(R.id.tipo_medicamento_texto))
				.getText().toString();
		String descripcion = ((TextView) findViewById(R.id.descripcion_medicamento_texto))
				.getText().toString();

		Medicamento medicamento = new Medicamento();
		medicamento = new Medicamento(crearIdMedicamento(), fecha, tipo, descripcion,
				id_vaca);

		return medicamento;
	}

	public void nuevoMedicamento(View view) {
		Thread hilo = new Thread() {
			LlamadaMedicamentoWS llamada = new LlamadaMedicamentoWS();

			public void run() {
				String m = json.toJson(crearMedicamento());
				llamada.LLamadaAñadirMedicamento(m);
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
					}
				});
			}
		};
		hilo.start();
		Intent i = new Intent(this, MedicamentosVista.class);
		i.putExtra("id_vaca", id_vaca);
		startActivity(i);
		finish();
	}

	/**
	 * Añade el menu a la vista aniadirVaca
	 * 
	 * @param menu
	 * */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	/**
	 * Añade los item al menu
	 * 
	 * @param item
	 * */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.ayuda) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
