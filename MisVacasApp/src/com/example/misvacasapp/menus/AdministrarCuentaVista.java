package com.example.misvacasapp.menus;

import java.util.ArrayList;

import com.example.misvacasapp.LanzarVista;
import com.example.misvacasapp.R;
import com.example.misvacasapp.adapter.AdapterListaMenu;
import com.example.misvacasapp.llamadaWS.LlamadaMedicamentoWS;
import com.example.misvacasapp.llamadaWS.LlamadaUsuarioWS;
import com.example.misvacasapp.llamadaWS.LlamadaVacaWS;
import com.example.misvacasapp.modelo.Vaca;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Clase de la actividad administrar cuenta
 * <p>
 * En ella se implementan los métodos que se utilizan para manejar la vista de
 * administrar cuenta
 * </p>
 * 
 * @author Sara Martinez Lopez
 * */
public class AdministrarCuentaVista extends ActionBarActivity {
	// ----------------------------Atributos----------------------------------//
	/** Adapter de la lista de los menus */
	private AdapterListaMenu adapter;
	/** Id del usuario */
	private String id_usuario;
	/** Contraseña del usuario */
	private String contraseña;
	/** Lista de vacas del usuario */
	private ArrayList<Vaca> listaVacas;

	// -----------------------------Métodos-----------------------------------//
	/**
	 * Método que según el menu seleccionado se hace una cosa u otra Se puede
	 * modificar el usuario, cambiar la contraseña o eliminar la cuenta
	 * 
	 * @param item
	 *            String del nombre del menu
	 * */
	private void elegirMenu(String item) {
		switch (item) {
		case "\n Modificar usuario\n":
			nuevaVentana(ModificarUsuarioVista.class);
			break;
		case "\n Cambiar contraseña\n":
			nuevaVentana(NuevaContraseniaVista.class);
			break;
		case "\n Eliminar cuenta\n":
			alertaEliminarUsuario();
			break;
		default:
			break;
		}
	}

	/**
	 * Alerta que avisa si desea eliminar el usuario o no Si pulsa que si
	 * elimina la cuenta, si no deja de mostrar el dialogo
	 * 
	 * @see elegirMenu
	 * */
	private void alertaEliminarUsuario() {
		AlertDialog.Builder dialogo = new AlertDialog.Builder(this);
		dialogo.setMessage("¿Quiere eliminar la cuenta?");
		dialogo.setPositiveButton("Si", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				eliminarCuenta();
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
	 * Método que elimina la cuenta del usuario
	 * <p>
	 * LLama al servicio web del usuario
	 * </p>
	 * 
	 * @see alertaEliminarUsuario
	 * */
	private void eliminarCuenta() {
		Thread hilo = new Thread() {
			LlamadaUsuarioWS llamada = new LlamadaUsuarioWS();

			public void run() {
				eliminarVacas(id_usuario);
				llamada.eliminarUsuario(id_usuario);
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						lanzarLogin();
						Toast.makeText(AdministrarCuentaVista.this,
								"Usuario elimidado", Toast.LENGTH_LONG).show();
						finish();
					}
				});
			}
		};
		hilo.start();
	}

	/**
	 * Método que lanza una ventana segun la clase, pasandole el id de usuario y
	 * la contraseña
	 * 
	 * @param ventanaNombre
	 *            Clase de la ventana
	 * */
	@SuppressWarnings("rawtypes")
	private void nuevaVentana(Class ventanaNombre) {
		new LanzarVista(this).lanzarItemMenu(id_usuario, contraseña,
				ventanaNombre);
	}

	/**
	 * Método que lanza la vista de login
	 */
	private void lanzarLogin() {
		SharedPreferences settings = getSharedPreferences("MisDatos",
				Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = settings.edit();
		editor.putString("id_usuario", "");
		editor.putString("contraseña", "");
		editor.commit();
		new LanzarVista(this).lanzarLogin();
	}

	/**
	 * Añade la vista de administrar cuenta. Recoge el usuario y la contraseña
	 * de la vista login. Inicializa parametros
	 * */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_administrar_cuenta);
		Bundle bundle = getIntent().getExtras();
		id_usuario = bundle.getString("id_usuario");
		contraseña = bundle.getString("contraseña");
		ListView lista = (ListView) findViewById(R.id.listaAdministracionCuenta);
		ArrayList<String> items = new ArrayList<String>();
		items.add("\n Modificar usuario\n");
		items.add("\n Cambiar contraseña\n");
		items.add("\n Eliminar cuenta\n");
		adapter = new AdapterListaMenu(this, items);
		lista.setAdapter(adapter);
		/** Método que se utiliza para hacer click en la liste de menus */
		lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				String item = adapter.getItem(position);
				elegirMenu(item);
			}
		});
	}

	// TODO En la base de datos cloud ay q eliminar los vacas y los medicamentos
	// cuando se elimina el usuario. ESTE METODO NO ARIA FALTA
	private void listaVacas(final String id_usuario) {
		listaVacas = new ArrayList<Vaca>();

		Thread hilo = new Thread() {
			String res = "";
			Gson json = new GsonBuilder().setPrettyPrinting()
					.setDateFormat("dd-MM-yyyy").create();
			LlamadaVacaWS llamada = new LlamadaVacaWS();

			public void run() {
				res = llamada.LlamadaListaVacas(id_usuario);

				listaVacas = json.fromJson(res,
						new TypeToken<ArrayList<Vaca>>() {
						}.getType());
			}
		};
		hilo.start();
	}

	// TODO En la base de datos cloud ay q eliminar los vacas y los medicamentos
	// cuando se elimina el usuario. ESTE METODO NO ARIA FALTA
	private void eliminarVacas(final String id_usuario) {
		listaVacas(id_usuario);
		for (int i = 0; i < listaVacas.size(); i++) {
			eliminarMedicamentosVaca(listaVacas.get(i).getId_vaca());
		}
		Thread hilo = new Thread() {
			LlamadaVacaWS llamada = new LlamadaVacaWS();

			public void run() {
				llamada.LLamadaEliminarVacas(id_usuario);
			}
		};
		hilo.start();
	}

	// TODO En la base de datos cloud ay q eliminar los vacas y los medicamentos
	// cuando se elimina el usuario. ESTE METODO NO ARIA FALTA
	private void eliminarMedicamentosVaca(final String id_vaca) {
		Thread hilo = new Thread() {
			LlamadaMedicamentoWS llamadaMedicamento = new LlamadaMedicamentoWS();

			public void run() {
				llamadaMedicamento.LLamadaEliminarMedicamentos(id_vaca);
			}
		};
		hilo.start();
	}
}