package com.example.misvacasapp.aniadir;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.example.misvacasapp.R;
import com.example.misvacasapp.UsuarioVista;
import com.example.misvacasapp.llamadaWS.LlamadaVacaWS;
import com.example.misvacasapp.modelo.Vaca;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

/**
 * Clase de la actividad de añadir vaca. En ella se implementan los métodos que
 * se utilizan para manejar la vista de añadir vaca
 * 
 * @author Sara Martinez Lopez
 * */
public class AniadirVacaVista extends ActionBarActivity {
	// Atributos
	/** Id del usuario */
	private String id_usuario;
	/** Lista de vacas del usuario */
	private ArrayList<Vaca> lista;
	/** Tipo que serializa o deserializa para enviar a traves del servicio web */
	private Gson json;
	/** Lista desplegable que muestra los tipos de vacas que puedes introducir */
	private Spinner spinnerRaza;

	// Métodos
	/**
	 * Añade la vista de añadir vaca. Recoge el usuario de la vista del usuario.
	 * Inicializa parametros
	 * */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_aniadir_vaca);
		json = new GsonBuilder().setPrettyPrinting()
				.setDateFormat("dd-MM-yyyy").create();
		Bundle bundle = getIntent().getExtras();
		id_usuario = bundle.getString("id_usuario");
		lista = new ArrayList<Vaca>();
		rellenarSpinner();
		listaVacas();
	}

	/**
	 * Rellena la lista de desplegable de los tipos de vacas
	 * 
	 * @see onCreate
	 * */
	private void rellenarSpinner() {
		spinnerRaza = (Spinner) findViewById(R.id.raza_nuevo_texto);
		ArrayList<String> listaRazas = new ArrayList<String>(Arrays.asList(
				"Albera", "Alistana Sanabresa", "Asturiana de la Montaña",
				"Asturiana de los Valles", "Avileña Negra Ibérica",
				"Avileña Negra Ibérica Bociblanca", "Berrenda en Colorado",
				"Berrenda en Negro", "Betizu", "Blanca Cacereña",
				"Blonda de Aquitania", "Bruna de los Pirineos", "Cachena",
				"Caldelá", "Canaria", "Cárdena Andaluza", "Charolesa",
				"Fleckvieh", "Frieiresa", "Frisona", "Lidia", "Limiá",
				"Limusina", "Mallorquina", "Marismeña", "Menorquina",
				"Monchina", "Morucha", "Morucha Negra", "Murciana Levantina",
				"Negra Andaluza", "Pajuna", "Palmera", "Parda",
				"Parda de Montaña", "Pasiega", "Pirenaica", "Retinto",
				"Rubia Gallega", "Sayaguesa", "Serrana de Teruel",
				"Serrana Negra", "Terreña", "Tudanca", "Vianesa"));
		ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
				android.R.layout.simple_spinner_dropdown_item, listaRazas);
		spinnerRaza.setAdapter(adapter);
	}

	/**
	 * Recoge la lista de vacas del usuario y lo guarda en el arrayList de lista
	 * Para ello se llama al servicio web de vacas
	 * 
	 * @see onCreate
	 * */
	private void listaVacas() {
		Thread hilo = new Thread() {
			String res = "";
			Gson json = new GsonBuilder().setPrettyPrinting()
					.setDateFormat("dd-MM-yyyy").create();
			LlamadaVacaWS llamada = new LlamadaVacaWS();

			public void run() {
				res = llamada.LlamadaListaVacas(id_usuario);
				lista = json.fromJson(res, new TypeToken<ArrayList<Vaca>>() {
				}.getType());
			}
		};
		hilo.start();
	}

	/**
	 * Comprueba que el id de la vaca nueva introducido sea correcto Para ello
	 * comprueba que no sea vacio y que no exista ya ese animal
	 * 
	 * @see nuevaVaca
	 * @return boolean si es true el id es correcto si es false no
	 * */
	private boolean comprobarIdVaca() {
		boolean id_correcto = true;
		String id_vaca = ((TextView) findViewById(R.id.id_vaca_nuevo_texto))
				.getText().toString();
		for (int i = 0; lista.size() > i; i++) {
			if (id_vaca.equals(lista.get(i).getId_vaca())) {
				id_correcto = false;
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						Toast.makeText(AniadirVacaVista.this, "Id ya existe",
								Toast.LENGTH_SHORT).show();
					}
				});
			} else if (id_vaca.equals("")) {
				id_correcto = false;
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						Toast.makeText(AniadirVacaVista.this, "Id vacio",
								Toast.LENGTH_SHORT).show();
					}
				});
			}
		}
		return id_correcto;
	}

	/**
	 * Recoge los campos de la vista que se han introducido para crear el animal
	 * Comprueba que la fecha sea correcta
	 * 
	 * @return Vaca Vaca creada con los parametros introducidos
	 * */
	private Vaca crearVaca() {
		Vaca vaca = new Vaca();

		int dia = Integer.parseInt(((TextView) findViewById(R.id.dia_vaca))
				.getText().toString());
		int mes = Integer.parseInt(((TextView) findViewById(R.id.mes_vaca))
				.getText().toString()) - 1;
		int año = Integer.parseInt(((TextView) findViewById(R.id.anio_vaca))
				.getText().toString()) - 1900;
		@SuppressWarnings("deprecation")
		Date fecha = new Date(año, mes, dia);
		String id_vaca = (((TextView) findViewById(R.id.id_vaca_nuevo_texto))
				.getText().toString());
		String raza = spinnerRaza.getSelectedItem().toString();
		String id_madre = ((TextView) findViewById(R.id.id_madre_nuevo_vaca))
				.getText().toString();
		String sexo = ((TextView) findViewById(R.id.sexo_nuevo_vaca)).getText()
				.toString();
		vaca = new Vaca(id_vaca, raza, fecha, id_madre, id_usuario, sexo, null);

		return vaca;
	}

	private boolean comprobarSexo() {
		boolean sexoOK = false;
		String sexo = ((TextView) findViewById(R.id.sexo_nuevo_vaca)).getText()
				.toString();

		if (sexo.equals("M") || sexo.equals("H")) {
			sexoOK = true;
		} else {
			sexoOK = false;
			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					Toast.makeText(AniadirVacaVista.this, "Mal sexo. M o H",
							Toast.LENGTH_SHORT).show();
				}
			});
		}
		return sexoOK;

	}

	private boolean comprobarFecha() {
		boolean fechaOk = false;

		if (((TextView) findViewById(R.id.dia_vaca)).getText().toString()
				.equals("")
				|| ((TextView) findViewById(R.id.mes_vaca)).getText()
						.toString().equals("")
				|| ((TextView) findViewById(R.id.anio_vaca)).getText()
						.toString().equals("")) {
			fechaOk = false;
			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					Toast.makeText(AniadirVacaVista.this, "Fecha incorrecta",
							Toast.LENGTH_SHORT).show();
				}
			});
		} else {
			int dia = Integer.parseInt(((TextView) findViewById(R.id.dia_vaca))
					.getText().toString());
			int mes = Integer.parseInt(((TextView) findViewById(R.id.mes_vaca))
					.getText().toString());
			int año = Integer
					.parseInt(((TextView) findViewById(R.id.anio_vaca))
							.getText().toString())-1900;
			if (dia <= 31 && mes <= 12 && año <= new java.util.Date().getYear()) {
				fechaOk = true;
			} else {
				fechaOk = false;
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						Toast.makeText(AniadirVacaVista.this,
								"Fecha incorrecta", Toast.LENGTH_SHORT).show();
					}
				});
			}
		}

		return fechaOk;
	}

	// REMODELAR ESTE METODO PONER EN OTRO METODO COMPROBAR SEXO
	// COMPROBAR EN OTROS METODOS LOS VALORES INTRODUCIDOS
	/**
	 * Método que se ejecuta cuando se presiona el botón aceptar En el se llama
	 * a las comprobaciones que se pueden hacer para añadir el animal
	 * correctamente y añade el animal si todo es correcto
	 * 
	 * @param view
	 *            Vista
	 * */
	public void nuevaVaca(View view) {
		Thread hilo = new Thread() {
			LlamadaVacaWS llamada = new LlamadaVacaWS();

			public void run() {
				if (comprobarIdVaca() && comprobarFecha() && comprobarSexo()) {
					final Vaca v = crearVaca();

					String vaca = json.toJson(v);
					llamada.LLamadaAñadirVaca(vaca);
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							Toast.makeText(AniadirVacaVista.this,
									"Añadido correctamente", Toast.LENGTH_SHORT)
									.show();
							Intent i = new Intent(getApplicationContext(),
									UsuarioVista.class);
							i.putExtra("id_usuario", id_usuario);
							startActivity(i);
							finish();
						}
					});
				}
			}
		};
		hilo.start();
	}

	/**
	 * Añade el menu a la vista aniadirVaca
	 * 
	 * @param menu
	 * */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
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
		if (id == R.id.ayuda) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
