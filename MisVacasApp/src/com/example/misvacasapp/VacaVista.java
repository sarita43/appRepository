package com.example.misvacasapp;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
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
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Clase de la actividad de la vista de la vaca. En ella se implementan los
 * métodos que se utilizan para la vista de la vaca
 * 
 * @author Sara Martinez Lopez
 * */
public class VacaVista extends ActionBarActivity {
	// ---------------------Atributos-------------------------------//
	/** Id de la vaca */
	private String id_vaca;
	/** Base de datos interna de los animales */
	private VacaDatosBbdd vdatos;

	// ----------------------Métodos--------------------------------//
	/**
	 * Rellena los campos de la vaca que se ha seleccionado anteriormente.
	 * Recoge los datos de la base de datos interna
	 * */
	@SuppressWarnings("deprecation")
	private void rellenarCamposVaca() {

		Vaca vaca = vdatos.getVaca(id_vaca);

		TextView preidVaca = (TextView) findViewById(R.id.preidVaca);
		preidVaca.setText(vaca.getId_vaca().substring(0,
				vaca.getId_vaca().length() - 4));
		TextView idVaca = (TextView) findViewById(R.id.idVaca);
		idVaca.setText(vaca.getId_vaca().substring(
				vaca.getId_vaca().length() - 4, vaca.getId_vaca().length()));
		TextView raza = (TextView) findViewById(R.id.raza);
		raza.setText("RAZA: " + vaca.getRaza());
		TextView sexo = (TextView) findViewById(R.id.sexo);
		sexo.setText("SEXO: " + vaca.getSexo());
		TextView fechaNacimiento = (TextView) findViewById(R.id.fechaNacimiento);
		fechaNacimiento.setText("FECHA DE NACIMIENTO: "
				+ vaca.getFecha_nacimiento());
		TextView idMadre = (TextView) findViewById(R.id.idMadre);
		idMadre.setText("ID MADRE: " + vaca.getId_madre());
		Button imagen = (Button) findViewById(R.id.imageView1);
		byte[] decodedString = Base64.decode(vaca.getFoto(), Base64.DEFAULT);
		Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0,
				decodedString.length);
		Drawable i = new BitmapDrawable(decodedByte);
		imagen.setBackground(i);
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
			Button imagen = (Button) findViewById(R.id.imageView1);
			imagen.setBackgroundDrawable(new BitmapDrawable(bitmap));
			actualizarVaca();
			// TODO onclick Boton guardar cambios??
		} catch (FileNotFoundException e) {
		}
		}
	}

	/**
	 * Método que actualiza la imagen del animal en la base de datos interna
	 */
	public void actualizarVaca() {
		String bitmapdata = crearImagen();
		Vaca vaca = vdatos.getVaca(id_vaca);
		vaca.setFoto(bitmapdata);
		vdatos.actualizarFoto(vaca);
	}

	/**
	 * Método que crea la foto que se va a guardar en la base de datos y la devuelve como String
	 * @return String Foto del animal
	 */
	private String crearImagen() {
		Button imagen = (Button) findViewById(R.id.imageView1);
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
	 * Método que se ejecuta al hacer click en el botón de los medicamentos
	 * Cambia a la vista de medicamentos de la vaca
	 * 
	 * @param v
	 * */
	public void onClickMedicamentos(View v) {
		new LanzarVista(this).lanzarMedicamentos(id_vaca);
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
								VacaVista.this,
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
						Toast.makeText(VacaVista.this,
								"La cuenta ha siendo sincronizada",
								Toast.LENGTH_LONG).show();
					}
				});
			}
		};
		hilo.start();
	}

	/**
	 * Añade la vista de la vaca. Inicializa atributos
	 * 
	 * @param savedInstanceState
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_vaca_vista);
		Bundle bundle = getIntent().getExtras();
		id_vaca = bundle.getString("id_vaca");
		vdatos = new VacaDatosBbdd(getApplicationContext());
		rellenarCamposVaca();
	}

	/**
	 * Añade el menu a la vista vaca
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