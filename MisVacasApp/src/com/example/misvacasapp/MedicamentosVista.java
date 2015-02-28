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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Spinner;

/**
 * Clase de la actividad de los medicamentos En ella se implementan los m�todos
 * que se utilizan para manejar la vista de los medicamentos de una vaca
 * 
 * @author Sara Martinez Lopez
 * */
public class MedicamentosVista extends ActionBarActivity {
	// Atributos
	/** Id vaca */
	private String idVaca;
	/**
	 * Vista de la lista de medicamentos a mostrar en la vista de medicamentos
	 * de una vaca
	 */
	private ListView listaVista;
	/** Adaptador de la lista */
	private AdapterMedicamento adapter;
	/** Tabla hash que indica que vaca esta seleccionada */
	private TableSeleccionado seleccionado;
	/** Lista de los medicamentos que tiene una vaca */
	private ArrayList<Medicamento> lista;

	// M�todos
	/**
	 * A�ade la vista de los medicamentos Recoge el id de la vaca de la vista de
	 * la vista Inicializa parametros
	 * */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lista_medicamentos_vista);
		Bundle bundle = getIntent().getExtras();
		idVaca = bundle.getString("id_vaca");
		listaVista = (ListView) findViewById(R.id.lista_medicamentos_vista);
		mostrarListado();
	}

	/**
	 * M�todo que rellena la lista con los medicamentos de la vaca Llama al
	 * servicio web para recibir los datos
	 * 
	 * @see onCreate eliminar
	 * */
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

	/**
	 * Crea el adaptador de la lista de la vista del usuario y se la a�ade
	 * 
	 * @see mostrarListado
	 * @param lista
	 *            ArrayList de medicamentos
	 * */
	private void setAdapter(ArrayList<Medicamento> lista) {
		adapter = new AdapterMedicamento(this, lista, seleccionado);
		listaVista.setAdapter(adapter);
		clickLista();
		clickLargoLista();
	}

	/**
	 * M�todo que utiliza la lista para hacer el click en un item de la lista
	 * 
	 * @see setAdapter
	 * */
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

	/**
	 * M�todo que se utiliza para la selecci�n larga en la lista
	 * 
	 * @see setAdapter
	 * */
	private void clickLargoLista() {
		listaVista.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				seleccionado = adapter.getSeleccionado();
				if (seleccionado.getTable().get(position)) {
					seleccionado.getTable().put(position, false);

					if (!activarBoton()) {
						Button botonEliminar = (Button) findViewById(R.id.eliminar_medicamento);
						botonEliminar
								.setBackgroundResource(R.drawable.boton_eliminar_2);
						botonEliminar.setEnabled(false);
					}
				} else {
					seleccionado.getTable().put(position, true);
					Button botonEliminar = (Button) findViewById(R.id.eliminar_medicamento);
					botonEliminar
							.setBackgroundResource(R.drawable.boton_eliminar);
					botonEliminar.setEnabled(true);
				}
				adapter.setSeleccionado(seleccionado);
				setAdapter(lista);
				return true;
			}
		});
	}

	/**
	 * M�todo que activa o desactiva el bot�n eliminar
	 * 
	 * @see clickLargoLista
	 * */
	private boolean activarBoton() {
		boolean resultado = false;
		for (int i = 0; i < lista.size(); i++) {
			if (seleccionado.getTable().get(i)) {
				resultado = true;
			}
		}
		return resultado;
	}

	/**
	 * Cambia a la vista de la vaca
	 * 
	 * @param id_vaca
	 *            id de la vaca
	 * @see clickLista
	 * */
	private void lanzarMedicamento(int id_medicamento) {
		Intent i = new Intent(this, MedicamentoVista.class);
		i.putExtra("id_medicamento", id_medicamento);
		i.putExtra("id_vaca", idVaca);
		startActivity(i);
	}

	/**
	 * M�todo que utiliza el bot�n a�adir Lanza la vista para a�adir un
	 * medicamento pasandole el parametro del id de la vaca y la lista de
	 * medicamentos que tiene esa vaca
	 * 
	 * @param v
	 *            Vista
	 * */
	public void a�adirMedicamento(View v) {
		Gson json = new GsonBuilder().setPrettyPrinting()
				.setDateFormat("dd-MM-yyyy").create();
		String listaMedicamentos = json.toJson(lista);
		Intent i = new Intent(this, AniadirMedicamentoVista.class);
		i.putExtra("id_vaca", idVaca);
		i.putExtra("listaMedicamentos", listaMedicamentos);
		startActivity(i);
	}

	/**
	 * M�todo que utiliza el bot�n eliminar Busca los medicamentos que estan
	 * seleccionados utilizando la tabla hash, para eliminarlos
	 * 
	 * @param v
	 *            Vista
	 * */
	public void eliminarMedicamento(View v) {
		String eliminados = "";
		for (int i = 0; i < seleccionado.getTable().size(); i++) {
			if (seleccionado.getTable().get(i)) {
				eliminados = eliminados + " " + lista.get(i).getTipo() + "("
						+ lista.get(i).getFecha() + ")" + "\n";
			}
		}
		alertaConfirmarEliminar(eliminados);
	}

	/**
	 * M�todo que lanza una alerta para advertir que se van a eliminar esas
	 * vacas Si pulsas si eliminas la vaca(s) y si no se cierra la alerta y no
	 * hace nada
	 * 
	 * @param eliminados
	 *            String de los ids que se van a eliminar para mostrarlos por
	 *            pantalla
	 */
	public void alertaConfirmarEliminar(String eliminados) {
		AlertDialog.Builder dialogo = new AlertDialog.Builder(this);
		dialogo.setMessage("�Quiere eliminar los medicamentos?: " + eliminados);
		dialogo.setPositiveButton("Si", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				for (int i = 0; i < seleccionado.getTable().size(); i++) {
					if (seleccionado.getTable().get(i)) {
						eliminar(lista.get(i).getId_medicamento());
					}
				}
				mostrarListado();
			}
		});
		dialogo.setNegativeButton("No", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		});

		dialogo.show();
	}

	/**
	 * M�todo que llama al servicio web para eliminar un medicamento. Para
	 * eliminarlo utiliza el id de la vaca y el del medicamento
	 * 
	 * @see eliminarVaca
	 * @param id_medicamento
	 *            Id del medicamento a eliminar
	 * */
	public void eliminar(final int id_medicamento) {
		Thread hilo = new Thread() {
			LlamadaMedicamentoWS llamada = new LlamadaMedicamentoWS();

			public void run() {
				llamada.LLamadaEliminarMedicamento(
						Integer.toString(id_medicamento), idVaca);
				
			}
		};
		hilo.start();
		Button botonEliminar = (Button) findViewById(R.id.eliminar_medicamento);
		botonEliminar.setBackgroundResource(R.drawable.boton_eliminar_2);
		botonEliminar.setEnabled(false);
	}

	/**
	 * M�todo que utiliza el bot�n buscar
	 * 
	 * @param v
	 *            Vista
	 * */
	public void buscarMedicamento(View v) {
		alertaBuscar();
	}

	/**
	 * Muestra un dialogo para introducir el id de la vaca a buscar
	 * 
	 * @see buscarMedicamento
	 * */
	private void alertaBuscar() {
		LayoutInflater inflater = LayoutInflater.from(this);
		View layout = inflater.inflate(R.layout.buscar_medicamento_layout, null);
		AlertDialog.Builder dialogo = new AlertDialog.Builder(this);
		dialogo.setView(layout);
		dialogo.setMessage("Buscar");

		CheckBox tipoBox = (CheckBox)layout.findViewById(R.id.checkBox1);
		CheckBox fechaBox = (CheckBox)layout.findViewById(R.id.checkBox2);
		if(tipoBox.isChecked()){
			Spinner tipoSpinner = (Spinner)layout.findViewById(R.id.spinner1);
			tipoSpinner.setVisibility(layout.VISIBLE);
		}
		if(fechaBox.isChecked()){
			Spinner diaSpinner = (Spinner)layout.findViewById(R.id.spinner2);
			diaSpinner.setVisibility(layout.VISIBLE);
			Spinner mesSpinner = (Spinner)layout.findViewById(R.id.spinner3);
			mesSpinner.setVisibility(layout.VISIBLE)
			Spinner anioSpinner = (Spinner)layout.findViewById(R.id.spinner4);
			anioSpinner.setVisibility(layout.VISIBLE);
		}
		/** M�todo del bot�n aceptar del dialogo */
		dialogo.setPositiveButton("Aceptar", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
			}
		});
		/** M�todo del bot�n cancelar del dialogo */
		dialogo.setNegativeButton("Cancelar", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				finish();
			}
		});
		dialogo.show();
	}

	/**
	 * M�todo que selecciona un item de la lista
	 * 
	 * @param item
	 *            Item a seleccionar
	 * @see alertaBuscar
	 * */
	public void seleccionarEnLista(int item) {
		int i = 0;
		while (lista.size() > i) {
			if (seleccionado.getTable().get(i)) {
				seleccionado.getTable().put(i, false);
				listaVista.getChildAt(i).setBackgroundColor(Color.TRANSPARENT);
			}
			i++;
		}
		i = 0;
		while (lista.size() > i) {
			if (item == (lista.get(i).getId_medicamento())) {
				listaVista.getChildAt(i).setBackgroundColor(Color.RED);
				seleccionado.getTable().put(i, true);
				Button botonEliminar = (Button) findViewById(R.id.eliminar_medicamento);
				botonEliminar.setBackgroundResource(R.drawable.boton_eliminar);
				botonEliminar.setEnabled(true);
			}
			i++;
		}
	}

	/**
	 * A�ade el menu a la vista login
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
	 * A�ade los item al menu
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
