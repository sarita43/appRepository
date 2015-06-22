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
import com.example.misvacasapp.llamadaWS.LlamadaProduccionWS;
import com.example.misvacasapp.llamadaWS.LlamadaVacaWS;
import com.example.misvacasapp.modelo.Medicamento;
import com.example.misvacasapp.modelo.Produccion;
import com.example.misvacasapp.modelo.Vaca;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class ProducionVista extends ActionBarActivity {

	// ----------------------------------Atributos-----------------------------//
	/** Base de datos de la producción */
	private ProduccionDatosBbdd pdbbdd;
	/** Lista de la producción de carne */
	private ArrayList<Produccion> listaCarne;
	/** Lista de la producción de leche */
	private ArrayList<Produccion> listaLeche;
	/** 30 días en segundos 86400000=1000s*3600m*24h */
	private static final long DAYS = 86400000 * 30;

	/** Formato de la fecha */
	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

	// ---------------------------------Métodos------------------------------//
	/**
	 * Método que muestra el grafico de la carne. Se visualiza cuando se pulsa
	 * en el botón de carne
	 * 
	 * @param v
	 *            View
	 */
	public void mostrarGraficoCarne(View v) {
		Button boton1 = (Button) findViewById(R.id.button1);
		Button boton2 = (Button) findViewById(R.id.button2);
		boton2.setBackgroundColor(Color.rgb(237, 229, 229));
		boton1.setBackgroundResource(R.drawable.fondo_blanco);
		// Trae la lista de la producción de carne
		getListaCarne();

		// Rellena los valores de la gráfica con la lista de la producción de la
		// carne
		TimeSeries series = new TimeSeries("Carne(Kg)");
		for (int i = 0; i < listaCarne.size(); i++) {
			series.add(listaCarne.get(i).getFecha(), listaCarne.get(i)
					.getCantidad());
		}
		// Crea el render de la gráfica
		XYSeriesRenderer renderer = new XYSeriesRenderer();
		renderer.setLineWidth(2);
		renderer.setColor(Color.RED);
		renderer.setDisplayBoundingPoints(true);
		renderer.setPointStyle(PointStyle.CIRCLE);
		renderer.setFillPoints(true);
		renderer.setDisplayBoundingPoints(true);

		// Crea el multi render
		XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer();
		mRenderer.addSeriesRenderer(renderer);
		mRenderer.setMarginsColor(Color.argb(0x00, 0xff, 0x00, 0x00)); // transparent Color.argb(0x00, 0xff, 0x00, 0x00)

		mRenderer.setZoomButtonsVisible(true);

		// Fecha minima a mostrar al inicializar el gráfico
		java.util.Date minDia = new java.util.Date((long) series.getMaxX());
		minDia = new java.util.Date(minDia.getYear(), minDia.getMonth() - 1,
				minDia.getDay());

		// Valores de los eje X e Y
		mRenderer.setYAxisMax(series.getMaxY());
		mRenderer.setYAxisMin(0);
		mRenderer.setXAxisMax(series.getMaxX()+10);
		mRenderer.setXAxisMin((double) minDia.getTime());
		mRenderer.setShowGrid(true);

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

		// Crea la vista del gráfico
		final GraphicalView chartView = ChartFactory.getTimeChartView(
				getApplicationContext(), dataset, mRenderer, "d/M/yy");

		// Click en el gráfico
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

		// Añadir la vista al layout
		chartLyt.addView(chartView, 0);
	}

	/**
	 * Método que muestra el grafico de la leche. Se visualiza cuendo se pulsa
	 * en el botón de leche
	 * 
	 * @param v
	 *            View
	 */
	public void mostrarGraficoLeche(View v) {
		Button boton1 = (Button) findViewById(R.id.button1);
		Button boton2 = (Button) findViewById(R.id.button2);
		boton1.setBackgroundColor(Color.rgb(237, 229, 229));
		boton2.setBackgroundResource(R.drawable.fondo_blanco);
		// Trae la lista de la leche
		getListaLeche();
		// Rellena los valores de la gráfica con la lista de la producción de la
		// leche
		TimeSeries series = new TimeSeries("Leche(L)");
		for (int i = 0; i < listaLeche.size(); i++) {
			series.add(listaLeche.get(i).getFecha(), listaLeche.get(i)
					.getCantidad());
		}

		// Crea el render de la gráfica
		XYSeriesRenderer renderer = new XYSeriesRenderer();
		renderer.setLineWidth(3);
		renderer.setColor(Color.BLUE);
		renderer.setDisplayBoundingPoints(true);
		renderer.setPointStyle(PointStyle.CIRCLE);
		renderer.setFillPoints(true);
        renderer.setDisplayChartValuesDistance(10);

		// Crea el multi render
		XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer();
		mRenderer.addSeriesRenderer(renderer);
		mRenderer.setMarginsColor(Color.rgb(255, 255, 255));// transparent
		mRenderer.setZoomButtonsVisible(true);

		// Fecha minima a mostrar al inicializar el gráfico
		java.util.Date minDia = new java.util.Date((long) series.getMaxX());
		minDia = new java.util.Date(minDia.getYear(), minDia.getMonth() - 1,
				minDia.getDay());

		// Valores de los eje X e Y
		mRenderer.setYAxisMax(series.getMaxY());
		mRenderer.setYAxisMin(0);
		mRenderer.setXAxisMax(series.getMaxX());
		mRenderer.setXAxisMin((double) minDia.getTime());
		mRenderer.setShowGrid(true);

		mRenderer.setYTitle("Litros");
		mRenderer.setXTitle("Dias");
		mRenderer.setYLabelsColor(0, Color.BLACK);
		mRenderer.setXLabelsColor(Color.BLACK);
		mRenderer.setLabelsColor(Color.BLACK);
		mRenderer.setLabelsTextSize(18);

		mRenderer.setPanEnabled(true, true);
		mRenderer.setPanLimits(new double[] { series.getMinX() + DAYS,
				series.getMaxX() - DAYS, 0, series.getMaxY() });

		mRenderer.setClickEnabled(true);

		// Layout de la vista
		LinearLayout chartLyt = (LinearLayout) findViewById(R.id.chart);

		// Crea el multi datos
		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		dataset.addSeries(series);

		// Crea la vista del gráfico
		final GraphicalView chartView = ChartFactory.getTimeChartView(
				getApplicationContext(), dataset, mRenderer, "d/M/yy");

		// Click en el gráfico
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
		// Añadir la vista al layout
		chartLyt.addView(chartView, 0);
	}

	/**
	 * Método que se ejecuta cuando se pulsa el en botón de añadir. Muestra una
	 * areta para añadir una nueva producción
	 * 
	 * @param v
	 *            View
	 */
	public void onClickAñadirProduccion(View v) {
		LayoutInflater inflater = LayoutInflater.from(this);
		final View layout = inflater.inflate(R.layout.aniadir_produccion, null);
		rellenarSpinner(layout);
		AlertDialog.Builder dialogo = new AlertDialog.Builder(this);
		dialogo.setView(layout);
		dialogo.setMessage("Añadir producción");
		dialogo.setPositiveButton("Añadir", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				if (comprobarFecha(layout)) {
					añadirProduccion(layout);
				}
			}
		});
		dialogo.show();
	}

	/**
	 * Método que añade una producción a la base de datos
	 * 
	 * @param layout
	 *            View. Alerta que contiene los datos de añadir la producción
	 */
	@SuppressWarnings("deprecation")
	public void añadirProduccion(View layout) {
		int dia = Integer.parseInt(((EditText) layout
				.findViewById(R.id.dia_produccion)).getText().toString());
		int mes = Integer.parseInt(((EditText) layout
				.findViewById(R.id.mes_produccion)).getText().toString()) - 1;
		int año = Integer.parseInt(((EditText) layout
				.findViewById(R.id.anio_produccion)).getText().toString()) - 1900;

		Date fecha = new Date(año, mes, dia);
		String tipo = ((Spinner) layout.findViewById(R.id.spinner_produccion))
				.getSelectedItem().toString();
		int cantidad = Integer.parseInt(((EditText) layout
				.findViewById(R.id.cantidad_produccion)).getText().toString());
		SharedPreferences settings = getSharedPreferences("MisDatos",
				Context.MODE_PRIVATE);
		Produccion produccion = new Produccion(0, fecha, tipo,
				settings.getString("id_usuario", ""), cantidad);
		pdbbdd.añadirProduccion(produccion);
		if (tipo.compareTo("Leche") == 0) {
			mostrarGraficoLeche(layout);
		} else {
			mostrarGraficoCarne(layout);
		}
	}

	/**
	 * Rellena el spinner del tipo de producción para mostrar en añadir la
	 * producción
	 * 
	 * @param layout
	 *            View. Alerta que contiene los datos de añadir la producción
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
	 * Método que trae la lista de producción de carne de la base de datos
	 */
	public void getListaCarne() {
		listaCarne = pdbbdd.getListaCarne();
	}

	/**
	 * Método que trae la lista de producción de leche de la base de datos
	 */
	public void getListaLeche() {
		listaLeche = pdbbdd.getListaLeche();
	}

	/**
	 * Método que comprueba si la fecha introducida por el usuario es correcta.
	 * Comprueba que este entre los valores de los dias posibles, los meses
	 * posibles y el año que no supere al año actual. También comprueba que se
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
					.findViewById(R.id.mes_produccion)).getText().toString()) - 1;
			int año = Integer.parseInt(((TextView) layout
					.findViewById(R.id.anio_produccion)).getText().toString()) - 1900;
			int añoActual = new java.util.Date().getYear();
			int mesActual = new java.util.Date().getMonth();
			int diaActual = new java.util.Date().getDate();
			if (dia <= 31 && mes <= 12 && año < añoActual) {
				fechaOk = true;

			} else if (año == añoActual) {
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
	 * Método que sincroniza base de datos interna con la cloud. Deja la base de
	 * datos cloud como la base de datos interna.
	 * 
	 * @param usuario
	 *            String id_usuario
	 */
	public void sincronizar(final String usuario) {
		Thread hilo = new Thread() {
			public void run() {
				//Base de datos interna
				VacaDatosBbdd vdatos = new VacaDatosBbdd(
						getApplicationContext());
				ArrayList<Vaca> listaVacas = new ArrayList<Vaca>();
				listaVacas = vdatos.getListaVacas(usuario);

				MedicamentoDatosBbdd mdatos = new MedicamentoDatosBbdd(
						getApplicationContext());
				ArrayList<Medicamento> listaMedicamentosUsuario = new ArrayList<Medicamento>();

				ProduccionDatosBbdd pdbbdd = new ProduccionDatosBbdd(getApplicationContext());
				ArrayList<Produccion> listaProduccion = new ArrayList<Produccion>();
				
				
				Gson json = new GsonBuilder().setPrettyPrinting()
						.setDateFormat("dd-MM-yyyy").create();
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						Toast.makeText(
								getApplicationContext(),
								"La cuenta esta siendo sincronizada. Esto puede tardar unos minutos",
								Toast.LENGTH_LONG).show();
					}
				});
				
				for (int i = 0; i < listaVacas.size(); i++) {
					// Guarda los medicamentos en una lista
					ArrayList<Medicamento> listaAux = mdatos
							.getMedicamentos(listaVacas.get(i).getId_vaca());
					for (int j = 0; j < listaAux.size(); j++) {
						listaMedicamentosUsuario.add(listaAux.get(j));
					}
				}

				// Eliminar vaca base de datos cloud
				LlamadaVacaWS llamadaVaca = new LlamadaVacaWS();
				LlamadaMedicamentoWS llamadaMedicamento = new LlamadaMedicamentoWS();
				llamadaVaca.LLamadaEliminarVacas(usuario);

				// Añadir vaca a base de datos cloud
				for (int i = 0; i < listaVacas.size(); i++) {
					String vaca = json.toJson(listaVacas.get(i));
					llamadaVaca.LLamadaAñadirVaca(vaca);
				}

				// Añadir medicamentos a base de datos cloud
				for (int i = 0; i < listaMedicamentosUsuario.size(); i++) {
					Medicamento m = listaMedicamentosUsuario.get(i);
					String medicamento = json.toJson(m);
					llamadaMedicamento.LLamadaAñadirMedicamento(medicamento);
				}
				
				//Añadir produccion a la base de datos cloud
				listaProduccion = pdbbdd.getProducciones();
				LlamadaProduccionWS llamadaProducciones = new LlamadaProduccionWS();
				llamadaProducciones.setProducciones(json.toJson(listaProduccion),usuario);
				
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						Toast.makeText(getApplicationContext(),
								"La cuenta ha siendo sincronizada",
								Toast.LENGTH_LONG).show();
					}
				});
			}
		};
		hilo.start();
	}

	/**
	 * Añade la vista de gráficos de la producción. Inicializa paráetros
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
	 * Añade el menu a la vista producción
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
		} else if (id == R.id.produccion) {
			new LanzarVista(this).lanzarProduccion();
			finish();
			return true;
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
			alerta.setMessage("Graficos de producción de leche y carne."
					+ "\n\n"
					+ "Para ver la producción de leche pulsar el botón de leche."
					+ "\n\n"
					+ "Para ver la producción de carne pulsar el botón de carne."
					+ "\n\n"
					+ "Para añadir una nueva producción pulsar el botón de añadir.");
			alerta.show();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}