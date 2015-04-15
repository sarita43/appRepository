package com.example.misvacasapp.aniadir;


import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
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
	
	private static final Logger logger = Logger.getLogger(AniadirVacaVista.class);

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
		
		  BasicConfigurator.configure();
	       logger.debug("Hola esto es una traza");
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
				res = llamada.LlamadaListaVacas();
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
		String bitmapdata =crearImagen();
		vaca = new Vaca(id_vaca, raza, fecha, id_madre, id_usuario, sexo,bitmapdata);

		return vaca;
	}
	
	private String crearImagen(){
		Button imagen = (Button)findViewById(R.id.foto_boton);
		Bitmap bitmap = ((BitmapDrawable)imagen.getBackground()).getBitmap();
		bitmap = redimensionarImagenMaximo(bitmap, 500, 300);
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.WEBP, 100, stream);
		byte[] bitmapdata = stream.toByteArray();
		System.out.println(" TAMAÑO  :"+bitmapdata.length);
		String encodedImage = Base64.encodeToString(bitmapdata, Base64.DEFAULT);
		System.out.println("TAMAÑO STRING  "+encodedImage.length());
		return encodedImage;
	}
	
	/**
	 * Redimensionar un Bitmap. By TutorialAndroid.com
	* @param Bitmap mBitmap
	* @param float newHeight
	* @param float newHeight
	 * @param float newHeight
	 * @return Bitmap
	 */
	public Bitmap redimensionarImagenMaximo(Bitmap mBitmap, float newWidth, float newHeigth){
	   //Redimensionamos
	   int width = mBitmap.getWidth();
	   int height = mBitmap.getHeight();
	   float scaleWidth = ((float) newWidth) / width;
	   float scaleHeight = ((float) newHeigth) / height;
	   // create a matrix for the manipulation
	   Matrix matrix = new Matrix();
	   // resize the bit map
	   matrix.postScale(scaleWidth, scaleHeight);
	   // recreate the new Bitmap
	   return Bitmap.createBitmap(mBitmap, 0, 0, width, height, matrix, false);
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
					.getText().toString());
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
				if (comprobarIdVaca() && comprobarFecha() && comprobarSexo()&& comprobarIdMadre()) {
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
	
	public void cargarFoto(View v){
		String name = Environment.getExternalStorageDirectory() + "/test.jpg";
		Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
		startActivityForResult(intent, 2);
		
	}
	
	@SuppressWarnings("deprecation")
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
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
