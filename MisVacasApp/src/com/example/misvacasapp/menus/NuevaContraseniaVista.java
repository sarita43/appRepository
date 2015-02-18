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
 * Clase de la actividad cambiar contrase�a
 * <p>
 * En ella se implementan los m�todos que se utilizan para manejar la vista de
 * cambiar contrase�a
 * </p>
 * 
 * @author Sara Martinez Lopez
 * */
public class NuevaContraseniaVista extends ActionBarActivity {
	// Atributos
	/** Id usuario */
	private String id_usuario;
	/** Contrase�a del usuario */
	private String contrase�a;
	/** TextView de la contrase�a del usuario */
	private TextView contrase�aActual;
	/** TextView de la nueva contrase�a que quiere el usuario */
	private TextView contrase�aNueva;
	/** TextView de la repetici�n de la nueva contrase�a del usuario */
	private TextView contrase�aNuevaRepetida;
	/** Usuario que se va a recoger de la llamada al servicio web */
	private Usuario usuario;

	// M�todos
	/**
	 * A�ade la vista de cambiar contrase�a Recoge el usuario y la contrase�a de
	 * la vista login Inicializa parametros
	 * */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cambiar_contrasena);
		Bundle bundle = getIntent().getExtras();
		id_usuario = bundle.getString("id_usuario");
		contrase�a = bundle.getString("contrase�a");
	}

	// PORQUE LLAMO AL WEB SERVICE SI SE PUEDE COMPROBAR LA CONTRASE�A CON LA
	// QUE E PASADO DE ANTES DE LA VISTA ANTERIOR
	/**
	 * M�todo que se ejecuta cuando se da al bot�n aceptar En el se recogen los
	 * par�metros del los textView Se comprueba que la contrase�a introducida y
	 * la que tiene el usuario sean iguales
	 * */
	public void onClickCambioContrase�a(View view) {
		contrase�aActual = (TextView) findViewById(R.id.contrasena_actual_texto);
		contrase�aNueva = (TextView) findViewById(R.id.nueva_contrasena_texto);
		contrase�aNuevaRepetida = (TextView) findViewById(R.id.repita_nueva_contrasena_texto);
		usuario = new Usuario();

		Thread hilo = new Thread() {
			String res = "";
			Gson json = new Gson();

			LlamadaUsuarioWS llamada = new LlamadaUsuarioWS();

			public void run() {
				res = llamada.LlamadaUsuario(id_usuario, contrase�a);

				usuario = json.fromJson(res, Usuario.class);
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						
						System.out.println("usuatio "+usuario.getContrase�a() +" text "+contrase�aActual.getText().toString());
						
						if (usuario.getContrase�a().equals(
								contrase�aActual.getText().toString())) {
							comprobarContrase�a();
						} else {
							Toast.makeText(NuevaContraseniaVista.this,
									"Contrase�a incorrecta", Toast.LENGTH_LONG)
									.show();
						}
					}
				});
			}
		};
		hilo.start();
	}

	/**
	 * M�todo que comprueba que la contrase�a nueva no sea vacia y que la
	 * contrase�a nueva coincidan las dos la nueva ya repetir contrase�a nueva
	 * */
	private void comprobarContrase�a() {
		if (contrase�aNueva.getText().toString().equals("")) {
			Toast.makeText(NuevaContraseniaVista.this,
					"La contrase�a no puede ser vacia", Toast.LENGTH_LONG)
					.show();
		} else if (contrase�aNueva.getText().toString()
				.equals(contrase�aNuevaRepetida.getText().toString())) {
			cambiarContrase�a();
			Toast.makeText(NuevaContraseniaVista.this,
					"La contrase�a ha sido cambiada correctamente",
					Toast.LENGTH_LONG).show();
			Intent i = new Intent(this, AdministrarCuentaVista.class);
			i.putExtra("id_usuario", id_usuario);
			i.putExtra("contrase�a", contrase�aNueva.getText().toString());
			startActivity(i);
			finish();
		} else {
			Toast.makeText(NuevaContraseniaVista.this,
					"Las contrase�as no coinciden", Toast.LENGTH_LONG).show();
		}
	}

	/**
	 * M�todo que cambia la contrase�a del usuario LLama al servicio web del
	 * usuario
	 * */
	private void cambiarContrase�a() {
		Thread hilo = new Thread() {
			LlamadaUsuarioWS llamada = new LlamadaUsuarioWS();

			public void run() {
				llamada.actualizarContrase�a(id_usuario, contrase�aNueva
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