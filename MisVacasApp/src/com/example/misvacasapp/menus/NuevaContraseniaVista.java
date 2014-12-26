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
	private String contrase�a;

	private TextView contrase�aActual;
	private TextView contrase�aNueva;
	private TextView contrase�aNuevaRepetida;

	private Usuario usuario;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cambiar_contrasena);

		Bundle bundle = getIntent().getExtras();
		id_usuario = bundle.getString("id_usuario");
		contrase�a = bundle.getString("contrase�a");
	}

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

	private void cambiarContrase�a() {
		Thread hilo = new Thread() {

			LlamadaUsuarioWS llamada = new LlamadaUsuarioWS();

			public void run() {

				llamada.actualizarContrase�a(id_usuario, contrase�aNueva
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
