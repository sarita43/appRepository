package com.example.misvacasapp;

import java.text.SimpleDateFormat;
import com.example.misvacasapp.llamadaWS.LlamadaVacaWS;
import com.example.misvacasapp.modelo.Vaca;
import com.google.gson.Gson;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

public class VacaVista extends ActionBarActivity {
	private String id_vaca;
	private String id_usuario;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_vaca_vista);
		Bundle bundle = getIntent().getExtras();
		id_vaca = bundle.getString("id_vaca");
		id_usuario = bundle.getString("id_usuario");
		rellenarCamposVaca();
	}

	private void rellenarCamposVaca() {
		Thread hilo = new Thread() {
			String res = "";
			Gson json = new Gson();
			LlamadaVacaWS llamada = new LlamadaVacaWS();
			SimpleDateFormat formatoDeFecha = new SimpleDateFormat("dd-MM-yyyy");
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
								+ formatoDeFecha.format(vaca
										.getFecha_nacimiento()));
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