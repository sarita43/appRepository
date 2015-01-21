package com.example.misvacasapp;

import java.util.ArrayList;

import com.example.misvacasapp.R;
import com.example.misvacasapp.adapter.AdapterVaca;
import com.example.misvacasapp.llamadaWS.LlamadaVacaWS;
import com.example.misvacasapp.menus.AdministrarCuentaVista;
import com.example.misvacasapp.modelo.Vaca;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class UsuarioVista extends ActionBarActivity {

	private String id_usuario;
	private String contraseña;
	private ListView listaVista;
	private AdapterVaca adapter;
	private ArrayList<Vaca> lista;
	private TableSeleccionado seleccionado;

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

	public void añadirVaca(View v) {

	}

	public void eliminarVaca(View v) {
		for(int i=0;i<seleccionado.getTable().size();i++){
			if(seleccionado.getTable().get(i)){
				eliminar(lista.get(i).getId_vaca());
			}
		}
	}
	
	public void eliminar(final String id_vaca ){
		Thread hilo = new Thread() {
			LlamadaVacaWS llamada = new LlamadaVacaWS();
			
			public void run() {
				llamada.LLamadaEliminarVaca(id_vaca, id_usuario);
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						mostrarListado();
					}
				});
			}
		};
		hilo.start();
	}	

	public void buscarVaca(View v) {
		alertaBuscar();
	}

	private void alertaBuscar() {
		LayoutInflater inflater = LayoutInflater.from(this);
		View layout = inflater.inflate(R.layout.buscar_layout, null);

		AlertDialog.Builder dialogo = new AlertDialog.Builder(this);
		dialogo.setView(layout);
		dialogo.setMessage("Buscar");

		final EditText texto = (EditText) layout.findViewById(R.id.busca);
		dialogo.setPositiveButton("Aceptar", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				seleccionarEnLista(texto.getText().toString());
			}
		});
		dialogo.setNegativeButton("Cancelar", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				finish();
			}
		});

		dialogo.show();
	}

	public void seleccionarEnLista(String item) {

		int i = 0;
		while (lista.size() > i) {
			listaVista.getChildAt(i).setBackgroundColor(Color.TRANSPARENT);
			seleccionado.getTable().put(i, false);
			i++;
		}
		i = 0;
		while (lista.size() > i) {
			if (item.equals(lista.get(i).getId_vaca())) {
				listaVista.getChildAt(i).setBackgroundColor(Color.RED);
				seleccionado.getTable().put(i, true);
				Button botonEliminar = (Button) findViewById(R.id.eliminar);
				botonEliminar.setEnabled(true);
			}
			i++;
		}
	}

	private void mostrarListado() {
		seleccionado = new TableSeleccionado();
		Thread hilo = new Thread() {
			String res = "";
			Gson json = new Gson();
			LlamadaVacaWS llamada = new LlamadaVacaWS();

			public void run() {

				res = llamada.LlamadaListaVacas(id_usuario);

				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						lista = json.fromJson(res,
								new TypeToken<ArrayList<Vaca>>() {
								}.getType());
						for (int i = 0; i < lista.size(); i++) {
							seleccionado.getTable().put(i, false);
						}
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

		clickLista();
		clickLargoLista();

	}

	private void clickLista() {
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

	private void clickLargoLista() {
		listaVista.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {

				if (seleccionado.getTable().get(position)) {
					seleccionado.getTable().put(position, false);
					listaVista.getChildAt(position).setBackgroundColor(
							Color.TRANSPARENT);
					if (!activarBoton()) {
						Button botonEliminar = (Button) findViewById(R.id.eliminar);
						botonEliminar.setEnabled(false);
					}
				} else {
					seleccionado.getTable().put(position, true);
					listaVista.getChildAt(position).setBackgroundColor(
							Color.RED);
					Button botonEliminar = (Button) findViewById(R.id.eliminar);
					botonEliminar.setEnabled(true);
				}
				return true;
			}

		});
	}

	private void lanzarVaca(String id_vaca) {
		Intent i = new Intent(this, VacaVista.class);
		i.putExtra("id_vaca", id_vaca);
		i.putExtra("id_usuario", this.id_usuario);
		startActivity(i);
	}

	private boolean activarBoton() {
		boolean resultado = false;
		for (int i = 0; i < lista.size(); i++) {
			if (seleccionado.getTable().get(i)) {
				resultado = true;
			}
		}
		return resultado;
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
