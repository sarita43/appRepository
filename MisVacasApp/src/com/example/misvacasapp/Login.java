package com.example.misvacasapp;

import com.example.misvacasapp.llamadaWS.LlamadaUsuarioWS;
import com.example.misvacasapp.modelo.Usuario;
import com.google.gson.Gson;
import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Clase de la actividad Login En ella se implementan los m�todos que se
 * utilizan para hacer el login
 * 
 * @author Sara Martinez Lopez
 * */
public class Login extends ActionBarActivity {
	// Atributos
	/*
	 * Id usuario que introduce a traves de la pantalla login
	 */
	private String usuario;
	/*
	 * Contrase�a del usuario que introduce a traves de la pantalla login
	 */
	private String contrase�a;

	// Metodos
	/**
	 * Recoge el usuario y contrase�a introducidos por el usuario y comprueba si
	 * existen y son correctos
	 * 
	 * @param v
	 *            Vista que hace el click en el boton
	 */
	public void onClick(View v) {
		usuario = ((TextView) findViewById(R.id.usuario)).getText().toString();
		contrase�a = ((TextView) findViewById(R.id.contrasena)).getText()
				.toString();
		Thread hilo = new Thread() {
			String res = "";
			LlamadaUsuarioWS llamada = new LlamadaUsuarioWS();

			public void run() {
				res = llamada.LlamadaUsuarioExistente(usuario, contrase�a);
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						if (res.compareTo("true") == 0) {
							Toast.makeText(Login.this, "Conectando...",
									Toast.LENGTH_SHORT).show();
							rol();
						} else if (res.compareTo("false") == 0) {
							Toast.makeText(Login.this, "Usuario no existe",
									Toast.LENGTH_SHORT).show();
						} else {
							Toast.makeText(Login.this, "Problemas de conexi�n",
									Toast.LENGTH_SHORT).show();
						}
					}
				});
			}
		};
		hilo.start();
	}

	/**
	 * Comprueba el rol del usuario Si es administrador o no
	 */
	private void rol() {
		Thread hilo = new Thread() {
			String res = "";
			Gson json = new Gson();
			LlamadaUsuarioWS llamada = new LlamadaUsuarioWS();

			public void run() {
				res = llamada.LlamadaUsuario(usuario, contrase�a);
				final Usuario usuario = json.fromJson(res, Usuario.class);
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						if (usuario.getRol() == 1) {
							// Admin
						} else if (usuario.getRol() == 0) {
							lanzarUsuario();
						}
					}
				});
			}
		};
		hilo.start();
	}

	/**
	 * Cambia a la vista del usuario
	 * */
	private void lanzarUsuario() {
		Intent i = new Intent(this, UsuarioVista.class);
		i.putExtra("id_usuario", usuario);
		i.putExtra("contrase�a", contrase�a);
		startActivity(i);
		finish();
	}

	/**
	 * A�ade la vista del login
	 * 
	 * @param savedInstanceState
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
	}

	/**
	 * A�ade el menu a la vista login
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
	 * A�ade los item al menu
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
			System.out.println("click menu ayuda");
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}