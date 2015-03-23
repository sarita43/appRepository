package com.example.misvacasapp.vista.menus;

import com.example.misvacasapp.R;
import com.example.misvacasapp.controlador.UsuarioControlador;
import com.example.misvacasapp.vista.LanzarVista;

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

	/**
	 * Método que se ejecuta cuando se da al botón aceptar En el se recogen los
	 * parámetros del los textView Se comprueba que la contraseña introducida y
	 * la que tiene el usuario sean iguales
	 * */
	public void onClickCambioContraseña(View view) {
		contraseñaActual = (TextView) findViewById(R.id.contrasena_actual_texto);
		contraseñaNueva = (TextView) findViewById(R.id.nueva_contrasena_texto);
		contraseñaNuevaRepetida = (TextView) findViewById(R.id.repita_nueva_contrasena_texto);

		if (contraseña.equals(contraseñaActual.getText().toString())) {
			comprobarContraseña();
		} else {
			Toast.makeText(NuevaContraseniaVista.this, "Contraseña incorrecta",
					Toast.LENGTH_LONG).show();
		}
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

			new LanzarVista(this).lanzarItemMenu(id_usuario, contraseña, AdministrarCuentaVista.class);
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
		new UsuarioControlador().cambiarContraseña(id_usuario, contraseñaNueva.getText().toString());
	}
}