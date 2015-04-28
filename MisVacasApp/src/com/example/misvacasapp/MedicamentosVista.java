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
 * Clase de la actividad de los medicamentos En ella se implementan los m�todos
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
	 * M�todo que rellena la lista con los medicamentos de la vaca Llama al
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
	 * M�todo que activa o desactiva el bot�n eliminar
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
	 * M�todo que utiliza el bot�n a�adir Lanza la vista para a�adir un
	 * medicamento pasandole el parametro del id de la vaca y la lista de
	 * medicamentos que tiene esa vaca
	 * 
	 * @param v
	 *            Vista
	 * */
	public void a�adirMedicamento(View v) {
		new LanzarVista(this).lanzarA�adirMedicamento(id_vaca);
		finish();
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
				eliminados = eliminados + " "
						+ listaMedicamentos.get(i).getTipo() + " ("
						+ listaMedicamentos.get(i).getFecha() + ")" + "\n";
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
		dialogo.setMessage("�Quiere eliminar los medicamentos?:   " + eliminados);
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
	 * M�todo que llama al servicio web para eliminar un medicamento. Para
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
		final View layout = inflater.inflate(
				R.layout.buscar_medicamento_layout, null);
		AlertDialog.Builder dialogo = new AlertDialog.Builder(this);
		dialogo.setView(layout);
		dialogo.setMessage("Buscar");

		Spinner tipoSpinner = (Spinner) layout.findViewById(R.id.spinner1);

		rellenarSpinnerBuscar(tipoSpinner);

		/** M�todo del bot�n aceptar del dialogo */
		dialogo.setPositiveButton("Aceptar", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Spinner tipoSpinner = (Spinner) layout
						.findViewById(R.id.spinner1);
				mostrarTipoFecha(tipoSpinner);
			}
		});

		/** M�todo del bot�n cancelar del dialogo */
		dialogo.setNegativeButton("Cancelar", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		});
		dialogo.show();
	}

	/**
	 * M�todo que rellena los spinner de la alerta buscar
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
						"Diarrea viral bovina (DVB)", "Analg�sico",
						"Antibi�tico", " Desparasitante", " Diur�tico",
						"Expectorante", "Anab�licos y Hormonales",
						" Miscel�neos", " Vitaminas", "Otros"));
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
		// int a�oActual = new java.util.Date().getYear() + 1900;
		// ArrayList<String> listaAnio = new
		// ArrayList<String>(Arrays.asList("-",
		// Integer.toString(a�oActual - 30),
		// Integer.toString(a�oActual - 29),
		// Integer.toString(a�oActual - 28),
		// Integer.toString(a�oActual - 27),
		// Integer.toString(a�oActual - 26),
		// Integer.toString(a�oActual - 25),
		// Integer.toString(a�oActual - 24),
		// Integer.toString(a�oActual - 23),
		// Integer.toString(a�oActual - 22),
		// Integer.toString(a�oActual - 21),
		// Integer.toString(a�oActual - 20),
		// Integer.toString(a�oActual - 19),
		// Integer.toString(a�oActual - 18),
		// Integer.toString(a�oActual - 17),
		// Integer.toString(a�oActual - 16),
		// Integer.toString(a�oActual - 15),
		// Integer.toString(a�oActual - 14),
		// Integer.toString(a�oActual - 13),
		// Integer.toString(a�oActual - 12),
		// Integer.toString(a�oActual - 11),
		// Integer.toString(a�oActual - 10),
		// Integer.toString(a�oActual - 9),
		// Integer.toString(a�oActual - 8),
		// Integer.toString(a�oActual - 7),
		// Integer.toString(a�oActual - 6),
		// Integer.toString(a�oActual - 5),
		// Integer.toString(a�oActual - 4),
		// Integer.toString(a�oActual - 3),
		// Integer.toString(a�oActual - 2),
		// Integer.toString(a�oActual - 1), Integer.toString(a�oActual)));
		// adapter = new ArrayAdapter<>(getApplicationContext(),
		// android.R.layout.simple_spinner_dropdown_item, listaAnio);
		// diaSpinner.setAdapter(adapter);
	}

	/**
	 * M�todo que muestra la lista de medicamentos que coincidan con el tipo
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
					llamada.LLamadaA�adirVaca(vaca);
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
	 * A�ade el menu a la vista login
	 * 
	 * @param menu
	 * */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_usuario, menu);
		return true;
	}

	/**
	 * A�ade los item al menu
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
			editor.putString("contrase�a", "");
			editor.commit();
			new LanzarVista(this).lanzarLogin();
			finish();
		} else if (id == R.id.sincronizar_cuenta) {
			sincronizar(settings.getString("id_usuario", ""));
		} else if (id == R.id.mis_vacas) {
			new LanzarVista(this).lanzarUsuarioVista(settings.getString("id_usuario", ""), settings.getString("contrase�a", ""));
			finish();
			return true;
		} else if (id == R.id.seleccionar_todo) {
			seleccionarTodo();
		} else if (id == R.id.administrar_cuenta) {
			new LanzarVista(this).lanzarAdministrarCuenta(settings.getString("id_usuario", ""), settings.getString("contrase�a", ""));
			return true;
		} else if (id == R.id.ayuda) {
			AlertDialog.Builder alerta = new AlertDialog.Builder(this);
			alerta.setTitle("Ayuda");
			alerta.setMessage("Para a�adir un nuevo medicamento pincha sobre en bot�n de a�adir (el verde)."
					+ "\n"
					+ "Para eliminar un medicamento hay que tener seleccionado un medicamento o varios, y despues pulsar sobre el bot�n eliminar (el rojo)."
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
