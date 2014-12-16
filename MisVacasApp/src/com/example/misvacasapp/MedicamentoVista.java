package com.example.misvacasapp;

import java.text.SimpleDateFormat;

import com.example.misvacasapp.llamadaWS.LlamadaMedicamentoWS;
import com.example.misvacasapp.modelo.Medicamento;
import com.google.gson.Gson;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MedicamentoVista extends ActionBarActivity{

	private String id_medicamento;
	private String id_vaca;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_medicamento);
		
		Bundle bundle = getIntent().getExtras();
		id_medicamento = Integer.toString(bundle.getInt("id_medicamento"));
		id_vaca = bundle.getString("id_vaca");
		
		rellenarCampos();
	}

	private void rellenarCampos(){
		Thread hilo = new Thread() {
			String res = "";
			Gson json = new Gson();
			LlamadaMedicamentoWS llamada = new LlamadaMedicamentoWS();
			SimpleDateFormat formatoDeFecha = new SimpleDateFormat("dd-MM-yyyy");
			Medicamento medicamento = new Medicamento();

			public void run() {
				res = llamada.LlamadaMedicamento(id_vaca, id_medicamento);
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						medicamento = json.fromJson(res, Medicamento.class);
						TextView idMedicamento = (TextView) findViewById(R.id.id_medicamento);
						idMedicamento.setText("ID MEDICAMENTO: " + medicamento.getId_medicamento());
						TextView fecha = (TextView) findViewById(R.id.fecha_medicamento);
						fecha.setText("FECHA: "
								+ formatoDeFecha.format(medicamento
										.getFecha()));
						TextView tipo = (TextView) findViewById(R.id.tipo_medicamento);
						tipo.setText("TIPO: " + medicamento.getTipo());
						
						TextView descripcion = (TextView) findViewById(R.id.descripcion);
						descripcion.setText("DESCRIPCION: " + medicamento.getDescripcion());
					}
				});
			}
		};
		hilo.start();
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
