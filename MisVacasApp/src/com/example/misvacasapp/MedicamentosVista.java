package com.example.misvacasapp;

import java.util.ArrayList;
import java.util.Arrays;

import com.example.misvacasapp.adapter.AdapterMedicamento;
import com.example.misvacasapp.bbddinterna.MedicamentoDatosBbdd;
import com.example.misvacasapp.bbddinterna.VacaDatosBbdd;
import com.example.misvacasapp.llamadaWS.LlamadaVacaWS;
import com.example.misvacasapp.modelo.Medicamento;
import com.example.misvacasapp.modelo.Vaca;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Spinner;

/**
 * Clase de la actividad de los medicamentos En ella se implementan los métodos
 * que se utilizan para manejar la vista de los medicamentos de una vaca
 * 
 * @author Sara Martinez Lopez
 * */
public class MedicamentosVista extends ActionBarActivity {
	// Atributos
	/** Id vaca */
	private String id_vaca;
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
	private ArrayList<Medicamento> listaMedicamentos;
	
	private MedicamentoDatosBbdd mdatos;

	// Métodos
	/**
	 * Añade la vista de los medicamentos Recoge el id de la vaca de la vista de
	 * la vista Inicializa parametros
	 * */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lista_medicamentos_vista);
		Bundle bundle = getIntent().getExtras();
		id_vaca = bundle.getString("id_vaca");
		listaVista = (ListView) findViewById(R.id.lista_medicamentos_vista);
		listaMedicamentos = new ArrayList<Medicamento>();

		mostrarListado();
	}

	private void getListaMedicamentos() {
		mdatos = new MedicamentoDatosBbdd(
				getApplicationContext());
		listaMedicamentos = mdatos.getMedicamentos(id_vaca);
	}

	/**
	 * Método que rellena la lista con los medicamentos de la vaca Llama al
	 * servicio web para recibir los datos
	 * 
	 * @see onCreate eliminar
	 * */
	private void mostrarListado() {
		seleccionado = new TableSeleccionado();
		getListaMedicamentos();
		for (int i = 0; i < listaMedicamentos.size(); i++) {
			seleccionado.getTable().put(i, false);
		}
		Button botonEliminar = (Button) findViewById(R.id.eliminar_medicamento);
		botonEliminar.setBackgroundResource(R.drawable.boton_eliminar_5);
		botonEliminar.setEnabled(false);
		if (listaMedicamentos.size() != 0)
			setAdapter(listaMedicamentos);
		else {
			setAdapter(new ArrayList<Medicamento>());
		}

	}

	/**
	 * Crea el adaptador de la lista de la vista del usuario y se la añade
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
	 * Método que utiliza la lista para hacer el click en un item de la lista
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
	 * Método que se utiliza para la selección larga en la lista
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
								.setBackgroundResource(R.drawable.boton_eliminar_5);
						botonEliminar.setEnabled(false);
					}
				} else {
					seleccionado.getTable().put(position, true);
					Button botonEliminar = (Button) findViewById(R.id.eliminar_medicamento);
					botonEliminar
							.setBackgroundResource(R.drawable.boton_borrar2);
					botonEliminar.setEnabled(true);
				}
				adapter.setSeleccionado(seleccionado);
				setAdapter(listaMedicamentos);
				return true;
			}
		});
	}

	/**
	 * Método que activa o desactiva el botón eliminar
	 * 
	 * @see clickLargoLista
	 * */
	private boolean activarBoton() {
		boolean resultado = false;
		for (int i = 0; i < listaMedicamentos.size(); i++) {
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
		new LanzarVista(this).lanzarMedicamento(id_medicamento, id_vaca);
	}

	/**
	 * Método que utiliza el botón añadir Lanza la vista para añadir un
	 * medicamento pasandole el parametro del id de la vaca y la lista de
	 * medicamentos que tiene esa vaca
	 * 
	 * @param v
	 *            Vista
	 * */
	public void añadirMedicamento(View v) {
		new LanzarVista(this).lanzarAñadirMedicamento(id_vaca);
		finish();
	}

	/**
	 * Método que utiliza el botón eliminar Busca los medicamentos que estan
	 * seleccionados utilizando la tabla hash, para eliminarlos
	 * 
	 * @param v
	 *            Vista
	 * */
	public void eliminarMedicamento(View v) {
		String eliminados = "";
		for (int i = 0; i < seleccionado.getTable().size(); i++) {
			if (seleccionado.getTable().get(i)) {
				eliminados = eliminados + " "
						+ listaMedicamentos.get(i).getTipo() + " ("
						+ listaMedicamentos.get(i).getFecha() + ")" + "\n";
			}
		}
		alertaConfirmarEliminar(eliminados);
	}

	/**
	 * Método que lanza una alerta para advertir que se van a eliminar esas
	 * vacas Si pulsas si eliminas la vaca(s) y si no se cierra la alerta y no
	 * hace nada
	 * 
	 * @param eliminados
	 *            String de los ids que se van a eliminar para mostrarlos por
	 *            pantalla
	 */
	public void alertaConfirmarEliminar(String eliminados) {
		AlertDialog.Builder dialogo = new AlertDialog.Builder(this);
		dialogo.setMessage("¿Quiere eliminar los medicamentos?:   " + eliminados);
		dialogo.setPositiveButton("Si", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				for (int i = 0; i < seleccionado.getTable().size(); i++) {
					if (seleccionado.getTable().get(i)) {
						eliminar(listaMedicamentos.get(i).getId_medicamento());
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
	 * Método que llama al servicio web para eliminar un medicamento. Para
	 * eliminarlo utiliza el id de la vaca y el del medicamento
	 * 
	 * @see eliminarVaca
	 * @param id_medicamento
	 *            Id del medicamento a eliminar
	 * */
	public void eliminar(int id_medicamento) {
		mdatos.eliminarMedicamento(id_medicamento, id_vaca);
	}

	/**
	 * Método que utiliza el botón buscar
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
		final View layout = inflater.inflate(
				R.layout.buscar_medicamento_layout, null);
		AlertDialog.Builder dialogo = new AlertDialog.Builder(this);
		dialogo.setView(layout);
		dialogo.setMessage("Buscar");

		Spinner tipoSpinner = (Spinner) layout.findViewById(R.id.spinner1);

		rellenarSpinnerBuscar(tipoSpinner);

		/** Método del botón aceptar del dialogo */
		dialogo.setPositiveButton("Aceptar", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Spinner tipoSpinner = (Spinner) layout
						.findViewById(R.id.spinner1);
				mostrarTipoFecha(tipoSpinner);
			}
		});

		/** Método del botón cancelar del dialogo */
		dialogo.setNegativeButton("Cancelar", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		});
		dialogo.show();
	}

	/**
	 * Método que rellena los spinner de la alerta buscar
	 * 
	 * @param tipoSpinner
	 * @param diaSpinner
	 * @param mesSpinner
	 * @param anioSpinner
	 */

	private void rellenarSpinnerBuscar(Spinner tipoSpinner) {
		ArrayList<String> listaMedicamentos = new ArrayList<String>(
				Arrays.asList("-", "Brucelosis", "Leptospirosis",
						"Rinotraqueitis", "Parainfluenza 3 (PI 3)",
						"Diarrea viral bovina (DVB)", "Analgésico",
						"Antibiótico", " Desparasitante", " Diurético",
						"Expectorante", "Anabólicos y Hormonales",
						" Misceláneos", " Vitaminas", "Otros"));
		ArrayAdapter<String> adapter = new ArrayAdapter<>(
				getApplicationContext(),
				android.R.layout.simple_spinner_dropdown_item,
				listaMedicamentos);
		tipoSpinner.setAdapter(adapter);
		//
		// ArrayList<String> listaDias = new
		// ArrayList<String>(Arrays.asList("-",
		// "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12",
		// "13", "14", "15", "16", "17", "18", "19", "20", "21", "22",
		// "23", "24", "25", "26", "27", "28", "29", "30", "31"));
		// adapter = new ArrayAdapter<>(getApplicationContext(),
		// android.R.layout.simple_spinner_dropdown_item, listaDias);
		// diaSpinner.setAdapter(adapter);
		//
		// ArrayList<String> listaMeses = new
		// ArrayList<String>(Arrays.asList("-",
		// "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"));
		// adapter = new ArrayAdapter<>(getApplicationContext(),
		// android.R.layout.simple_spinner_dropdown_item, listaMeses);
		// diaSpinner.setAdapter(adapter);
		//
		// int añoActual = new java.util.Date().getYear() + 1900;
		// ArrayList<String> listaAnio = new
		// ArrayList<String>(Arrays.asList("-",
		// Integer.toString(añoActual - 30),
		// Integer.toString(añoActual - 29),
		// Integer.toString(añoActual - 28),
		// Integer.toString(añoActual - 27),
		// Integer.toString(añoActual - 26),
		// Integer.toString(añoActual - 25),
		// Integer.toString(añoActual - 24),
		// Integer.toString(añoActual - 23),
		// Integer.toString(añoActual - 22),
		// Integer.toString(añoActual - 21),
		// Integer.toString(añoActual - 20),
		// Integer.toString(añoActual - 19),
		// Integer.toString(añoActual - 18),
		// Integer.toString(añoActual - 17),
		// Integer.toString(añoActual - 16),
		// Integer.toString(añoActual - 15),
		// Integer.toString(añoActual - 14),
		// Integer.toString(añoActual - 13),
		// Integer.toString(añoActual - 12),
		// Integer.toString(añoActual - 11),
		// Integer.toString(añoActual - 10),
		// Integer.toString(añoActual - 9),
		// Integer.toString(añoActual - 8),
		// Integer.toString(añoActual - 7),
		// Integer.toString(añoActual - 6),
		// Integer.toString(añoActual - 5),
		// Integer.toString(añoActual - 4),
		// Integer.toString(añoActual - 3),
		// Integer.toString(añoActual - 2),
		// Integer.toString(añoActual - 1), Integer.toString(añoActual)));
		// adapter = new ArrayAdapter<>(getApplicationContext(),
		// android.R.layout.simple_spinner_dropdown_item, listaAnio);
		// diaSpinner.setAdapter(adapter);
	}

	/**
	 * Método que muestra la lista de medicamentos que coincidan con el tipo
	 * pasado por parametro
	 * 
	 * @param tipoSpinner
	 */
	private void mostrarTipoFecha(Spinner tipoSpinner) {

		ArrayList<Medicamento> listaTipoFecha = new ArrayList<Medicamento>();

		if (tipoSpinner.getSelectedItem().toString().equals("-")) {
			for (int i = 0; i < listaMedicamentos.size(); i++) {
				listaTipoFecha = listaMedicamentos;
				seleccionado.getTable().put(i, false);
			}

		} else {
			for (int i = 0; i < listaMedicamentos.size(); i++) {
				if (listaMedicamentos.get(i).getTipo()
						.equals(tipoSpinner.getSelectedItem().toString())) {
					listaTipoFecha.add(listaMedicamentos.get(i));
				}
			}
		}
		setAdapter(listaTipoFecha);
	}
	
	/**
	 * Metodo que se acciona cuando en el menu se selecciona toda la lista
	 */
	private void seleccionarTodo() {
		for (int i = 0; i < seleccionado.getTable().size(); i++) {
			seleccionado.getTable().put(i, true);
		}
		adapter.setSeleccionado(seleccionado);
		setAdapter(listaMedicamentos);

		Button botonEliminar = (Button) findViewById(R.id.eliminar_medicamento);
		botonEliminar.setEnabled(true);
		botonEliminar.setBackgroundResource(R.drawable.boton_borrar2);
	}

	public void sincronizar(final String usuario) {
		Thread hilo = new Thread() {
			public void run() {
				VacaDatosBbdd vdatos = new VacaDatosBbdd(
						
						getApplicationContext());
				ArrayList<Vaca> listaVacas = new ArrayList<Vaca>();
				listaVacas = vdatos.getListaVacas(usuario);
				Gson json = new GsonBuilder().setPrettyPrinting()
						.setDateFormat("dd-MM-yyyy").create();
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						Toast.makeText(
								MedicamentosVista.this,
								"La cuenta esta siendo sincronizada. Esto puede tardar unos minutos",
								Toast.LENGTH_LONG).show();
					}
				});
				LlamadaVacaWS llamada = new LlamadaVacaWS();
				llamada.LLamadaEliminarVacas(usuario);
				for (int i = 0; i < listaVacas.size(); i++) {
					String vaca = json.toJson(listaVacas.get(i));
					llamada.LLamadaAñadirVaca(vaca);
				}

				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						Toast.makeText(MedicamentosVista.this,
								"La cuenta ha siendo sincronizada",
								Toast.LENGTH_LONG).show();
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
		getMenuInflater().inflate(R.menu.menu_usuario, menu);
		return true;
	}

	/**
	 * Añade los item al menu
	 * 
	 * @param item
	 * */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
SharedPreferences settings = getSharedPreferences("MisDatos",
					Context.MODE_PRIVATE);
		if (id == R.id.cerrar_sesion) {
			
			SharedPreferences.Editor editor = settings.edit();
			editor.putString("id_usuario", "");
			editor.putString("contraseña", "");
			editor.commit();
			new LanzarVista(this).lanzarLogin();
			finish();
		} else if (id == R.id.sincronizar_cuenta) {
			sincronizar(settings.getString("id_usuario", ""));
		} else if (id == R.id.mis_vacas) {
			new LanzarVista(this).lanzarUsuarioVista(settings.getString("id_usuario", ""), settings.getString("contraseña", ""));
			finish();
			return true;
		} else if (id == R.id.seleccionar_todo) {
			seleccionarTodo();
		} else if (id == R.id.administrar_cuenta) {
			new LanzarVista(this).lanzarAdministrarCuenta(settings.getString("id_usuario", ""), settings.getString("contraseña", ""));
			return true;
		} else if (id == R.id.ayuda) {
			AlertDialog.Builder alerta = new AlertDialog.Builder(this);
			alerta.setTitle("Ayuda");
			alerta.setMessage("Para añadir un nuevo medicamento pincha sobre en botón de añadir (el verde)."
					+ "\n"
					+ "Para eliminar un medicamento hay que tener seleccionado un medicamento o varios, y despues pulsar sobre el botón eliminar (el rojo)."
					+ "\n"
					+ "Para buscar un medicamento en la lista, hay que pulsar sobre el boton buscar(el azul) y seleccionar el tipo de medicamento que buscas."
					+ "\n"
					+ "Para ver la ficha del medicamento, pulsa sobre el medicamento en la lista.");
			alerta.show();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
