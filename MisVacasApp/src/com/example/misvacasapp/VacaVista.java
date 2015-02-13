package com.example.misvacasapp;

import com.example.misvacasapp.llamadaWS.LlamadaVacaWS;
import com.example.misvacasapp.modelo.Vaca;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

/**
 * Clase de la actividad de la vista de la vaca En ella se implementan los
 * métodos que se utilizan para la vista de la vaca
 * 
 * @author Sara Martinez Lopez
 * */
public class VacaVista extends ActionBarActivity {
	// Atributos
	/** Id de la vaca */
	private String id_vaca;
	/** Id delusuario */
	private String id_usuario;

	// Métodos
	/**
	 * Añade la vista de la vaca
	 * 
	 * @param savedInstanceState
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_vaca_vista);
		Bundle bundle = getIntent().getExtras();
		id_vaca = bundle.getString("id_vaca");
		id_usuario = bundle.getString("id_usuario");
		rellenarCamposVaca();
	}

	/**
	 * Método que se ejecuta al hacer click en el botón de los medicamentos
	 * Cambia a la vista de medicamentos de la vaca
	 * 
	 * @param v
	 * */
	public void onClickMedicamentos(View v) {
		Intent i = new Intent(this, MedicamentosVista.class);
		i.putExtra("id_vaca", id_vaca);
		startActivity(i);
	}

	/**
	 * Rellena los campos de la vaca que se ha seleccionado anteriormente Llama
	 * al web service para recoger los datos
	 * 
	 * @see onCreate
	 * */
	private void rellenarCamposVaca() {
		Thread hilo = new Thread() {
			String res = "";
			Gson json = new GsonBuilder().setPrettyPrinting()
					.setDateFormat("dd-MM-yyyy").create();
			LlamadaVacaWS llamada = new LlamadaVacaWS();
			Vaca vaca = new Vaca();

			public void run() {
				res = llamada.LlamadaVaca(id_vaca, id_usuario);
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						vaca = json.fromJson(res, Vaca.class);
						TextView idVaca = (TextView) findViewById(R.id.idVaca);
						idVaca.setText("ID VACA: " + vaca.getId_vaca());
						TextView raza = (TextView) findViewById(R.id.raza);
						raza.setText("RAZA: " + vaca.getRaza());
						TextView fechaNacimiento = (TextView) findViewById(R.id.fechaNacimiento);
						fechaNacimiento.setText("FECHA DE NACIMIENTO: "
								+ vaca.getFecha_nacimiento());
						TextView idMadre = (TextView) findViewById(R.id.idMadre);
						idMadre.setText("ID MADRE: " + vaca.getId_madre());
						// ImageView imagen =
						// (ImageView)findViewById(R.id.imageView1);
						// imagen.setImageDrawable(vaca.getId_vaca());
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