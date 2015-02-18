package com.example.misvacasapp.menus;

import com.example.misvacasapp.R;
import com.example.misvacasapp.llamadaWS.LlamadaUsuarioWS;
import com.example.misvacasapp.modelo.Usuario;
import com.google.gson.Gson;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Clase de la actividad cambiar contraseña
 * <p>
 * En ella se implementan los métodos que se utilizan para manejar la vista de
 * cambiar contraseña
 * </p>
 * 
 * @author Sara Martinez Lopez
 * */
public class NuevaContraseniaVista extends ActionBarActivity {
	// Atributos
	/** Id usuario */
	private String id_usuario;
	/** Contraseña del usuario */
	private String contraseña;
	/** TextView de la contraseña del usuario */
	private TextView contraseñaActual;
	/** TextView de la nueva contraseña que quiere el usuario */
	private TextView contraseñaNueva;
	/** TextView de la repetición de la nueva contraseña del usuario */
	private TextView contraseñaNuevaRepetida;
	/** Usuario que se va a recoger de la llamada al servicio web */
	private Usuario usuario;

	// Métodos
	/**
	 * Añade la vista de cambiar contraseña Recoge el usuario y la contraseña de
	 * la vista login Inicializa parametros
	 * */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cambiar_contrasena);
		Bundle bundle = getIntent().getExtras();
		id_usuario = bundle.getString("id_usuario");
		contraseña = bundle.getString("contraseña");
	}

	// PORQUE LLAMO AL WEB SERVICE SI SE PUEDE COMPROBAR LA CONTRASEÑA CON LA
	// QUE E PASADO DE ANTES DE LA VISTA ANTERIOR
	/**
	 * Método que se ejecuta cuando se da al botón aceptar En el se recogen los
	 * parámetros del los textView Se comprueba que la contraseña introducida y
	 * la que tiene el usuario sean iguales
	 * */
	public void onClickCambioContraseña(View view) {
		contraseñaActual = (TextView) findViewById(R.id.contrasena_actual_texto);
		contraseñaNueva = (TextView) findViewById(R.id.nueva_contrasena_texto);
		contraseñaNuevaRepetida = (TextView) findViewById(R.id.repita_nueva_contrasena_texto);
		usuario = new Usuario();

		Thread hilo = new Thread() {
			String res = "";
			Gson json = new Gson();

			LlamadaUsuarioWS llamada = new LlamadaUsuarioWS();

			public void run() {
				res = llamada.LlamadaUsuario(id_usuario, contraseña);

				usuario = json.fromJson(res, Usuario.class);
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						
						System.out.println("usuatio "+usuario.getContraseña() +" text "+contraseñaActual.getText().toString());
						
						if (usuario.getContraseña().equals(
								contraseñaActual.getText().toString())) {
							comprobarContraseña();
						} else {
							Toast.makeText(NuevaContraseniaVista.this,
									"Contraseña incorrecta", Toast.LENGTH_LONG)
									.show();
						}
					}
				});
			}
		};
		hilo.start();
	}

	/**
	 * Método que comprueba que la contraseña nueva no sea vacia y que la
	 * contraseña nueva coincidan las dos la nueva ya repetir contraseña nueva
	 * */
	private void comprobarContraseña() {
		if (contraseñaNueva.getText().toString().equals("")) {
			Toast.makeText(NuevaContraseniaVista.this,
					"La contraseña no puede ser vacia", Toast.LENGTH_LONG)
					.show();
		} else if (contraseñaNueva.getText().toString()
				.equals(contraseñaNuevaRepetida.getText().toString())) {
			cambiarContraseña();
			Toast.makeText(NuevaContraseniaVista.this,
					"La contraseña ha sido cambiada correctamente",
					Toast.LENGTH_LONG).show();
			Intent i = new Intent(this, AdministrarCuentaVista.class);
			i.putExtra("id_usuario", id_usuario);
			i.putExtra("contraseña", contraseñaNueva.getText().toString());
			startActivity(i);
			finish();
		} else {
			Toast.makeText(NuevaContraseniaVista.this,
					"Las contraseñas no coinciden", Toast.LENGTH_LONG).show();
		}
	}

	/**
	 * Método que cambia la contraseña del usuario LLama al servicio web del
	 * usuario
	 * */
	private void cambiarContraseña() {
		Thread hilo = new Thread() {
			LlamadaUsuarioWS llamada = new LlamadaUsuarioWS();

			public void run() {
				llamada.actualizarContraseña(id_usuario, contraseñaNueva
						.getText().toString());
				// runOnUiThread(new Runnable() {
				// @Override
				// public void run() {
				//
				// }
				// });
			}
		};
		hilo.start();
	}
}