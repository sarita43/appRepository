package com.example.misvacasapp;

import java.util.ArrayList;

import com.example.misvacasapp.bbddinterna.MedicamentoDatosBbdd;
import com.example.misvacasapp.bbddinterna.VacaDatosBbdd;
import com.example.misvacasapp.llamadaWS.LlamadaMedicamentoWS;
import com.example.misvacasapp.llamadaWS.LlamadaVacaWS;
import com.example.misvacasapp.modelo.Medicamento;
import com.example.misvacasapp.modelo.Vaca;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Clase de la actividad del medicamento En ella se implementan los métodos que
 * se utilizan para manejar la vista del medicamento
 * 
 * @author Sara Martinez Lopez
 * */
public class MedicamentoVista extends ActionBarActivity {
	// Atributos
	/** Id del medicamento */
	private String id_medicamento;
	/** Id de la vaca */
	private String id_vaca;

	// Metodos
	/**
	 * Añade la vista del medicamento Recoge el id del medicamento y el id de la
	 * vaca de la vista medicamento Inicializa parametros
	 * */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_medicamento);
		Bundle bundle = getIntent().getExtras();
		id_medicamento = Integer.toString(bundle.getInt("id_medicamento"));
		id_vaca = bundle.getString("id_vaca");
		rellenarCampos();
	}

	/**
	 * Rellena los campos de la vista del medicamento Llama al servicio web para
	 * recoger los campos
	 * 
	 * @see onCreate
	 * */
	private void rellenarCampos() {
		MedicamentoDatosBbdd mdatos = new MedicamentoDatosBbdd(
				getApplicationContext());
		Medicamento medicamento = mdatos
				.getMedicamento(id_medicamento, id_vaca);

		TextView idMedicamento = (TextView) findViewById(R.id.id_medicamento);
		idMedicamento.setText("ID MEDICAMENTO: "
				+ medicamento.getId_medicamento());
		TextView fecha = (TextView) findViewById(R.id.fecha_medicamento);
		fecha.setText("FECHA: " + medicamento.getFecha());
		TextView tipo = (TextView) findViewById(R.id.tipo_medicamento);
		tipo.setText("TIPO: " + medicamento.getTipo());
		TextView descripcion = (TextView) findViewById(R.id.descripcion);
		descripcion.setText("DESCRIPCION: " +"\n "+ medicamento.getDescripcion());

	}
	
	public void sincronizar(final String usuario) {
		Thread hilo = new Thread() {
			public void run() {
				VacaDatosBbdd vdatos = new VacaDatosBbdd(
						
						getApplicationContext());
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
								MedicamentoVista.this,
								"La cuenta esta siendo sincronizada. Esto puede tardar unos minutos",
								Toast.LENGTH_LONG).show();
					}
				});
				LlamadaVacaWS llamadaVaca = new LlamadaVacaWS();
				LlamadaMedicamentoWS llamadaMedicamento = new LlamadaMedicamentoWS();
				for (int i = 0; i < listaVacas.size(); i++) {
					//Eliminar medicamentos base de datos cloud
					eliminarMedicamentos(listaVacas.get(i).getId_vaca());
					//Eliminar vaca base de datos cloud
					llamadaVaca.LLamadaEliminarVaca(listaVacas.get(i).getId_vaca(), listaVacas.get(i).getId_usuario());
					
					//Añadir vaca a base de datos cloud
					String vaca = json.toJson(listaVacas.get(i));
					llamadaVaca.LLamadaAñadirVaca(vaca);
					//Guarda los medicamentos en una lista
					ArrayList<Medicamento> listaAux = mdatos.getMedicamentos(listaVacas.get(i).getId_vaca());
					for (int j = 0; j < listaAux.size(); j++) {
						listaMedicamentosUsuario.add(listaAux.get(j));
					}
					
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
						Toast.makeText(MedicamentoVista.this,
								"La cuenta ha siendo sincronizada",
								Toast.LENGTH_LONG).show();
					}
				});
			}
		};
		hilo.start();
	}
	
	private void eliminarMedicamentos(String id_vaca){
		LlamadaMedicamentoWS llamadaMedicamento = new LlamadaMedicamentoWS();
		llamadaMedicamento.LLamadaEliminarMedicamentos(id_vaca);
	}

	/**
	 * Añade el menu a la vista login
	 * 
	 * @param menu
	 * */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_vaca, menu);
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
		} else if (id == R.id.mis_vacas) {

			String id_usuarioGuardado = settings.getString("id_usuario", "");
			String contraseñaGuardado = settings.getString("contraseña", "");
			new LanzarVista(this).lanzarUsuarioVista(id_usuarioGuardado,
					contraseñaGuardado);
			finish();
			return true;
		} else if (id == R.id.administrar_cuenta) {
			String id_usuarioGuardado = settings.getString("id_usuario", "");
			String contraseñaGuardado = settings.getString("contraseña", "");
			new LanzarVista(this).lanzarAdministrarCuenta(id_usuarioGuardado,
					contraseñaGuardado);
			return true;
		} else if (id == R.id.sincronizar_cuenta) {
			sincronizar(settings.getString("id_usuario", ""));
		} else if (id == R.id.ayuda) {
			AlertDialog.Builder alerta = new AlertDialog.Builder(this);
			alerta.setTitle("Ayuda");
			alerta.setMessage("Ficha del animal."
					+ "\n"
					+ "Para ver sus medicamentos, pulse sobre el botón de medicamentos.");
			alerta.show();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}