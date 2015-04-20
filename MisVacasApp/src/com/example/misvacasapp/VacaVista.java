package com.example.misvacasapp;

import java.util.ArrayList;

import com.example.misvacasapp.bbddinterna.VacaDatosBbdd;
import com.example.misvacasapp.iterator.AgregadoUsuario;
import com.example.misvacasapp.iterator.IteratorListaUsuario;
import com.example.misvacasapp.llamadaWS.LlamadaUsuarioWS;
import com.example.misvacasapp.llamadaWS.LlamadaVacaWS;
import com.example.misvacasapp.modelo.Usuario;
import com.example.misvacasapp.modelo.Vaca;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Clase de la actividad de la vista de la vaca En ella se implementan los
 * métodos que se utilizan para la vista de la vaca
 * 
 * @author Sara Martinez Lopez
 * */
public class VacaVista extends ActionBarActivity {
	// Atributos
	/** Id de la vaca */
	private String id_vaca;
	/** Id delusuario */
	private String id_usuario;
	/** Usuario */
	private Usuario usuario;

	// Métodos
	/**
	 * Añade la vista de la vaca
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
	 * Método que se ejecuta al hacer click en el botón de los medicamentos
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
		VacaDatosBbdd vdatos = new VacaDatosBbdd(getApplicationContext());
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
		ImageView imagen = (ImageView) findViewById(R.id.imageView1);
		byte[] decodedString = Base64.decode(vaca.getFoto(), Base64.DEFAULT);
		Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0,
				decodedString.length);
		Drawable i = new BitmapDrawable(decodedByte);
		imagen.setBackground(i);
	}

	private Usuario getUsuario(final String id_usuario) {

		Thread hilo = new Thread() {
			String res = "";
			Gson json = new GsonBuilder().setPrettyPrinting()
					.setDateFormat("dd-MM-yyyy").create();
			LlamadaUsuarioWS llamada = new LlamadaUsuarioWS();

			public void run() {
				res = llamada.LlamadaListaUsuarios();
				ArrayList<Usuario> listaUsuario = json.fromJson(res,
						new TypeToken<ArrayList<Vaca>>() {
						}.getType());
				AgregadoUsuario agregado = new AgregadoUsuario(listaUsuario);
				IteratorListaUsuario i = (IteratorListaUsuario) agregado
						.createIterator();
				while (i.hasNext()) {
					if (i.actualElement().getDni().equals(id_usuario)) {
						usuario = i.actualElement();
					}
				}
			}
		};
		hilo.start();
		return usuario;

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

		if (id == R.id.cerrar_sesion) {
			SharedPreferences settings = getSharedPreferences("MisDatos",
					Context.MODE_PRIVATE);
			SharedPreferences.Editor editor = settings.edit();
			editor.putString("id_usuario", "");
			editor.putString("contraseña", "");
			editor.commit();
			new LanzarVista(this).lanzarLogin();
			finish();
		} else if (id == R.id.mis_vacas) {
			new LanzarVista(this).lanzarUsuarioVista(id_usuario,
					getUsuario(id_usuario).getContraseña());
			finish();
			return true;
		} else if (id == R.id.administrar_cuenta) {
			new LanzarVista(this).lanzarAdministrarCuenta(id_usuario,
					getUsuario(id_usuario).getContraseña());
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}