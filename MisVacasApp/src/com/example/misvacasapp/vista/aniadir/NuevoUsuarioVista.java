package com.example.misvacasapp.vista.aniadir;

import java.util.ArrayList;

import com.example.misvacasapp.R;
import com.example.misvacasapp.controlado.modelo.iterator.AgregadoUsuario;
import com.example.misvacasapp.controlado.modelo.iterator.IteratorListaUsuario;
import com.example.misvacasapp.controlador.UsuarioControlador;
import com.example.misvacasapp.modelo.Usuario;

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

	// Métodos
	/**
	 * Añade la vista del nuevo usuario
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
	 * Método que se acciona que
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
		String contraseña = ((TextView) findViewById(R.id.contrasenia_nuevo_usuario))
				.getText().toString();
		String codigo_explotacion = ((TextView) findViewById(R.id.codigo_explotacion_nuevo_usuario))
				.getText().toString();
		if (comprobarDni(dni)&& comprobarCorreo(correo)) {
			nuevoUsuario = new Usuario(nombre, apellido1, apellido2, direccion,
					poblacion, telefono, dni, contraseña, 0, correo,
					codigo_explotacion);
			new UsuarioControlador().añadirUsuario(nuevoUsuario);
			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					Toast.makeText(NuevoUsuarioVista.this,
							"Usuario creado", Toast.LENGTH_SHORT).show();
				}
			});
			//TODO ENVIAR EMAIL??
			finish();
		}
	}	

	/**
	 * Método que rellena la lista de usuarios
	 */
	private void getListaUsuarios() {
		lista = new UsuarioControlador().listaUsuarios();
	}

	/**
	 * Método que comprueba si el dni ya existe. Si existe quiere decir que el usuario ya esta registrado
	 * @param dni 
	 * @return boolean Dni correcto o no
	 */
	private boolean comprobarDni(String dni) {
		boolean dniOk = true;
		AgregadoUsuario agregado = new AgregadoUsuario();
		IteratorListaUsuario i = (IteratorListaUsuario) agregado.createIterator();
		while (i.hasNext()){
			if(i.actualElement().getDni().equals(dni)){
				dniOk = true;
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						Toast.makeText(NuevoUsuarioVista.this,
								"Usuario ya existe", Toast.LENGTH_SHORT).show();
					}
				});
			}
			i.next();
		}
		return dniOk;
	}

	/**
	 * Método que comprueba si el correo ya existe. Si existe quiere decir que el usuario ya esta registrado
	 * @param correo 
	 * @return boolean Correo correcto o no
	 */
	private boolean comprobarCorreo(String correo) {
		boolean correoOk = true;
		correoOk = new UsuarioControlador().correoExistente(correo);
		if (correoOk) {
			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					Toast.makeText(NuevoUsuarioVista.this,
							"Correo ya existe", Toast.LENGTH_SHORT).show();
				}
			});
		}
		return correoOk;
	}
}
