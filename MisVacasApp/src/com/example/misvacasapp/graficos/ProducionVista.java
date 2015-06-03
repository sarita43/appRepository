package com.example.misvacasapp.graficos;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.SeriesSelection;
import org.achartengine.model.TimeSeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import com.example.misvacasapp.LanzarVista;
import com.example.misvacasapp.R;
import com.example.misvacasapp.bbddinterna.MedicamentoDatosBbdd;
import com.example.misvacasapp.bbddinterna.ProduccionDatosBbdd;
import com.example.misvacasapp.bbddinterna.VacaDatosBbdd;
import com.example.misvacasapp.llamadaWS.LlamadaMedicamentoWS;
import com.example.misvacasapp.llamadaWS.LlamadaVacaWS;
import com.example.misvacasapp.modelo.Medicamento;
import com.example.misvacasapp.modelo.Produccion;
import com.example.misvacasapp.modelo.Vaca;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import android.R.layout;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.DialogInterface.OnClickListener;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class ProducionVista extends ActionBarActivity {

	// ----------------------------------Atributos-----------------------------//
	/** Base de datos de la producci�n */
	private ProduccionDatosBbdd pdbbdd;
	/** Lista de la producci�n de carne */
	private ArrayList<Produccion> listaCarne;
	/** Lista de la producci�n de leche */
	private ArrayList<Produccion> listaLeche;
	/** 30 d�as en segundos 86400000=1000s*3600m*24h */
	private static final long DAYS = 86400000 * 30;

	/** Formato de la fecha */
	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

	// ---------------------------------M�todos------------------------------//
	/**
	 * M�todo que muestra el grafico de la carne. Se visualiza cuando se pulsa
	 * en el bot�n de carne
	 * 
	 * @param v
	 *            View
	 */
	public void mostrarGraficoCarne(View v) {
		// Trae la lista de la producci�n de carne
		getListaCarne();

		// Rellena los valores de la gr�fica con la lista de la producci�n de la
		// carne
		TimeSeries series = new TimeSeries("Carne(Kg)");
		for (int i = 0; i < listaCarne.size(); i++) {
			series.add(listaCarne.get(i).getFecha(), listaCarne.get(i)
					.getCantidad());
		}
		// Crea el render de la gr�fica
		XYSeriesRenderer renderer = new XYSeriesRenderer();
		renderer.setLineWidth(2);
		renderer.setColor(Color.RED);
		renderer.setDisplayBoundingPoints(true);
		renderer.setPointStyle(PointStyle.CIRCLE);
		renderer.setFillPoints(true);
		renderer.setDisplayChartValues(true);

		// Crea el multi render
		XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer();
		mRenderer.addSeriesRenderer(renderer);
		mRenderer.setMarginsColor(Color.argb(0x00, 0xff, 0x00, 0x00)); // transparent

		mRenderer.setZoomButtonsVisible(true);

		mRenderer.setYAxisMax(series.getMaxY());
		mRenderer.setYAxisMin(0);
		mRenderer.setXAxisMax(series.getMaxX());
		mRenderer.setXAxisMin(series.getMaxX() + DAYS);
		mRenderer.setShowGrid(true); // we show the grid

		mRenderer.setYTitle("Kg");
		mRenderer.setXTitle("Dias");
		mRenderer.setYLabelsColor(0, Color.BLACK);
		mRenderer.setXLabelsColor(Color.BLACK);
		mRenderer.setLabelsColor(Color.BLACK);
		mRenderer.setLabelsTextSize(16);

		mRenderer.setPanEnabled(true, true);
		mRenderer.setPanLimits(new double[] { series.getMinX() + DAYS,
				series.getMaxX() - DAYS, 0, series.getMaxY() });

		mRenderer.setClickEnabled(true);

		// Layout de la vista
		LinearLayout chartLyt = (LinearLayout) findViewById(R.id.chart);

		// Crea el multi datos
		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		dataset.addSeries(series);

		// Crea la vista del gr�fico
		final GraphicalView chartView = ChartFactory.getTimeChartView(
				getApplicationContext(), dataset, mRenderer, "d/M/yy");

		// Click en el gr�fico
		chartView.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				SeriesSelection seriesSelection = chartView
						.getCurrentSeriesAndPoint();
				if (seriesSelection != null) {
					Date day = new Date((long) seriesSelection.getXValue());
					String dia = sdf.format(day);
					int amount = (int) seriesSelection.getValue();

					Toast.makeText(getBaseContext(),
							dia + " : " + amount + "Kg", Toast.LENGTH_SHORT)
							.show();
				}
			}
		});

		// A�adir la vista al layout
		chartLyt.addView(chartView, 0);
	}

	/**
	 * M�todo que muestra el grafico de la leche. Se visualiza cuendo se pulsa
	 * en el bot�n de leche
	 * 
	 * @param v
	 *            View
	 */
	public void mostrarGraficoLeche(View v) {
		// Trae la lista de la leche
		getListaLeche();
		java.util.Date minDia = new java.util.Date();
		// Rellena los valores de la gr�fica con la lista de la producci�n de la
		// leche
		TimeSeries series = new TimeSeries("Leche(L)");
		for (int i = 0; i < listaLeche.size(); i++) {
			System.out.println(listaLeche.get(i).getFecha());
			System.out.println(listaLeche.get(i).getCantidad());
			series.add(listaLeche.get(i).getFecha(), listaLeche.get(i)
					.getCantidad());
		}

		// Crea el render de la gr�fica
		XYSeriesRenderer renderer = new XYSeriesRenderer();
		renderer.setLineWidth(2);
		renderer.setColor(Color.BLUE);
		renderer.setDisplayBoundingPoints(true);
		renderer.setPointStyle(PointStyle.CIRCLE);
		renderer.setFillPoints(true);
		renderer.setDisplayChartValues(true);

		// Crea el multi render
		XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer();
		mRenderer.addSeriesRenderer(renderer);
		mRenderer.setMarginsColor(Color.argb(0x00, 0xff, 0x00, 0x00)); // transparent
		mRenderer.setZoomButtonsVisible(true);

		mRenderer.setYAxisMax(series.getMaxY());
		mRenderer.setYAxisMin(0);
		mRenderer.setXAxisMax(series.getMaxX());
		mRenderer.setXAxisMin(minDia.getTime());
		mRenderer.setShowGrid(true);

		mRenderer.setYTitle("Litros");
		mRenderer.setXTitle("Dias");
		mRenderer.setYLabelsColor(0, Color.BLACK);
		mRenderer.setXLabelsColor(Color.BLACK);
		mRenderer.setLabelsColor(Color.BLACK);
		mRenderer.setLabelsTextSize(16);

		mRenderer.setPanEnabled(true, true);
		mRenderer.setPanLimits(new double[] { series.getMinX() + DAYS,
				series.getMaxX() - DAYS, 0, series.getMaxY() });

		mRenderer.setClickEnabled(true);

		// Layout de la vista
		LinearLayout chartLyt = (LinearLayout) findViewById(R.id.chart);

		// Crea el multi datos
		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		dataset.addSeries(series);

		// Crea la vista del gr�fico
		final GraphicalView chartView = ChartFactory.getTimeChartView(
				getApplicationContext(), dataset, mRenderer, "d/M/yy");

		// Click en el gr�fico
		chartView.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				SeriesSelection seriesSelection = chartView
						.getCurrentSeriesAndPoint();
				if (seriesSelection != null) {
					Date day = new Date((long) seriesSelection.getXValue());
					String dia = sdf.format(day);
					int amount = (int) seriesSelection.getValue();

					Toast.makeText(getBaseContext(),
							dia + " : " + amount + "L", Toast.LENGTH_SHORT)
							.show();
				}
			}
		});
		// A�adir la vista al layout
		chartLyt.addView(chartView, 0);
	}

	/**
	 * M�todo que se ejecuta cuando se pulsa el en bot�n de a�adir. Muestra una
	 * areta para a�adir una nueva producci�n
	 * 
	 * @param v
	 *            View
	 */
	public void onClickA�adirProduccion(View v) {
		LayoutInflater inflater = LayoutInflater.from(this);
		final View layout = inflater.inflate(R.layout.aniadir_produccion, null);
		rellenarSpinner(layout);
		AlertDialog.Builder dialogo = new AlertDialog.Builder(this);
		dialogo.setView(layout);
		dialogo.setMessage("A�adir producci�n");
		dialogo.setPositiveButton("A�adir", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				if (comprobarFecha(layout)) {
					a�adirProduccion(layout);
				}
			}
		});
		dialogo.show();
	}

	/**
	 * M�todo que a�ade una producci�n a la base de datos
	 * 
	 * @param layout
	 *            View. Alerta que contiene los datos de a�adir la producci�n
	 */
	@SuppressWarnings("deprecation")
	public void a�adirProduccion(View layout) {
		int dia = Integer.parseInt(((EditText) layout
				.findViewById(R.id.dia_produccion)).getText().toString());
		int mes = Integer.parseInt(((EditText) layout
				.findViewById(R.id.mes_produccion)).getText().toString()) - 1;
		System.out.println("MES"+mes);
		int a�o = Integer.parseInt(((EditText) layout
				.findViewById(R.id.anio_produccion)).getText().toString()) - 1900;
		System.out.println("a�o"+a�o);

		Date fecha = new Date(a�o, mes, dia);
		String tipo = ((Spinner) layout.findViewById(R.id.spinner_produccion))
				.getSelectedItem().toString();
		int cantidad = Integer.parseInt(((EditText) layout
				.findViewById(R.id.cantidad_produccion)).getText().toString());
		SharedPreferences settings = getSharedPreferences("MisDatos",
				Context.MODE_PRIVATE);
		Produccion produccion = new Produccion(0, fecha, tipo,
				settings.getString("id_usuario", ""), cantidad);
		pdbbdd.a�adirProduccion(produccion);
	}

	/**
	 * Rellena el spinner del tipo de producci�n para mostrar en a�adir la
	 * producci�n
	 * 
	 * @param layout
	 *            View. Alerta que contiene los datos de a�adir la producci�n
	 */
	@SuppressWarnings("rawtypes")
	private void rellenarSpinner(View layout) {
		Spinner spinner = (Spinner) layout
				.findViewById(R.id.spinner_produccion);
		ArrayAdapter adapter = ArrayAdapter.createFromResource(this,
				R.array.tipo_produccion,
				android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
	}

	/**
	 * M�todo que trae la lista de producci�n de carne de la base de datos
	 */
	public void getListaCarne() {
		listaCarne = pdbbdd.getListaCarne();
	}

	/**
	 * M�todo que trae la lista de producci�n de leche de la base de datos
	 */
	public void getListaLeche() {
		listaLeche = pdbbdd.getListaLeche();
	}

	/**
	 * M�todo que comprueba si la fecha introducida por el usuario es correcta.
	 * Comprueba que este entre los valores de los dias posibles, los meses
	 * posibles y el a�o que no supere al a�o actual. Tambi�n comprueba que se
	 * introduzca algun valor en la fecha
	 * 
	 * @param layout
	 * 
	 * @return boolean Fecha correcta true fecha incorrecta false
	 */
	@SuppressWarnings("deprecation")
	private boolean comprobarFecha(View layout) {
		boolean fechaOk = false;

		if (((TextView) layout.findViewById(R.id.dia_produccion)).getText()
				.toString().equals("")
				|| ((TextView) layout.findViewById(R.id.mes_produccion))
						.getText().toString().equals("")
				|| ((TextView) layout.findViewById(R.id.anio_produccion))
						.getText().toString().equals("")) {
			fechaOk = false;
			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					Toast.makeText(ProducionVista.this, "Fecha incorrecta",
							Toast.LENGTH_SHORT).show();
				}
			});
		} else {
			int dia = Integer.parseInt(((TextView) layout
					.findViewById(R.id.dia_produccion)).getText().toString());
			int mes = Integer.parseInt(((TextView) layout
					.findViewById(R.id.mes_produccion)).getText().toString());
			int a�o = Integer.parseInt(((TextView) layout
					.findViewById(R.id.anio_produccion)).getText().toString()) - 1900;
			int a�oActual = new java.util.Date().getYear();
			int mesActual = new java.util.Date().getMonth();
			int diaActual = new java.util.Date().getDate();
			System.out.println(mes+"    "+mesActual);
			System.out.println(a�o+"    "+a�oActual);
			if (dia <= 31 && mes <= 12 && a�o < a�oActual) {
				fechaOk = true;

			} else if (a�o == a�oActual) {
				if (dia <= diaActual && mes == mesActual) {
					fechaOk = true;
				} else if (mes < mesActual && dia <= 31) {
					fechaOk = true;
				} else {
					fechaOk = false;
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							Toast.makeText(ProducionVista.this,
									"Fecha incorrecta", Toast.LENGTH_SHORT)
									.show();
						}
					});
				}
			} else {
				fechaOk = false;
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						Toast.makeText(ProducionVista.this, "Fecha incorrecta",
								Toast.LENGTH_SHORT).show();
					}
				});
			}
		}

		return fechaOk;
	}

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
								ProducionVista.this,
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
						Toast.makeText(ProducionVista.this,
								"La cuenta ha siendo sincronizada",
								Toast.LENGTH_LONG).show();
					}
				});
			}
		};
		hilo.start();
	}

	/**
	 * A�ade la vista de gr�ficos de la producci�n. Inicializa par�etros
	 * 
	 * @param savedInstanceState
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_graficos);

		listaCarne = new ArrayList<Produccion>();
		listaLeche = new ArrayList<Produccion>();

		pdbbdd = new ProduccionDatosBbdd(this);

		mostrarGraficoLeche(getCurrentFocus());

	}

	/**
	 * A�ade el menu a la vista producci�n
	 * 
	 * @param menu
	 * */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_vaca, menu);
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
		} else if (id == R.id.mis_vacas) {

			String id_usuarioGuardado = settings.getString("id_usuario", "");
			String contrase�aGuardado = settings.getString("contrase�a", "");
			new LanzarVista(this).lanzarUsuarioVista(id_usuarioGuardado,
					contrase�aGuardado);
			finish();
			return true;
		} else if (id == R.id.administrar_cuenta) {
			String id_usuarioGuardado = settings.getString("id_usuario", "");
			String contrase�aGuardado = settings.getString("contrase�a", "");
			new LanzarVista(this).lanzarAdministrarCuenta(id_usuarioGuardado,
					contrase�aGuardado);
			return true;
		} else if (id == R.id.sincronizar_cuenta) {
			sincronizar(settings.getString("id_usuario", ""));
		} else if (id == R.id.ayuda) {
			AlertDialog.Builder alerta = new AlertDialog.Builder(this);
			alerta.setTitle("Ayuda");
			alerta.setMessage("Graficos de producci�n de leche y carne."
					+ "\n"
					+ "Para ver la producci�n de leche pulsar el bot�n de leche."
					+ "\n"
					+ "Para ver la producci�n de carne pulsar el bot�n de carne."
					+ "\n"
					+ "Para a�adir una nueva producci�n pulsar el bot�n de a�adir.");
			alerta.show();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}