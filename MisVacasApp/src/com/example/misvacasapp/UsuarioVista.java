package com.example.misvacasapp;

import java.util.ArrayList;
import com.example.misvacasapp.R;
import com.example.misvacasapp.adapter.AdapterVaca;
import com.example.misvacasapp.aniadir.AniadirVacaVista;
import com.example.misvacasapp.llamadaWS.LlamadaVacaWS;
import com.example.misvacasapp.menus.AdministrarCuentaVista;
import com.example.misvacasapp.modelo.Vaca;
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
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

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
	/** Lista de las vacas que tiene el usuario */
	private ArrayList<Vaca> lista;
	/** Tabla hash que indica que vaca esta seleccionada */
	private TableSeleccionado seleccionado;

	// Métodos
	/**
	 * Añade la vista usuario Recoge el usuario y la contraseña de la vista
	 * login Inicializa parametros
	 * */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_usuario_vista);
		Bundle bundle = getIntent().getExtras();
		id_usuario = bundle.getString("id_usuario");
		contraseña = bundle.getString("contraseña");
		listaVista = (ListView) findViewById(R.id.lista_usuario_vista);
		// scroolLista();
		mostrarListado();
	}

	/**
	 * Método que utiliza el botón añadir Lanza la vista para añadir una vaca
	 * pasandole el parametro del id de usuario
	 * 
	 * @param v
	 *            Vista
	 * */
	public void añadirVaca(View v) {
		Intent i = new Intent(this, AniadirVacaVista.class);
		i.putExtra("id_usuario", id_usuario);
		startActivity(i);
	}

	/**
	 * Método que utiliza el botón eliminar Busca las vacas que estan
	 * seleccionadas utilizando la tabla hash, para eliminarlas
	 * 
	 * @param v
	 *            Vista
	 * */
	public void eliminarVaca(View v) {
		for (int i = 0; i < seleccionado.getTable().size(); i++) {
			if (seleccionado.getTable().get(i)) {
				eliminar(lista.get(i).getId_vaca());
			}
		}
	}

	/**
	 * Método que llama al servicio web para eliminar a la vaca. Para eliminarla
	 * utiliza el id de la vaca y el del usuario
	 * 
	 * @see eliminarVaca
	 * @param id_vaca
	 *            Id de la vaca a eliminar
	 * */
	public void eliminar(final String id_vaca) {
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
					System.out.println("1: " + texto.getText().toString());
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
	 * Método que selecciona un item de la lista
	 * 
	 * @param item
	 *            Item a seleccionar
	 * @see alertaBuscar
	 * */
	public void seleccionarEnLista(String item) {
		int i = 0;
		while (lista.size() > i) {
			listaVista.getSelectedView().setBackgroundColor(Color.BLUE);
			// System.out.println("ENTRA: "+i);
			// System.out.println(listaVista.getChildAt(i));
			// listaVista.getChildAt(i).setBackgroundColor(Color.TRANSPARENT);
			seleccionado.getTable().put(i, false);
			i++;
		}
		i = 0;
		while (lista.size() > i) {
			if (item.equals(lista.get(i).getId_vaca())) {
				listaVista.getChildAt(i).setVisibility(i);
				System.out.println("3: " + i);
				listaVista.getChildAt(i).setBackgroundColor(Color.RED);
				seleccionado.getTable().put(i, true);
				Button botonEliminar = (Button) findViewById(R.id.eliminar);
				botonEliminar.setEnabled(true);
			}
			i++;
		}
	}

	/**
	 * Método que rellena la lista con las vacas del usuario Llama al servicio
	 * web para recibir los datos
	 * 
	 * @see onCreate eliminar
	 * */
	private void mostrarListado() {
		seleccionado = new TableSeleccionado();
		Thread hilo = new Thread() {
			String res = "";
			Gson json = new GsonBuilder().setPrettyPrinting()
					.setDateFormat("dd-MM-yyyy").create();
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
						if (lista.get(0).getId_vaca().equals("0")) {
						} else {
							setAdapter(lista);
						}
					}
				});
			}
		};
		hilo.start();
	}

	/**
	 * Crea el adaptador de la lista de la vista del usuario y se la añade
	 * 
	 * @see mostrarListado
	 * */
	private void setAdapter(ArrayList<Vaca> lista) {
		adapter = new AdapterVaca(this, lista);
		listaVista.setAdapter(adapter);
		clickLista();
		clickLargoLista();
	}

	// /**
	// * Genera el scrool de la lisa de la vista del usuario
	// *
	// * @see onCreate
	// * */
	// private void scroolLista() {
	// listaVista.setOnTouchListener(new ListView.OnTouchListener() {
	// @Override
	// public boolean onTouch(View v, MotionEvent event) {
	// // TODO Auto-generated method stub
	// int action = event.getAction();
	// switch (action) {
	// case MotionEvent.ACTION_DOWN:
	// // Disallow ScrollView to intercept touch events.
	// v.getParent().requestDisallowInterceptTouchEvent(true);
	// break;
	// case MotionEvent.ACTION_UP:
	// // Allow ScrollView to intercept touch events.
	// v.getParent().requestDisallowInterceptTouchEvent(false);
	// break;
	// }
	// // Handle ListView touch events.
	// v.onTouchEvent(event);
	// return true;
	// }
	// });
	// }
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
				if (seleccionado.getTable().get(position)) {
					seleccionado.getTable().put(position, false);
					listaVista.getChildAt(
							position - listaVista.getFirstVisiblePosition())
							.setBackgroundColor(Color.TRANSPARENT);
					if (!activarBoton()) {
						Button botonEliminar = (Button) findViewById(R.id.eliminar);
						botonEliminar.setEnabled(false);
					}
				} else {
					System.out.println(position
							- listaVista.getFirstVisiblePosition());
					seleccionado.getTable().put(position, true);
					listaVista.getChildAt(
							position - listaVista.getFirstVisiblePosition())
							.setBackgroundColor(Color.RED);
					Button botonEliminar = (Button) findViewById(R.id.eliminar);
					botonEliminar.setEnabled(true);
				}
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
		Intent i = new Intent(this, VacaVista.class);
		i.putExtra("id_vaca", id_vaca);
		i.putExtra("id_usuario", this.id_usuario);
		startActivity(i);
	}

	/**
	 * Método que activa o desactiva el botón eliminar
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
	 * Añade el menu a la vista login
	 * 
	 * @param menu
	 * */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
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