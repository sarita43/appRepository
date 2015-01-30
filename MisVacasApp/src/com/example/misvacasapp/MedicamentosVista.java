package com.example.misvacasapp;

import java.util.ArrayList;
import com.example.misvacasapp.adapter.AdapterMedicamento;
import com.example.misvacasapp.aniadir.AniadirMedicamentoVista;
import com.example.misvacasapp.llamadaWS.LlamadaMedicamentoWS;
import com.example.misvacasapp.modelo.Medicamento;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.AdapterView.OnItemLongClickListener;

public class MedicamentosVista extends ActionBarActivity {

	private String idVaca;
	private ListView listaVista;
	private AdapterMedicamento adapter;
	private TableSeleccionado seleccionado;
	private ArrayList<Medicamento> lista;

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
		seleccionado = new TableSeleccionado();
		Thread hilo = new Thread() {
			String res = "";
			Gson json = new GsonBuilder().setPrettyPrinting()
					.setDateFormat("dd-MM-yyyy").create();
			LlamadaMedicamentoWS llamada = new LlamadaMedicamentoWS();

			public void run() {
				res = llamada.LlamadaListaMedicamentos(idVaca);
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						lista = json.fromJson(res,
								new TypeToken<ArrayList<Medicamento>>() {
								}.getType());
						for (int i = 0; i < lista.size(); i++) {
							seleccionado.getTable().put(i, false);
						}
						Button botonEliminar = (Button) findViewById(R.id.eliminar_medicamento);
						botonEliminar.setEnabled(false);
						if (lista.get(0).getId_medicamento() != 0)
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

		clickLista();
		clickLargoLista();

	}

	private void clickLista() {
		listaVista
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						int id_medicamento = adapter.getItem(position)
								.getId_medicamento();
						lanzarMedicamento(id_medicamento);

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
						Button botonEliminar = (Button) findViewById(R.id.eliminar_medicamento);
						botonEliminar.setEnabled(false);
					}
				} else {
					seleccionado.getTable().put(position, true);
					listaVista.getChildAt(position).setBackgroundColor(
							Color.RED);
					Button botonEliminar = (Button) findViewById(R.id.eliminar_medicamento);
					botonEliminar.setEnabled(true);
				}
				return true;
			}

		});
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

	private void lanzarMedicamento(int id_medicamento) {
		Intent i = new Intent(this, MedicamentoVista.class);
		i.putExtra("id_medicamento", id_medicamento);
		i.putExtra("id_vaca", idVaca);
		startActivity(i);
	}

	public void añadirMedicamento(View v) {
		Gson json = new GsonBuilder().setPrettyPrinting()
				.setDateFormat("dd-MM-yyyy").create();

		String listaMedicamentos = json.toJson(lista);

		Intent i = new Intent(this, AniadirMedicamentoVista.class);
		i.putExtra("id_vaca", idVaca);
		i.putExtra("listaMedicamentos", listaMedicamentos);
		startActivity(i);
	}

	public void eliminarMedicamento(View v) {
		for (int i = 0; i < seleccionado.getTable().size(); i++) {
			if (seleccionado.getTable().get(i)) {
				eliminar(lista.get(i).getId_medicamento());
			}
		}
	}

	public void eliminar(final int id_medicamento) {
		Thread hilo = new Thread() {
			LlamadaMedicamentoWS llamada = new LlamadaMedicamentoWS();

			public void run() {
				llamada.LLamadaEliminarMedicamento(
						Integer.toString(id_medicamento), idVaca);
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

	public void buscarMedicamento(View v) {
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
				seleccionarEnLista(Integer.parseInt(texto.getText().toString()));
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

	public void seleccionarEnLista(int item) {

		int i = 0;
		while (lista.size() > i) {
			if (seleccionado.getTable().get(i)) {
				seleccionado.getTable().put(i, false);
				listaVista.getChildAt(i).setBackgroundColor(
						Color.TRANSPARENT);
			}
			i++;
		}

		i = 0;
		while (lista.size() > i) {
			if (item == (lista.get(i).getId_medicamento())) {
				listaVista.getChildAt(i).setBackgroundColor(Color.RED);
				seleccionado.getTable().put(i, true);
				Button botonEliminar = (Button) findViewById(R.id.eliminar_medicamento);
				botonEliminar.setEnabled(true);
			}
			i++;
		}
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
