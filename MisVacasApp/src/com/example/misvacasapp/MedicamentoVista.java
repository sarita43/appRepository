package com.example.misvacasapp;

import java.util.ArrayList;
import com.example.misvacasapp.adapter.AdapterMedicamento;
import com.example.misvacasapp.llamadaWS.LlamadaMedicamentoWS;
import com.example.misvacasapp.modelo.Medicamento;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class MedicamentoVista extends ActionBarActivity {

	private String idVaca;
	private ListView listaVista;
	private AdapterMedicamento adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lista_medicamentos_vista);

		Bundle bundle = getIntent().getExtras();
		idVaca = bundle.getString("id_vaca");

		listaVista = (ListView) findViewById(R.id.lista_medicamentos_vista);

		mostrarListado();
	}

	private void mostrarListado() {
		Thread hilo = new Thread() {
			String res = "";
			Gson json = new Gson();
			LlamadaMedicamentoWS llamada = new LlamadaMedicamentoWS();

			public void run() {

				res = llamada.LlamadaListaMedicamentos(idVaca);
				System.out.println(res);
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						ArrayList<Medicamento> lista = json.fromJson(res,
								new TypeToken<ArrayList<Medicamento>>() {
								}.getType());
						System.out.println(lista.size());
						setAdapter(lista);
					}
				});
			}
		};
		hilo.start();
	}

	private void setAdapter(ArrayList<Medicamento> lista) {
		adapter = new AdapterMedicamento(this, lista);
		listaVista.setAdapter(adapter);
		listaVista
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {

					}
				});
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
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
