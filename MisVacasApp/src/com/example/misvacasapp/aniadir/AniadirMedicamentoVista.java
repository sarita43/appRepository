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

public class AniadirMedicamentoVista extends ActionBarActivity {

	private String id_vaca;
	private ArrayList<Medicamento> lista;
	private Gson json;

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

	private int idAleatorio() {
		return (int) Math.round(Math.random() * Integer.MAX_VALUE);
	}

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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

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
