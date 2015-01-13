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

public class ModificarUsuarioVista extends ActionBarActivity {

	private String id_usuario;
	private String contraseña;
	private Usuario usuario;
	private TextView nombre;
	private TextView apellido1;
	private TextView apellido2;
	private TextView direccion;
	private TextView poblacion;
	private TextView telefono;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_modificar_usuario);

		Bundle bundle = getIntent().getExtras();
		id_usuario = bundle.getString("id_usuario");
		contraseña = bundle.getString("contraseña");

		nombre = (TextView) findViewById(R.id.nombre_texto);
		apellido1 = (TextView) findViewById(R.id.apellido1_texto);
		apellido2 = (TextView) findViewById(R.id.apellido2_texto);
		direccion = (TextView) findViewById(R.id.direccion_texto);
		poblacion = (TextView) findViewById(R.id.poblacion_texto);
		telefono = (TextView) findViewById(R.id.telefono_texto);
		rellenarCamposTexto();
	}

	private void rellenarCamposTexto() {

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
						nombre.setText(usuario.getNombre());
						apellido1.setText(usuario.getApellido1());
						apellido2.setText(usuario.getApellido2());
						direccion.setText(usuario.getDireccion());
						poblacion.setText(usuario.getPoblacion());
						telefono.setText(Integer.toString(usuario.getTelefono()));
					}
				});
			}
		};
		hilo.start();
	}

	public void onClickActualizarUsuario(View view) {
		Thread hilo = new Thread() {

			LlamadaUsuarioWS llamada = new LlamadaUsuarioWS();

			public void run() {

				if (telefono.getText().toString().trim().length() == 0) {

				} else {
					llamada.actualizarUsuario(id_usuario, nombre.getText()
							.toString(), apellido1.getText().toString(),
							apellido2.getText().toString(), direccion.getText()
									.toString(),
							poblacion.getText().toString(), Integer
									.parseInt(telefono.getText().toString()));
					modificarUsuarioOk();
				}

				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						if (telefono.getText().toString().trim().length() == 0) {
							Toast.makeText(ModificarUsuarioVista.this,
									"Telefono no puede ser vacio",
									Toast.LENGTH_LONG).show();
						} else {
							Toast.makeText(
									ModificarUsuarioVista.this,
									"El usuario ha sido modificado correctamente",
									Toast.LENGTH_LONG).show();
						}
					}
				});
			}
		};
		hilo.start();
	}

	private void modificarUsuarioOk() {
		Intent i = new Intent(this, AdministrarCuentaVista.class);
		i.putExtra("id_usuario", id_usuario);
		i.putExtra("contraseña", contraseña);
		startActivity(i);
		finish();
	}
}
