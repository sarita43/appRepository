package com.example.misvacasapp.aniadir;

import java.sql.Date;
import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import com.example.misvacasapp.R;
import com.example.misvacasapp.UsuarioVista;
import com.example.misvacasapp.llamadaWS.LlamadaVacaWS;
import com.example.misvacasapp.modelo.Vaca;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class AniadirVacaVista extends ActionBarActivity {

	private String id_usuario;
	private ArrayList<Vaca> lista;
	private Gson json;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_aniadir_vaca);

		json = new GsonBuilder().setPrettyPrinting()
				.setDateFormat("dd-MM-yyyy").create();

		Bundle bundle = getIntent().getExtras();
		id_usuario = bundle.getString("id_usuario");
		lista = json.fromJson(bundle.getString("listaVacas"),
				new TypeToken<ArrayList<Vaca>>() {
				}.getType());

	}

	private String idVacaNuevo() {
		String id_vaca = ((TextView) findViewById(R.id.id_vaca_nuevo_texto))
				.getText().toString();
		for (int i = 0; lista.size() > i; i++) {
			if (id_vaca.equals(lista.get(i).getId_vaca())) {
				id_vaca = "";
				Toast.makeText(AniadirVacaVista.this, "Id ya existe",
						Toast.LENGTH_LONG).show();
			}
		}

		return id_vaca;
	}

	private Vaca crearVaca() {
		int dia = Integer.parseInt(((TextView) findViewById(R.id.dia_vaca))
				.getText().toString());
		int mes = Integer.parseInt(((TextView) findViewById(R.id.mes_vaca))
				.getText().toString()) - 1;
		int año = Integer.parseInt(((TextView) findViewById(R.id.anio_vaca))
				.getText().toString()) - 1900;
		@SuppressWarnings("deprecation")
		Date fecha = new Date(año, mes, dia);

		String raza = ((TextView) findViewById(R.id.raza_nuevo_texto))
				.getText().toString();
		String id_madre = ((TextView) findViewById(R.id.id_madre_nuevo_vaca))
				.getText().toString();

		Vaca vaca = new Vaca();
		vaca = new Vaca(idVacaNuevo(), raza, fecha, id_madre, "", id_madre);

		return vaca;
	}

	public void nuevaVaca(View view) {
		Thread hilo = new Thread() {
			LlamadaVacaWS llamada = new LlamadaVacaWS();

			public void run() {
				Vaca v = crearVaca();
				if (v.getId_vaca().equals("")) {

				} else {
					String m = json.toJson(v);
					llamada.LLamadaAñadirVaca(m);
				}
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
					}
				});
			}
		};
		hilo.start();
		Intent i = new Intent(this, UsuarioVista.class);
		i.putExtra("id_usuario", id_usuario);
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