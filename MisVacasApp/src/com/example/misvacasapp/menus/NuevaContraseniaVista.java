package com.example.misvacasapp.menus;

import com.example.misvacasapp.R;
import com.example.misvacasapp.VacaVista;
import com.example.misvacasapp.llamadaWS.LlamadaUsuarioWS;
import com.example.misvacasapp.modelo.Usuario;
import com.google.gson.Gson;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class NuevaContraseniaVista extends ActionBarActivity {

	private String id_usuario;
	private String contraseña;

	private TextView contraseñaActual;
	private TextView contraseñaNueva;
	private TextView contraseñaNuevaRepetida;

	private Usuario usuario;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cambiar_contrasena);

		Bundle bundle = getIntent().getExtras();
		id_usuario = bundle.getString("id_usuario");
		contraseña = bundle.getString("contraseña");
	}

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

	private void cambiarContraseña() {
		Thread hilo = new Thread() {

			LlamadaUsuarioWS llamada = new LlamadaUsuarioWS();

			public void run() {

				llamada.actualizarContraseña(id_usuario, contraseñaNueva
						.getText().toString());
				runOnUiThread(new Runnable() {
					@Override
					public void run() {

					}
				});
			}
		};
		hilo.start();
	}
}
