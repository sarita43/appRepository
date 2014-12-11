package com.example.misvacasapp;

import java.util.ArrayList;

import com.example.misvacasapp.R;
import com.example.misvacasapp.adapter.AdapterVaca;
import com.example.misvacasapp.llamadaWS.LlamadaVacaWS;
import com.example.misvacasapp.modelo.Vaca;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

public class UsuarioVista extends ActionBarActivity {
	
	private String id_usuario;
	private ListView listaVista;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_usuario_vista);
		
		Bundle bundle=getIntent().getExtras();
		id_usuario = bundle.getString("id_usuario");
		
		listaVista  = (ListView) findViewById(R.id.lista_usuario_vista);
		
		mostrarListado();
	}

	private void mostrarListado(){
		Thread hilo = new Thread() {
			String res = "";
			Gson json = new Gson();
			LlamadaVacaWS llamada = new LlamadaVacaWS();
			
			public void run() {
				
				res=llamada.LlamadaVaca(id_usuario);
				
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						ArrayList<Vaca> lista = json.fromJson(res, new TypeToken<ArrayList<Vaca>>(){}.getType());
						setAdapter(lista);
					}
				});
			}
		};
		hilo.start();
	}
	
	private void setAdapter(ArrayList<Vaca> lista){
		AdapterVaca adapter= new AdapterVaca(this, lista);
		listaVista.setAdapter(adapter);
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
