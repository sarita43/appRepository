package com.example.misvacasapp.calendario;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import com.example.misvacasapp.LanzarVista;
import com.example.misvacasapp.R;
import com.example.misvacasapp.adapter.AdapterCalendario;
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
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Adapter;
import android.widget.CalendarView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

public class CalendarioReproducionesVista extends ActionBarActivity {
	
	//-------------------------------Atributos-----------------------------------//
	private CalendarView calendario;
	private String[] meses = { "Enero", "Febrero", "Marzo", "Abril", "Mayo",
			"Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre",
			"Diciembre" };

	//---------------------------------M�todos-----------------------------------//
	/**
	 * M�todo que sincroniza base de datos interna con la cloud. Deja la base de
	 * datos cloud como la base de datos interna.
	 * 
	 * @param usuario
	 *            String id_usuario
	 */
	public void sincronizar(final String usuario) {
		Thread hilo = new Thread() {
			public void run() {
				VacaDatosBbdd vdatos = new VacaDatosBbdd(
						getApplicationContext());
				ArrayList<Vaca> listaVacas = new ArrayList<Vaca>();
				listaVacas = vdatos.getListaVacas(usuario);

				MedicamentoDatosBbdd mdatos = new MedicamentoDatosBbdd(
						getApplicationContext());
				ArrayList<Medicamento> listaMedicamentosUsuario = new ArrayList<Medicamento>();

				Gson json = new GsonBuilder().setPrettyPrinting()
						.setDateFormat("dd-MM-yyyy").create();
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						Toast.makeText(
								CalendarioReproducionesVista.this,
								"La cuenta esta siendo sincronizada. Esto puede tardar unos minutos",
								Toast.LENGTH_LONG).show();
					}
				});
				LlamadaVacaWS llamadaVaca = new LlamadaVacaWS();
				LlamadaMedicamentoWS llamadaMedicamento = new LlamadaMedicamentoWS();
				for (int i = 0; i < listaVacas.size(); i++) {
					// Guarda los medicamentos en una lista
					ArrayList<Medicamento> listaAux = mdatos
							.getMedicamentos(listaVacas.get(i).getId_vaca());
					for (int j = 0; j < listaAux.size(); j++) {
						listaMedicamentosUsuario.add(listaAux.get(j));
					}
				}

				// Eliminar vaca base de datos cloud
				llamadaVaca.LLamadaEliminarVacas(usuario);

				// A�adir vaca a base de datos cloud
				for (int i = 0; i < listaVacas.size(); i++) {
					String vaca = json.toJson(listaVacas.get(i));
					llamadaVaca.LLamadaA�adirVaca(vaca);
				}

				// A�adir medicamentos a base de datos cloud
				for (int i = 0; i < listaMedicamentosUsuario.size(); i++) {
					Medicamento m = listaMedicamentosUsuario.get(i);
					String medicamento = json.toJson(m);
					llamadaMedicamento.LLamadaA�adirMedicamento(medicamento);
				}

				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						Toast.makeText(CalendarioReproducionesVista.this,
								"La cuenta ha siendo sincronizada",
								Toast.LENGTH_LONG).show();
					}
				});
			}
		};
		hilo.start();
	}


	/**
	 * A�ade la vista usuario. Recoge el usuario y la contrase�a de la vista
	 * login. Inicializa parametros
	 * */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.calendario);
		calendario = (CalendarView)findViewById(R.id.calendarView1); 
		calendario.setDateTextAppearance(9);
		//Por que dia de la semana empieza
		calendario.setFirstDayOfWeek(2);
		//Color todos los dias del mes seleccionado
		calendario.setFocusedMonthDateColor(Color.BLUE);
		//calendario.setSelectedDateVerticalBar(Color.MAGENTA);
		calendario.setSelectedWeekBackgroundColor(Color.TRANSPARENT);
		//Muestra el numero de las semanas o no
		calendario.setShowWeekNumber(true);
		//Color del mes que no esta seleccionado
		calendario.setUnfocusedMonthDateColor(Color.MAGENTA);
		calendario.setWeekDayTextAppearance(DEFAULT_KEYS_DIALER);
		calendario.setWeekNumberColor(Color.RED);
		calendario.setWeekSeparatorLineColor(Color.RED);
		
//		
//		calendario = (GridView)findViewById(R.id.gridView1); 
//		calendario.setAdapter(new AdapterCalendario(this,new Date().getDay(),new Date().getMonth(),new Date().getYear()+1990));
//		TextView mes_a�o = (TextView) findViewById(R.id.mes_anio_text);
//		mes_a�o.setText(meses[new Date().getMonth()]+"  "+new Date().getYear());
	}

	/**
	 * A�ade el menu a la vista usuario
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
			new LanzarVista(this).lanzarUsuarioVista(
					settings.getString("id_usuario", ""),
					settings.getString("contrase�a", ""));
			finish();
			return true;
		} else if (id == R.id.administrar_cuenta) {
			new LanzarVista(this).lanzarAdministrarCuenta(
					settings.getString("id_usuario", ""),
					settings.getString("contrase�a", ""));
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
