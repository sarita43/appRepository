package com.example.misvacasapp.vista;

import com.example.misvacasapp.R;
import com.example.misvacasapp.controlador.VacaControlador;
import com.example.misvacasapp.modelo.Vaca;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Clase de la actividad de la vista de la vaca En ella se implementan los
 * m�todos que se utilizan para la vista de la vaca
 * 
 * @author Sara Martinez Lopez
 * */
public class VacaVista extends ActionBarActivity {
	// Atributos
	/** Id de la vaca */
	private String id_vaca;
 	/** Id del usuario */
	private String id_usuario;

	// M�todos
	/**
	 * A�ade la vista de la vaca
	 * 
	 * @param savedInstanceState
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_vaca_vista);
		Bundle bundle = getIntent().getExtras();
		id_vaca = bundle.getString("id_vaca");
		id_usuario = bundle.getString("id_usuario");
		rellenarCamposVaca();
	}

	/**
	 * M�todo que se ejecuta al hacer click en el bot�n de los medicamentos
	 * Cambia a la vista de medicamentos de la vaca
	 * 
	 * @param v
	 * */
	public void onClickMedicamentos(View v) {
		new LanzarVista(this).lanzarMedicamentos(id_vaca);
	}

	/**
	 * Rellena los campos de la vaca que se ha seleccionado anteriormente Llama
	 * al web service para recoger los datos
	 * 
	 * @see onCreate
	 * */
	private void rellenarCamposVaca() {

		Vaca vaca = new VacaControlador().getVaca(id_vaca, id_usuario);
		TextView idVaca = (TextView) findViewById(R.id.idVaca);
		idVaca.setText(vaca.getId_vaca());
		TextView raza = (TextView) findViewById(R.id.raza);
		raza.setText("RAZA: " + vaca.getRaza());
		TextView sexo = (TextView) findViewById(R.id.sexo);
		sexo.setText("SEXO: " + vaca.getSexo());
		TextView fechaNacimiento = (TextView) findViewById(R.id.fechaNacimiento);
		fechaNacimiento.setText("FECHA DE NACIMIENTO: "
				+ vaca.getFecha_nacimiento());
		TextView idMadre = (TextView) findViewById(R.id.idMadre);
		idMadre.setText("ID MADRE: " + vaca.getId_madre());
		ImageView imagen = (ImageView) findViewById(R.id.imageView1);
		byte[] decodedString = Base64.decode(vaca.getFoto(), Base64.DEFAULT);
		Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0,
				decodedString.length);
		imagen.setImageBitmap(decodedByte);
	}

	/**
	 * A�ade el menu a la vista login
	 * 
	 * @param menu
	 * */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.login, menu);
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
		if (id == R.id.ayuda) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}