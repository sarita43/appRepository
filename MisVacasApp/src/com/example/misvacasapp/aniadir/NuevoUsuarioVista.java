package com.example.misvacasapp.aniadir;

import java.util.ArrayList;

import com.example.misvacasapp.R;
import com.example.misvacasapp.llamadaWS.LlamadaUsuarioWS;
import com.example.misvacasapp.modelo.Usuario;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class NuevoUsuarioVista extends ActionBarActivity {

	// Atributos
	/** Nuevo usuario ha introducir */
	private Usuario nuevoUsuario;

	/** Lista de usuarios */
	private ArrayList<Usuario> lista;

	// M�todos
	/**
	 * A�ade la vista del nuevo usuario
	 * 
	 * @param savedInstanceState
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nuevo_usuario);
		getListaUsuarios();
	}

	/**
	 * M�todo que se acciona que
	 * 
	 * @param view
	 */
	public void onClickRegistrarse(View view) {
		String nombre = ((TextView) findViewById(R.id.nombre_nuevo_usuario))
				.getText().toString();
		String apellido1 = ((TextView) findViewById(R.id.apellido1_nuevo_usuario))
				.getText().toString();
		String apellido2 = ((TextView) findViewById(R.id.apellido2_nuevo_usuario))
				.getText().toString();
		String direccion = ((TextView) findViewById(R.id.direccion_nuevo_usuario))
				.getText().toString();
		String poblacion = ((TextView) findViewById(R.id.poblacion_nuevo_usuario))
				.getText().toString();
		String dni = ((TextView) findViewById(R.id.dni_nuevo_usuario))
				.getText().toString();
		int telefono = Integer.parseInt(((TextView) findViewById(R.id.telefono_nuevo_usuario))
				.getText().toString());
		String correo = ((TextView) findViewById(R.id.correo_nuevo_usuario))
				.getText().toString();
		String contrase�a = ((TextView) findViewById(R.id.contrasenia_nuevo_usuario))
				.getText().toString();
		String codigo_explotacion = ((TextView) findViewById(R.id.codigo_explotacion_nuevo_usuario))
				.getText().toString();
		if (comprobarDni()&& comprobarCorreo()) {
			nuevoUsuario = new Usuario(nombre, apellido1, apellido2, direccion,
					poblacion, telefono, dni, contrase�a, 0, correo,
					codigo_explotacion);
			a�adirUsuario();
			finish();
		}
	}	

	/**
	 * M�todo que rellena la lista de usuarios
	 */
	private void getListaUsuarios() {
		Thread hilo = new Thread() {
			String res = "";
			Gson json = new Gson();
			LlamadaUsuarioWS llamada = new LlamadaUsuarioWS();

			public void run() {
				res = llamada.LlamadaListaUsuarios();
				lista = json.fromJson(res, new TypeToken<ArrayList<Usuario>>() {
				}.getType());
			}
		};
		hilo.start();
	}

	/**
	 * M�todo que comprueba si el dni ya existe. Si existe quiere decir que el usuario ya esta registrado
	 * @return boolean Dni correcto o no
	 */
	private boolean comprobarDni() {
		boolean dniOk = true;
		String dni = ((TextView) findViewById(R.id.dni_nuevo_usuario))
				.getText().toString();
		for (int i = 0; i < lista.size(); i++) {
			System.out.println("DNI"+dni);
			if (dni.compareTo(lista.get(i).getDni()) == 0) {
				dniOk = false;
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						Toast.makeText(NuevoUsuarioVista.this,
								"Usuario ya existe", Toast.LENGTH_SHORT).show();
					}
				});
			}
		}
		System.out.println("DNIOK   "+dniOk);
		return dniOk;
	}

	/**
	 * M�todo que comprueba si el correo ya existe. Si existe quiere decir que el usuario ya esta registrado
	 * @return boolean Correo correcto o no
	 */
	private boolean comprobarCorreo() {
		boolean correoOk = true;
		String correo = ((TextView) findViewById(R.id.correo_nuevo_usuario))
				.getText().toString();
		for (int i = 0; i < lista.size(); i++) {
			if (correo.compareTo(lista.get(i).getCorreo()) == 0) {
				correoOk = false;
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						Toast.makeText(NuevoUsuarioVista.this,
								"Correo ya existe", Toast.LENGTH_SHORT).show();
					}
				});
			}
		}
		System.out.println("CORREOOK         "+correoOk);
		return correoOk;
	}

	/**
	 * M�todo que llama al srevicio web del usuario para a�adir un usuario nuevo.
	 */
	private void a�adirUsuario() {
		Thread hilo = new Thread() {
			Gson json = new Gson();
			LlamadaUsuarioWS llamada = new LlamadaUsuarioWS();
			String usuario;

			public void run() {
				
				usuario = json.toJson(nuevoUsuario);
				System.out.println("USUARIO"+usuario);
				llamada.a�adirUsuario(usuario);
			}
		};
		hilo.start();
	}
}