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
import android.widget.Toast;

public class ProducionVista extends ActionBarActivity {

	// ----------------------------------Atributos-----------------------------//
	private ProduccionDatosBbdd pdbbdd;

	private ArrayList<Produccion> listaCarne;

	private ArrayList<Produccion> listaLeche;

	private static final long THREEDAYS = 86400000 * 30;
	
	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

	// public interface Constants {
	// String TAG = "com.example.graphactivity";
	// }

	public void mostrarGraficoCarne(View v) {

		TimeSeries series = new TimeSeries("Carne(Kg)");

		getListaCarne();

		for (int i = 0; i < listaCarne.size(); i++) {
			series.add(listaCarne.get(i).getFecha(), listaCarne.get(i)
					.getCantidad());
		}
		// Now we create the renderer
		XYSeriesRenderer renderer = new XYSeriesRenderer();
		renderer.setLineWidth(2);
		renderer.setColor(Color.RED);
		// Include low and max value
		renderer.setDisplayBoundingPoints(true);
		// we add point markers
		renderer.setPointStyle(PointStyle.CIRCLE);
		renderer.setFillPoints(true);
		renderer.setDisplayChartValues(true);

		XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer();
		mRenderer.addSeriesRenderer(renderer);

		// We want to avoid black border
		mRenderer.setMarginsColor(Color.argb(0x00, 0xff, 0x00, 0x00)); // transparent

		mRenderer.setZoomButtonsVisible(true);

		mRenderer.setYAxisMax(series.getMaxY() + 10);
		mRenderer.setYAxisMin(0);

		mRenderer.setXAxisMax(series.getMaxX());
		mRenderer.setXAxisMin(series.getMaxX() + THREEDAYS);

		mRenderer.setShowGrid(true); // we show the grid

		mRenderer.setYTitle("Kg");
		mRenderer.setXTitle("Dias");
		mRenderer.setYLabelsColor(0, Color.BLACK);
		mRenderer.setXLabelsColor(Color.BLACK);
		mRenderer.setLabelsColor(Color.BLACK);
		mRenderer.setLabelsTextSize(16);

		mRenderer.setPanEnabled(true, true);
		mRenderer.setPanLimits(new double[] { series.getMinX() + THREEDAYS,
				series.getMaxX() - THREEDAYS, 0, series.getMaxY() });

		mRenderer.setClickEnabled(true);

		LinearLayout chartLyt = (LinearLayout) findViewById(R.id.chart);

		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		dataset.addSeries(series);
		final GraphicalView chartView = ChartFactory.getTimeChartView(
				getApplicationContext(), dataset, mRenderer, "d/M/yy");

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
							dia+ " : " + amount + "Kg", Toast.LENGTH_SHORT)
							.show();
				}
			}
		});
		chartLyt.addView(chartView, 0);
	}

	public void mostrarGraficoLeche(View v) {
		TimeSeries series = new TimeSeries("Leche(L)");

		getListaLeche();

		for (int i = 0; i < listaLeche.size(); i++) {
			series.add(listaLeche.get(i).getFecha(), listaLeche.get(i)
					.getCantidad());
		}

		// Now we create the renderer
		XYSeriesRenderer renderer = new XYSeriesRenderer();
		renderer.setLineWidth(2);
		renderer.setColor(Color.BLUE);
		// Include low and max value
		renderer.setDisplayBoundingPoints(true);
		// we add point markers
		renderer.setPointStyle(PointStyle.CIRCLE);
		renderer.setFillPoints(true);
		renderer.setDisplayChartValues(true);

		XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer();
		mRenderer.addSeriesRenderer(renderer);

		// We want to avoid black border
		mRenderer.setMarginsColor(Color.argb(0x00, 0xff, 0x00, 0x00)); // transparent
																		// margins

		mRenderer.setZoomButtonsVisible(true);

		mRenderer.setYAxisMax(series.getMaxY() + 10);
		mRenderer.setYAxisMin(0);

		mRenderer.setXAxisMax(series.getMaxX());
		mRenderer.setXAxisMin(series.getMaxX() + THREEDAYS);

		mRenderer.setShowGrid(true); // we show the grid

		mRenderer.setYTitle("Litros");
		mRenderer.setXTitle("Dias");
		mRenderer.setYLabelsColor(0, Color.BLACK);
		mRenderer.setXLabelsColor(Color.BLACK);
		mRenderer.setLabelsColor(Color.BLACK);
		mRenderer.setLabelsTextSize(16);

		mRenderer.setPanEnabled(true, true);
		mRenderer.setPanLimits(new double[] { series.getMinX() + THREEDAYS,
				series.getMaxX() - THREEDAYS, 0, series.getMaxY() });

		mRenderer.setClickEnabled(true);

		LinearLayout chartLyt = (LinearLayout) findViewById(R.id.chart);
		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		dataset.addSeries(series);
		final GraphicalView chartView = ChartFactory.getTimeChartView(
				getApplicationContext(), dataset, mRenderer, "d/M/yy");

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
		chartLyt.addView(chartView, 0);

	}

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
				añadirProduccion(layout);
			}
		});
		dialogo.show();
	}

	public void añadirProduccion(View layout) {
		int dia = Integer.parseInt(((EditText) layout
				.findViewById(R.id.dia_produccion)).getText().toString());
		int mes = Integer.parseInt(((EditText) layout
				.findViewById(R.id.mes_produccion)).getText().toString());
		int año = Integer.parseInt(((EditText) layout
				.findViewById(R.id.anio_produccion)).getText().toString());
		Date fecha = new Date(año, mes, dia);
		String tipo = ((Spinner) layout.findViewById(R.id.spinner_produccion))
				.getSelectedItem().toString();
		int cantidad = Integer.parseInt(((EditText) layout
				.findViewById(R.id.cantidad_produccion)).getText().toString());
		SharedPreferences settings = getSharedPreferences("MisDatos",
				Context.MODE_PRIVATE);
		Produccion produccion = new Produccion(0,fecha, tipo,settings.getString("id_usuario", ""),cantidad);
		pdbbdd.añadirProduccion(produccion);
	}

	private void rellenarSpinner(View layout) {
		Spinner spinner = (Spinner) layout
				.findViewById(R.id.spinner_produccion);
		ArrayAdapter adapter = ArrayAdapter.createFromResource(this,
				R.array.tipo_produccion,
				android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
	}

	public void getListaCarne() {
		listaCarne = pdbbdd.getListaCarne();
	}

	public void getListaLeche() {
		listaLeche = pdbbdd.getListaLeche();
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