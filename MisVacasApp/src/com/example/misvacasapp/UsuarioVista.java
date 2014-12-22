package com.example.misvacasapp;

import java.util.ArrayList;

import com.example.misvacasapp.R;
import com.example.misvacasapp.adapter.AdapterVaca;
import com.example.misvacasapp.llamadaWS.LlamadaVacaWS;
import com.example.misvacasapp.menus.AdministrarCuentaVista;
import com.example.misvacasapp.modelo.Vaca;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class UsuarioVista extends ActionBarActivity {

	private String id_usuario;
	private String contraseña;
	private ListView listaVista;
	private AdapterVaca adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_usuario_vista);

		Bundle bundle = getIntent().getExtras();
		id_usuario = bundle.getString("id_usuario");
		contraseña = bundle.getString("contraseña");

		listaVista = (ListView) findViewById(R.id.lista_usuario_vista);

		mostrarListado();
	}

	private void mostrarListado() {
		Thread hilo = new Thread() {
			String res = "";
			Gson json = new Gson();
			LlamadaVacaWS llamada = new LlamadaVacaWS();

			public void run() {

				res = llamada.LlamadaListaVacas(id_usuario);

				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						ArrayList<Vaca> lista = json.fromJson(res,
								new TypeToken<ArrayList<Vaca>>() {
								}.getType());
						setAdapter(lista);
					}
				});
			}
		};
		hilo.start();
	}

	private void setAdapter(ArrayList<Vaca> lista) {
		adapter = new AdapterVaca(this, lista);
		listaVista.setAdapter(adapter);

		listaVista
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						String id_vaca = adapter.getItem(position).getId_vaca();
						lanzarVaca(id_vaca);
					}
				});
	}

	private void lanzarVaca(String id_vaca) {
		Intent i = new Intent(this, VacaVista.class);
		i.putExtra("id_vaca", id_vaca);
		i.putExtra("id_usuario", this.id_usuario);
		startActivity(i);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_usuario, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.administrar_cuenta) {
			Intent i = new Intent(this, AdministrarCuentaVista.class);
			i.putExtra("id_usuario", this.id_usuario);
			i.putExtra("contraseña", this.contraseña);
			startActivity(i); 
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
