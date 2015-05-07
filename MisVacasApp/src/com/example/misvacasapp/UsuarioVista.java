package com.example.misvacasapp;

import java.util.ArrayList;
import com.example.misvacasapp.R;
import com.example.misvacasapp.adapter.AdapterVaca;
import com.example.misvacasapp.bbddinterna.MedicamentoDatosBbdd;
import com.example.misvacasapp.bbddinterna.VacaDatosBbdd;
import com.example.misvacasapp.llamadaWS.LlamadaMedicamentoWS;
import com.example.misvacasapp.llamadaWS.LlamadaVacaWS;
import com.example.misvacasapp.modelo.Medicamento;
import com.example.misvacasapp.modelo.Vaca;
import com.example.misvacasapp.singleton.TableSeleccionado;
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
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Clase de la actividad del usuario En ella se implementan los métodos que se
 * utilizan para manejar la vista del usuario
 * 
 * @author Sara Martinez Lopez
 * */
public class UsuarioVista extends ActionBarActivity {
	// Atributos
	/** Id del usuario */
	private String id_usuario;
	/** Contraseña del usuario */
	private String contraseña;
	/** Vista de la lista de vacas a mostrar en la vista del usuario */
	private ListView listaVista;
	/** Adaptador de la lista */
	private AdapterVaca adapter;
	/** Lista de animales de un usuario */
	private ArrayList<Vaca> listaVacas;
	/** Lista de medicamentos que tienes los animales de un usuario */
	private VacaDatosBbdd vdatos;

	/** Tabla hash que indica que vaca esta seleccionada */
	private TableSeleccionado seleccionado;

	// Métodos
	/**
	 * Añade la vista usuario. Recoge el usuario y la contraseña de la vista
	 * login. Inicializa parametros
	 * */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_usuario_vista);
		Bundle bundle = getIntent().getExtras();
		id_usuario = bundle.getString("id_usuario");
		contraseña = bundle.getString("contraseña");
		listaVista = (ListView) findViewById(R.id.lista_usuario_vista);
		listaVacas = new ArrayList<Vaca>();
		mostrarListado();
	}

	/**
	 * Recoge los animales de un usuario de la base de datos interna y los
	 * guarda en un arrayList
	 */
	public void getListaVacas() {
		vdatos = new VacaDatosBbdd(getApplicationContext());
		listaVacas = vdatos.getListaVacas(id_usuario);
	}

	/**
	 * Método que utiliza el botón añadir. Lanza la vista para añadir un animal
	 * pasandole el parametro del id de usuario
	 * 
	 * @param v
	 *            Vista
	 * */
	public void añadirVaca(View v) {
		new LanzarVista(this).lanzarAñadirVaca(id_usuario);
	}

	/**
	 * Método que utiliza el botón eliminar. Busca los animales que estan
	 * seleccionadas utilizando la tabla hash, para eliminarlas
	 * 
	 * @param v
	 *            Vista
	 * */
	public void botonEliminarVaca(View v) {
		String eliminados = "";
		for (int i = 0; i < seleccionado.getTable().size(); i++) {
			if (seleccionado.getTable().get(i)) {
				eliminados = eliminados + " " + listaVacas.get(i).getId_vaca()
						+ "\n";
			}
		}
		alertaConfirmarEliminar(eliminados);
	}

	/**
	 * Método que lanza una alerta para advertir que se van a eliminar esos
	 * animales. Si pulsas "Si" elimina y si no se cierra la alerta y no hace
	 * nada
	 * 
	 * @param eliminados
	 *            String de los ids que se van a eliminar para mostrarlos por
	 *            pantalla
	 */
	public void alertaConfirmarEliminar(String eliminados) {
		AlertDialog.Builder dialogo = new AlertDialog.Builder(this);
		dialogo.setMessage("¿Quiere eliminar la(s) vaca(s)?: " + eliminados);
		dialogo.setPositiveButton("Si", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				for (int i = 0; i < seleccionado.getTable().size(); i++) {
					if (seleccionado.getTable().get(i)) {
						eliminar(listaVacas.get(i).getId_vaca());
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
	 * Método que elimina al animal. Para eliminarlo utiliza el id del animal.
	 * También elimina todos los medicamentos que tenga ese animal.
	 * 
	 * @param id_vaca
	 *            Id de la vaca a eliminar
	 * */
	public void eliminar(String id_vaca) {
		eliminarMedicamentosVaca(id_vaca);
		vdatos.eliminar(id_vaca);
	}

	/**
	 * Elimina los medicamentos de un animal.
	 * 
	 * @param id_vaca
	 *            String. Id de la vaca a eliminar los medicamentos
	 */
	private void eliminarMedicamentosVaca(String id_vaca) {
		MedicamentoDatosBbdd mdatos = new MedicamentoDatosBbdd(
				getApplicationContext());
		mdatos.eliminarMedicamentos(id_vaca);
	}

	/**
	 * Método que utiliza el botón buscar
	 * 
	 * @param v
	 *            Vista
	 * */
	public void buscarVaca(View v) {
		alertaBuscar();
	}

	/**
	 * Muestra un dialogo para introducir el id de la vaca a buscar
	 * 
	 * @see buscarVaca
	 * */
	private void alertaBuscar() {
		LayoutInflater inflater = LayoutInflater.from(this);
		View layout = inflater.inflate(R.layout.buscar_layout, null);
		AlertDialog.Builder dialogo = new AlertDialog.Builder(this);
		dialogo.setView(layout);
		dialogo.setMessage("Buscar");
		final EditText texto = (EditText) layout.findViewById(R.id.busca);
		/** Método del botón aceptar del dialogo */
		dialogo.setPositiveButton("Aceptar", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				if (texto.getText().toString().equals("")) {
					dialog.cancel();
				} else {
					seleccionarEnLista(texto.getText().toString());
				}
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
	 * Método que selecciona un item de la lista. Si un item ya ha sido
	 * seleccionado lo deselecciona y selecciona el item que se ha pasado por
	 * parámetro
	 * 
	 * @param item
	 *            Item a seleccionar
	 * @see alertaBuscar
	 * */
	public void seleccionarEnLista(String item) {
		int i = 0;
		while (listaVacas.size() > i) {
			seleccionado.getTable().put(i, false);
			i++;
		}
		Button botonEliminar = (Button) findViewById(R.id.eliminar);
		botonEliminar.setEnabled(false);
		botonEliminar.setBackgroundResource(R.drawable.boton_eliminar_5);
		setAdapter(listaVacas);
		i = 0;
		while (listaVacas.size() > i) {
			if (item.equals(listaVacas.get(i).getId_vaca())) {
				listaVacas.add(0, listaVacas.get(i));
				listaVacas.remove(i+1);
				seleccionado.getTable().put(0, true);
				botonEliminar.setEnabled(true);
				botonEliminar.setBackgroundResource(R.drawable.boton_borrar2);
				setAdapter(listaVacas);
			}
			i++;
		}
	}

	/**
	 * Método que rellena la lista con los animales del usuario.
	 * 
	 * */
	private void mostrarListado() {
		seleccionado = new TableSeleccionado();
		getListaVacas();
		for (int i = 0; i < listaVacas.size(); i++) {
			seleccionado.getTable().put(i, false);
		}

		if (listaVacas.size() == 0) {
			setAdapter(new ArrayList<Vaca>());
		} else {
			setAdapter(listaVacas);
		}
		
		Button botonEliminar = (Button) findViewById(R.id.eliminar);
		botonEliminar
				.setBackgroundResource(R.drawable.boton_eliminar_5);
		botonEliminar.setEnabled(false);

	}

	/**
	 * Crea el adaptador de la lista de la vista del usuario
	 * 
	 * @see mostrarListado
	 * @param lista
	 *            ArrayList de animales
	 * */
	private void setAdapter(ArrayList<Vaca> lista) {
		adapter = new AdapterVaca(this, lista, seleccionado);
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
						String id_vaca = adapter.getItem(position).getId_vaca();
						lanzarVaca(id_vaca);
					}
				});
	}

	/**
	 * Método que se utiliza para la selección click largo en la lista
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
						Button botonEliminar = (Button) findViewById(R.id.eliminar);
						botonEliminar
								.setBackgroundResource(R.drawable.boton_eliminar_5);
						botonEliminar.setEnabled(false);
					}
				} else {
					seleccionado.getTable().put(position, true);
					Button botonEliminar = (Button) findViewById(R.id.eliminar);
					botonEliminar.setEnabled(true);
					botonEliminar
							.setBackgroundResource(R.drawable.boton_borrar2);
				}
				adapter.setSeleccionado(seleccionado);
				setAdapter(listaVacas);
				return true;
			}
		});
	}

	/**
	 * Cambia a la vista de la vaca
	 * 
	 * @param id_vaca
	 *            id de la vaca
	 * @see clickLista
	 * */
	private void lanzarVaca(String id_vaca) {
		new LanzarVista(this).lanzarVaca(id_vaca, id_usuario);
	}

	/**
	 * Método que devuelve un true si el boton tiene que estar activado o no
	 * 
	 * @see clickLargoLista
	 * */
	private boolean activarBoton() {
		boolean resultado = false;
		for (int i = 0; i < listaVacas.size(); i++) {
			if (seleccionado.getTable().get(i)) {
				resultado = true;
			}
		}
		return resultado;
	}

	/**
	 * Metodo que se acciona cuando en el menu se selecciona toda la lista
	 */
	private void seleccionarTodo() {
		for (int i = 0; i < seleccionado.getTable().size(); i++) {
			seleccionado.getTable().put(i, true);
		}
		adapter.setSeleccionado(seleccionado);
		setAdapter(listaVacas);

		Button botonEliminar = (Button) findViewById(R.id.eliminar);
		botonEliminar.setEnabled(true);
		botonEliminar.setBackgroundResource(R.drawable.boton_borrar2);
	}

	/**
	 * Añade el menu a la vista usuario
	 * 
	 * @param menu
	 * */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_usuario, menu);
		return true;
	}

	public void sincronizar(final String usuario) {
		Thread hilo = new Thread() {
			public void run() {
				VacaDatosBbdd vdatos = new VacaDatosBbdd(getApplicationContext());
				ArrayList<Vaca> listaVacas = new ArrayList<Vaca>();
				listaVacas = vdatos.getListaVacas(usuario);
				
				MedicamentoDatosBbdd mdatos = new MedicamentoDatosBbdd(getApplicationContext());
				ArrayList<Medicamento> listaMedicamentosUsuario = new ArrayList<Medicamento>();
				
				Gson json = new GsonBuilder().setPrettyPrinting()
						.setDateFormat("dd-MM-yyyy").create();
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						Toast.makeText(
								UsuarioVista.this,
								"La cuenta esta siendo sincronizada. Esto puede tardar unos minutos",
								Toast.LENGTH_LONG).show();
					}
				});
				LlamadaVacaWS llamadaVaca = new LlamadaVacaWS();
				LlamadaMedicamentoWS llamadaMedicamento = new LlamadaMedicamentoWS();
				for (int i = 0; i < listaVacas.size(); i++) {
					//Guarda los medicamentos en una lista
					ArrayList<Medicamento> listaAux = mdatos.getMedicamentos(listaVacas.get(i).getId_vaca());
					for (int j = 0; j < listaAux.size(); j++) {
						listaMedicamentosUsuario.add(listaAux.get(j));
					}
				}
				
				//Eliminar vaca base de datos cloud
				llamadaVaca.LLamadaEliminarVacas(usuario);
				
				//Añadir vaca a base de datos cloud
				for (int i = 0; i < listaVacas.size(); i++) {
					String vaca = json.toJson(listaVacas.get(i));
					llamadaVaca.LLamadaAñadirVaca(vaca);	
				}
				
				//Añadir medicamentos a base de datos cloud
				for (int i = 0; i < listaMedicamentosUsuario.size(); i++) {
					Medicamento m = listaMedicamentosUsuario.get(i);
					String medicamento = json.toJson(m);
					llamadaMedicamento.LLamadaAñadirMedicamento(medicamento);
				}
				

				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						Toast.makeText(UsuarioVista.this,
								"La cuenta ha siendo sincronizada",
								Toast.LENGTH_LONG).show();
					}
				});
			}
		};
		hilo.start();
	}

	/**
	 * Añade los item al menu
	 * 
	 * @param item
	 * */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();

		if (id == R.id.cerrar_sesion) {
			SharedPreferences settings = getSharedPreferences("MisDatos",
					Context.MODE_PRIVATE);
			SharedPreferences.Editor editor = settings.edit();
			editor.putString("id_usuario", "");
			editor.putString("contraseña", "");
			editor.commit();
			new LanzarVista(this).lanzarLogin();
			finish();
		} else if (id == R.id.sincronizar_cuenta) {
			sincronizar(id_usuario);
		} else if (id == R.id.mis_vacas) {
			new LanzarVista(this).lanzarUsuarioVista(id_usuario, contraseña);
			finish();
			return true;
		} else if (id == R.id.seleccionar_todo) {
			seleccionarTodo();
		} else if (id == R.id.administrar_cuenta) {
			new LanzarVista(this).lanzarAdministrarCuenta(id_usuario,
					contraseña);
			return true;
		} else if (id == R.id.ayuda) {
			AlertDialog.Builder alerta = new AlertDialog.Builder(this);
			alerta.setTitle("Ayuda");
			alerta.setMessage("Para añadir un nuevo animal pinchando sobre en botón de añadir (el verde)."
					+ "\n"
					+ "Para eliminar un animal hay que tener seleccionado un animal o varios, y despues pulsar sobre el botón eliminar (el rojo)."
					+ "\n"
					+ "Para buscar un animal en la lista, hay que pulsar sobre el boton buscar(el azul) y poner el id del animal a buscar"
					+ "\n"
					+ "Para ver la ficha del animal, pulsa sobre el animal en la lista");
			alerta.show();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}