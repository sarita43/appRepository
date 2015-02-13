package com.example.misvacasapp;

import com.example.misvacasapp.llamadaWS.LlamadaMedicamentoWS;
import com.example.misvacasapp.modelo.Medicamento;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

/**
 * Clase de la actividad del medicamento En ella se implementan los métodos que se
 * utilizan para manejar la vista del medicamento
 * 
 * @author Sara Martinez Lopez
 * */
public class MedicamentoVista extends ActionBarActivity{

	//Atributos
	/**Id del medicamento*/
	private String id_medicamento;
	/**Id de la vaca*/
	private String id_vaca;
	
	//Metodos
	/**
	 * Añade la vista del medicamento 
	 * Recoge el id del medicamento y el id de la vaca de la vista medicamento
	 * Inicializa parametros
	 * */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_medicamento);
		
		Bundle bundle = getIntent().getExtras();
		id_medicamento = Integer.toString(bundle.getInt("id_medicamento"));
		id_vaca = bundle.getString("id_vaca");
		
		rellenarCampos();
	}

	/**
	 * Rellena los campos de la vista del medicamento
	 * Llama al servicio web para recoger los campos
	 * @see onCreate
	 * */
	private void rellenarCampos(){
		Thread hilo = new Thread() {
			String res = "";
			Gson json = new GsonBuilder().setPrettyPrinting().setDateFormat("dd-MM-yyyy").create();
			LlamadaMedicamentoWS llamada = new LlamadaMedicamentoWS();
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
						fecha.setText("FECHA: "+medicamento.getFecha());
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
	
	/**
	 * Añade el menu a la vista login
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
