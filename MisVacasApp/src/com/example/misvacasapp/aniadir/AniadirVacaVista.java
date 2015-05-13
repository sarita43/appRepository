package com.example.misvacasapp.aniadir;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.misvacasapp.LanzarVista;
import com.example.misvacasapp.R;
import com.example.misvacasapp.UsuarioVista;
import com.example.misvacasapp.bbddinterna.MedicamentoDatosBbdd;
import com.example.misvacasapp.bbddinterna.VacaDatosBbdd;
import com.example.misvacasapp.llamadaWS.LlamadaMedicamentoWS;
import com.example.misvacasapp.llamadaWS.LlamadaVacaWS;
import com.example.misvacasapp.modelo.Medicamento;
import com.example.misvacasapp.modelo.Vaca;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Clase de la actividad de añadir vaca. En ella se implementan los métodos que
 * se utilizan para manejar la vista de añadir vaca
 * 
 * @author Sara Martinez Lopez
 * */
public class AniadirVacaVista extends ActionBarActivity {
	//-----------------------------Atributos------------------------------------//
	/** Id del usuario */
	private String id_usuario;
	/** Lista de vacas del usuario */
	private ArrayList<Vaca> listaVacas;
	/** Lista desplegable que muestra los tipos de vacas que puedes introducir */
	private Spinner spinnerRaza;
	/**Base de datos interna de los animales*/
	private VacaDatosBbdd vdatos;

	//------------------------------Métodos--------------------------------------//
	/**
	 * Rellena la lista de desplegable de los tipos de vacas
	 * 
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
	 * */
	private void listaVacas() {
		vdatos = new VacaDatosBbdd(getApplicationContext());
		listaVacas = vdatos.getListaVacas(id_usuario);
	}
	

	/**
	 * Método que se ejecuta cuando se presiona el botón aceptar En el se llama
	 * a las comprobaciones que se pueden hacer para añadir el animal
	 * correctamente y añade el animal si todo es correcto
	 * 
	 * @param view
	 *            Vista
	 * */
	public void nuevaVaca(View view) {
		if (comprobarIdVaca() && comprobarFecha() && comprobarSexo()
				&& comprobarIdMadre()) {
			final Vaca v = crearVaca();
			vdatos.añadirVaca(v);
			runOnUiThread(new Runnable() {
				@Override
				public void run() {

					Toast.makeText(AniadirVacaVista.this,
							"Añadido correctamente", Toast.LENGTH_SHORT).show();
					Intent i = new Intent(getApplicationContext(),
							UsuarioVista.class);
					i.putExtra("id_usuario", id_usuario);
					startActivity(i);
					finish();
				}
			});

		}
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
		String bitmapdata = crearImagen();
		vaca = new Vaca(id_vaca, raza, fecha, id_madre, id_usuario, sexo,
				bitmapdata);

		return vaca;
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
		for (int i = 0; listaVacas.size() > i; i++) {
			if (id_vaca.equals(listaVacas.get(i).getId_vaca())) {
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
	 * Comprueba que el sexo introducido por el usuario sea el correcto. El sexo
	 * tiene que se M (Macho) o H (Hembra), si no es asi devuelve un false
	 * 
	 * @return boolean True sexo correcto false sexo incorrecto
	 */
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

	/**
	 * Método que comprueba si la fecha introducida por el usuario es correcta.
	 * Comprueba que este entre los valores de los dias posibles, los meses
	 * posibles y el año que no supere al año actual. También comprueba que se
	 * introduzca algun valor en la fecha
	 * 
	 * @return boolean Fecha correcta true fecha incorrecta false
	 */
	@SuppressWarnings("deprecation")
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
					.getText().toString()) - 1;
			int año = Integer
					.parseInt(((TextView) findViewById(R.id.anio_vaca))
							.getText().toString()) - 1900;
			int añoActual = new java.util.Date().getYear();
			int mesActual = new java.util.Date().getMonth();
			int diaActual = new java.util.Date().getDate();
			if (dia <= 31 && mes <= 12 && año < añoActual) {
				fechaOk = true;

			} else if (año == añoActual) {
				if (dia <= diaActual && mes <= mesActual) {
					fechaOk = true;
				} else {
					fechaOk = false;
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							Toast.makeText(AniadirVacaVista.this,
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
						Toast.makeText(AniadirVacaVista.this,
								"Fecha incorrecta", Toast.LENGTH_SHORT).show();
					}
				});
			}
		}

		return fechaOk;
	}

	/**
	 * Método que comprueba que el id de la madre no sea vacio
	 * @return boolean True si el id es correcto False si es vacio
	 */
	private boolean comprobarIdMadre() {
		boolean id_correcto = true;
		String id_madre = ((TextView) findViewById(R.id.id_madre_nuevo_vaca))
				.getText().toString();
		if (id_madre.equals("")) {
			id_correcto = false;
			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					Toast.makeText(AniadirVacaVista.this, "Id madre vacio",
							Toast.LENGTH_SHORT).show();
				}
			});
		}
		return id_correcto;
	}
	
	/**
	 * Método que crea la foto que se va a guardar en la base de datos y la devuelve como String
	 * @return String Foto del animal
	 */
	private String crearImagen() {
		Button imagen = (Button) findViewById(R.id.foto_boton);
		Bitmap bitmap = ((BitmapDrawable) imagen.getBackground()).getBitmap();
		bitmap = redimensionarImagenMaximo(bitmap, 500, 300);
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.WEBP, 100, stream);
		byte[] bitmapdata = stream.toByteArray();
		String encodedImage = Base64.encodeToString(bitmapdata, Base64.DEFAULT);
		return encodedImage;
	}

	/**
	 * Redimensionar un Bitmap.
	 * 
	 * @param Bitmap
	 *            mBitmap
	 * @param float newHeight
	 * @param float newHeight
	 * @param float newHeight
	 * @return Bitmap
	 */
	public Bitmap redimensionarImagenMaximo(Bitmap mBitmap, float newWidth,
			float newHeigth) {

		int width = mBitmap.getWidth();
		int height = mBitmap.getHeight();
		float scaleWidth = ((float) newWidth) / width;
		float scaleHeight = ((float) newHeigth) / height;
		Matrix matrix = new Matrix();
		matrix.postScale(scaleWidth, scaleHeight);
		return Bitmap.createBitmap(mBitmap, 0, 0, width, height, matrix, false);
	}

	/**
	 * Crea la vista para seleccionar una foto
	 * 
	 * @param v
	 *            Vista
	 */
	public void cargarFoto(View v) {
		Intent intent = new Intent(Intent.ACTION_PICK,
				android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
		startActivityForResult(intent, 2);

	}

	@SuppressWarnings("deprecation")
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode!=0){
		Uri selectedImage = data.getData();
		InputStream is;
		try {
			is = getContentResolver().openInputStream(selectedImage);
			BufferedInputStream bis = new BufferedInputStream(is);
			Bitmap bitmap = BitmapFactory.decodeStream(bis);
			Button imagen = (Button) findViewById(R.id.foto_boton);
			imagen.setBackgroundDrawable(new BitmapDrawable(bitmap));

		} catch (FileNotFoundException e) {
		}
		}
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
								AniadirVacaVista.this,
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
						Toast.makeText(AniadirVacaVista.this,
								"La cuenta ha siendo sincronizada",
								Toast.LENGTH_LONG).show();
					}
				});
			}
		};
		hilo.start();
	}

	/**
	 * Añade la vista de añadir vaca. Recoge el usuario de la vista del usuario.
	 * Inicializa parametros
	 * */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_aniadir_vaca);
		Bundle bundle = getIntent().getExtras();
		id_usuario = bundle.getString("id_usuario");
		listaVacas = new ArrayList<Vaca>();
		rellenarSpinner();

		listaVacas();
	}

	/**
	 * Añade el menu a la vista aniadirVaca
	 * 
	 * @param menu
	 * */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
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
		} else if (id == R.id.sincronizar_cuenta) {
			sincronizar(id_usuario);
		} else if (id == R.id.mis_vacas) {
			new LanzarVista(this).lanzarUsuarioVista(id_usuario,
					settings.getString("contraseña", ""));
			finish();
			return true;
		} else if (id == R.id.administrar_cuenta) {
			new LanzarVista(this).lanzarAdministrarCuenta(id_usuario,
					settings.getString("contraseña", ""));
			return true;
		} else if (id == R.id.ayuda) {
			AlertDialog.Builder alerta = new AlertDialog.Builder(this);
			alerta.setTitle("Ayuda");
			alerta.setMessage("Para añadir un nuevo animal rellene los campos."
					+ "\n"
					+ "Pincha sobre la imagen del logo para añadir una foto al animal."
					+ "\n"
					+ "Cuando haya rellenado todos los campos pulse en aceptar par añadir el animal");
			alerta.show();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
