package com.example.misvacasapp;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import com.example.misvacasapp.bbddinterna.VacaDatosBbdd;
import com.example.misvacasapp.modelo.Vaca;

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
import android.view.View;
import android.widget.ImageView;

/**
 * Clase de la vista que cambia la foto del animal
 * 
 * @author Sara Mart�nez L�pez
 * 
 */
public class CambiarFotoVista extends ActionBarActivity {

	// -------------------------------Atributos-------------------------------//
	/** Id del animal */
	private String id_vaca;
	/** Base de datos interna de los animales */
	private VacaDatosBbdd vdatos;

	// -------------------------------M�todos---------------------------------//

	/**
	 * M�todo que carga la foto del animal que tiene guardada en la base de
	 * datos
	 */
	@SuppressWarnings("deprecation")
	private void cargarFoto() {
		vdatos = new VacaDatosBbdd(this);
		Vaca vaca = vdatos.getVaca(id_vaca);

		ImageView foto = (ImageView) findViewById(R.id.imageView1);
		byte[] decodedString = Base64.decode(vaca.getFoto(), Base64.DEFAULT);
		Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0,
				decodedString.length);
		Drawable i = new BitmapDrawable(decodedByte);
		foto.setBackground(i);
	}

	/**
	 * M�todo que al pulsar en el bot�n de Cambiar foto, abre la vista de la
	 * galeria para cambiar la foto del animal
	 * 
	 * @param view
	 */
	public void onClickCargarFoto(View view) {
		Intent intent = new Intent(Intent.ACTION_PICK,
				android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
		startActivityForResult(intent, 2);
	}

	/**
	 * M�todo que se utiliza para cargar la foto de la galeria y mostrarla en la
	 * vista
	 */
	@SuppressWarnings("deprecation")
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode != 0) {
			Uri selectedImage = data.getData();
			InputStream is;
			try {
				is = getContentResolver().openInputStream(selectedImage);
				BufferedInputStream bis = new BufferedInputStream(is);
				Bitmap bitmap = BitmapFactory.decodeStream(bis);
				ImageView imagen = (ImageView) findViewById(R.id.imageView1);
				imagen.setBackgroundDrawable(new BitmapDrawable(bitmap));
			} catch (FileNotFoundException e) {
			}
		}
	}

	/**
	 * M�todo que guarda la foto que ha sido cambiada por el usuario y que se
	 * muestra en la vista
	 * 
	 * @param v
	 */
	public void onClickGuardarCambios(View v) {
		String bitmapdata = crearImagen();
		Vaca vaca = vdatos.getVaca(id_vaca);
		vaca.setFoto(bitmapdata);
		vdatos.actualizarFoto(vaca);
		finish();
		SharedPreferences settings = getSharedPreferences("MisDatos",
				Context.MODE_PRIVATE);
		String id_usuario = settings.getString("id_usuario", "");
		new LanzarVista(this).lanzarVaca(id_vaca, id_usuario);
	}

	/**
	 * M�todo que crea la foto que se va a guardar en la base de datos y la
	 * devuelve como String
	 * 
	 * @return String Foto del animal
	 */
	private String crearImagen() {
		ImageView imagen = (ImageView) findViewById(R.id.imageView1);
		Bitmap bitmap = ((BitmapDrawable) imagen.getBackground()).getBitmap();
		bitmap = redimensionarImagenMaximo(bitmap, 500, 300);
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.WEBP, 100, stream);
		byte[] bitmapdata = stream.toByteArray();
		String encodedImage = Base64.encodeToString(bitmapdata, Base64.DEFAULT);
		return encodedImage;
	}

	/**
	 * Redimensiona la foto para mostrar en un Bitmap.
	 * 
	 * @param Bitmap
	 *            mBitmap
	 * @param float newHeight
	 * @param float newHeight
	 * @param float newHeight
	 * @return Bitmap Foto redimensionada
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
	 * A�ade la vista de cambiar fotos. Inicializa los atributos.
	 * 
	 * @param savedInstanceState
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_foto);
		Bundle bundle = getIntent().getExtras();
		id_vaca = bundle.getString("id_vaca");

		cargarFoto();
	}
}