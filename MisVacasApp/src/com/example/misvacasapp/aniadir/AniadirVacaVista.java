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
		lista = new ArrayList<Vaca>();
		listaVacas();
	}

	private void listaVacas() {
		Thread hilo = new Thread() {
			String res = "";
			Gson json = new GsonBuilder().setPrettyPrinting()
					.setDateFormat("dd-MM-yyyy").create();
			LlamadaVacaWS llamada = new LlamadaVacaWS();

			public void run() {

				res = llamada.LlamadaListaVacas(id_usuario);
				lista = json.fromJson(res, new TypeToken<ArrayList<Vaca>>() {
				}.getType());

			}
		};
		hilo.start();
	}

	private boolean comprobarIdVaca() {
		boolean id_correcto = true;
		String id_vaca = ((TextView) findViewById(R.id.id_vaca_nuevo_texto))
				.getText().toString();

		for (int i = 0; lista.size() > i; i++) {
			if (id_vaca.equals(lista.get(i).getId_vaca())) {
				id_correcto = false;
				runOnUiThread(new Runnable() {
					@Override
					public void run() {

						Toast.makeText(AniadirVacaVista.this, "Id ya existe",
								Toast.LENGTH_SHORT).show();
						Intent i = new Intent(getApplicationContext(),
								AniadirVacaVista.class);
						i.putExtra("id_usuario", id_usuario);
						startActivity(i);
						finish();
					}
				});
			} else if (id_vaca.equals("")) {
				id_correcto = false;
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						Toast.makeText(AniadirVacaVista.this, "Id vacio",
								Toast.LENGTH_SHORT).show();
						Intent i = new Intent(getApplicationContext(),
								AniadirVacaVista.class);
						i.putExtra("id_usuario", id_usuario);
						startActivity(i);
						finish();
					}
				});
			}
		}
		return id_correcto;
	}

	private Vaca crearVaca() {
		Vaca vaca = new Vaca();
		if (((TextView) findViewById(R.id.dia_vaca)).getText().toString()
				.equals("")) {
			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					Toast.makeText(AniadirVacaVista.this, "Fecha incorrecta",
							Toast.LENGTH_SHORT).show();
				}
			});
			Intent i = new Intent(this, AniadirVacaVista.class);
			i.putExtra("id_usuario", id_usuario);
			startActivity(i);
			finish();
		} else {
			int dia = Integer.parseInt(((TextView) findViewById(R.id.dia_vaca))
					.getText().toString());
			int mes = Integer.parseInt(((TextView) findViewById(R.id.mes_vaca))
					.getText().toString()) - 1;
			int año = Integer
					.parseInt(((TextView) findViewById(R.id.anio_vaca))
							.getText().toString()) - 1900;
			@SuppressWarnings("deprecation")
			Date fecha = new Date(año, mes, dia);
			String id_vaca = (((TextView) findViewById(R.id.id_vaca_nuevo_texto))
					.getText().toString());
			String raza = ((TextView) findViewById(R.id.raza_nuevo_texto))
					.getText().toString();
			String id_madre = ((TextView) findViewById(R.id.id_madre_nuevo_vaca))
					.getText().toString();
			String sexo = ((TextView) findViewById(R.id.sexo_nuevo_vaca))
					.getText().toString();
			vaca = new Vaca(id_vaca, raza, fecha, id_madre, id_usuario, sexo, null);	
		}
		return vaca;
	}

	public void nuevaVaca(View view) {
		Thread hilo = new Thread() {
			LlamadaVacaWS llamada = new LlamadaVacaWS();

			public void run() {
				if (comprobarIdVaca()) {
					final Vaca v = crearVaca();
					if (((TextView) findViewById(R.id.dia_vaca)).getText()
							.toString().equals("")) {
					} else if (v.getSexo().equals("M")
							|| v.getSexo().equals("H")) {
						String vaca = json.toJson(v);
						llamada.LLamadaAñadirVaca(vaca);
						System.out.println(vaca);
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								Toast.makeText(AniadirVacaVista.this,
										"Añadido correctamente",
										Toast.LENGTH_SHORT).show();
								Intent i = new Intent(getApplicationContext(),
										UsuarioVista.class);
								i.putExtra("id_usuario", id_usuario);
								startActivity(i);
								finish();
							}
						});
					} else {
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								Toast.makeText(AniadirVacaVista.this,
										"Mal sexo. M o H", Toast.LENGTH_SHORT)
										.show();

								Intent i = new Intent(getApplicationContext(),
										AniadirVacaVista.class);
								i.putExtra("id_usuario", id_usuario);
								startActivity(i);
								finish();
							}
						});
					}
				}

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
		if (id == R.id.ayuda) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}