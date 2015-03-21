package com.example.misvacasapp.vista;

import com.example.misvacasapp.R;
import com.example.misvacasapp.controlador.UsuarioControlador;
import com.example.misvacasapp.modelo.Usuario;

import android.support.v7.app.ActionBarActivity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Clase de la actividad Login En ella se implementan los métodos que se
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
	 * Contraseña del usuario que introduce a traves de la pantalla login
	 */
	private String contraseña;

	// Metodos
	/**
	 * Recoge el usuario y contraseña introducidos por el usuario y comprueba si
	 * existen y son correctos
	 * 
	 * @param v
	 *            Vista que hace el click en el boton
	 */
	public void onClick(View v) {
		usuario = ((TextView) findViewById(R.id.usuario)).getText().toString();
		contraseña = ((TextView) findViewById(R.id.contrasena)).getText()
				.toString();

		boolean existe = new UsuarioControlador().UsuarioExistente(usuario,
				contraseña);
		if (existe) {
			rol();
		} else {
			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					Toast.makeText(Login.this, "Usuario no existe",
							Toast.LENGTH_SHORT).show();
				}
			});
		}
	}

	/**
	 * Comprueba el rol del usuario Si es administrador o no
	 */
	private void rol() {
		Usuario u = new UsuarioControlador().getUsuario(usuario, contraseña);
		if (u.getRol() == 1) {
			// Admin
		} else if (u.getRol() == 0) {
			lanzarUsuario();
		}
	}

	/**
	 * Cambia a la vista del usuario
	 * */
	private void lanzarUsuario() {
		new LanzarVista(this).lanzarUsuarioVista(usuario, contraseña);
		finish();
	}

	/**
	 * Método que se acciona cuando pulsas sobre "Nuevo Ususario". Cambia a la
	 * vista para crear un nuevo usuario
	 * 
	 * @param v
	 *            Vista
	 */
	public void onClickNuevoUsuario(View v) {
		new LanzarVista(this).lanzarNuevoUsusario();
	}

	/**
	 * Método que se acciona cuando pulsas sobre "¿Has olvidado la contraseña?".
	 * Muestra la alerta para enviar el usuario y la contraseña por correo
	 * 
	 * @param v
	 */
	public void onClickPedirContraseña(View v) {
		alertaCorreo();
	}

	/**
	 * Alerta para introducir el correo electronico y decir si el correo esta en
	 * la base de datos o no. Si el correo esta en la base de datos envia un
	 * mensaje al usuario para recordarle la contraseña
	 */
	private void alertaCorreo() {
		LayoutInflater inflater = LayoutInflater.from(this);
		View layout = inflater.inflate(R.layout.buscar_layout, null);
		AlertDialog.Builder dialogo = new AlertDialog.Builder(this);
		dialogo.setView(layout);
		dialogo.setMessage("Introduzca el correo electronico");
		final EditText texto = (EditText) layout.findViewById(R.id.busca);
		/** Método del botón aceptar del dialogo */
		dialogo.setPositiveButton("Aceptar", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				if (texto.getText().toString().equals("")) {
					dialog.cancel();
				} else {
					if (new UsuarioControlador().correoExistente(texto
							.getText().toString())) {
						Usuario u = new UsuarioControlador().getUsuario(texto
								.getText().toString());
						enviar(texto.getText().toString(),
								"",
								"misvacasapp@gmail.es",
								"Mis Vacas APP",
								"Correo de autenticacion",
								"Su usuario y contraseña son: /n Usuario: "
										+ u.getDni() + "/nContraseña"
										+ u.getContraseña());
					} else {
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								Toast.makeText(Login.this, "Correo no existe",
										Toast.LENGTH_SHORT).show();
							}
						});
					}
				}
			}
		});
		/** Método del botón cancelar del dialogo */
		dialogo.setNegativeButton("Cancelar", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		});
		dialogo.show();
	}

	/**
	 * Métodos que envia un correo electronico al usuario recordandole el
	 * usuario y contraseña que ha olvidado
	 * 
	 * @param emailTo
	 * @param nameTo
	 * @param emailFrom
	 * @param nameFrom
	 * @param subject
	 * @param body
	 */
	private void enviar(String emailTo, String nameTo, String emailFrom,
			String nameFrom, String subject, String body) {

		Mail m = new Mail("misvacasapp@gmail.es", "sara130490");

		String[] toArr = { emailTo, "misvacasapp@gmail.es" };
		m.setTo(toArr);
		m.setFrom(emailFrom);
		m.setSubject(subject);
		m.setBody(body);
		try {
			m.send();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Correo enviado");
	}

	/**
	 * Añade la vista del login
	 * 
	 * @param savedInstanceState
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
	}

	/**
	 * Añade el menu a la vista login
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
	 * Añade los item al menu
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