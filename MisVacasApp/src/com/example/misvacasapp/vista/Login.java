package com.example.misvacasapp.vista;

import com.example.misvacasapp.R;
import com.example.misvacasapp.controlador.UsuarioControlador;
import com.example.misvacasapp.controlador.modelo.llamadaWS.LlamadaUsuarioWS;
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
 * Clase de la actividad Login En ella se implementan los m�todos que se
 * utilizan para hacer el login
 * 
 * @author Sara Martinez Lopez
 * */
public class Login extends ActionBarActivity {
	// Atributos
	/*
	 * Id usuario que se introduce a trav�s de la pantalla login
	 */
	private String usuario;
	/*
	 * Contrase�a del usuario que se introduce a trav�s de la pantalla login
	 */
	private String contrase�a;

	boolean existe;

	// Metodos
	/**
	 * Recoge el usuario y contrase�a introducidos por el usuario y comprueba si
	 * existen y son correctos
	 * 
	 * @param v
	 *            Vista que hace el click en el boton
	 */
	public void onClick(View v) {

		Thread hilo = new Thread() {
			String res = "";
			LlamadaUsuarioWS llamada = new LlamadaUsuarioWS();

			public void run() {
				usuario = ((TextView) findViewById(R.id.usuario)).getText()
						.toString();
				contrase�a = ((TextView) findViewById(R.id.contrasena))
						.getText().toString();
				res = llamada.LlamadaUsuarioExistente(usuario, contrase�a);
				System.out.println("RES" + res);
				if (res.compareTo("true") == 0) {
					existe = true;
					exixt(existe);
				} else if (res.compareTo("false") == 0) {
					existe = false;
					exixt(existe);
				}// TODO COMPROBAR CUANDO NO HAY CONEXION
			}
		};
		hilo.start();
		

	}

	private void exixt(boolean existe) {
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
		Usuario u = new UsuarioControlador().getUsuario(usuario, contrase�a);
		System.out.println(u.getRol());
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
		new LanzarVista(this).lanzarUsuarioVista(usuario, contrase�a);
		finish();
	}

	/**
	 * M�todo que se acciona cuando pulsas sobre "Nuevo Ususario". Cambia a la
	 * vista para crear un nuevo usuario
	 * 
	 * @param v
	 *            Vista
	 */
	public void onClickNuevoUsuario(View v) {
		new LanzarVista(this).lanzarNuevoUsusario();
	}

	/**
	 * M�todo que se acciona cuando pulsas sobre "�Has olvidado la contrase�a?".
	 * Muestra la alerta para enviar el usuario y la contrase�a por correo
	 * 
	 * @param v
	 */
	public void onClickPedirContrase�a(View v) {
		alertaCorreo();
	}

	/**
	 * Alerta para introducir el correo electronico y decir si el correo esta en
	 * la base de datos o no. Si el correo esta en la base de datos envia un
	 * mensaje al usuario para recordarle la contrase�a
	 */
	private void alertaCorreo() {
		LayoutInflater inflater = LayoutInflater.from(this);
		View layout = inflater.inflate(R.layout.buscar_layout, null);
		AlertDialog.Builder dialogo = new AlertDialog.Builder(this);
		dialogo.setView(layout);
		dialogo.setMessage("Introduzca el correo electronico");
		final EditText texto = (EditText) layout.findViewById(R.id.busca);
		/** M�todo del bot�n aceptar del dialogo */
		dialogo.setPositiveButton("Aceptar", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {

				if (new UsuarioControlador().correoExistente(texto.getText()
						.toString())) {
					Usuario u = new UsuarioControlador().getUsuario(texto
							.getText().toString());
					enviar(texto.getText().toString(),
							"",
							"Su usuario y contrase�a son: /n Usuario: "
									+ u.getDni() + "/nContrase�a"
									+ u.getContrase�a());
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
		});
		/** M�todo del bot�n cancelar del dialogo */
		dialogo.setNegativeButton("Cancelar", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		});
		dialogo.show();
	}

	/**
	 * M�todos que envia un correo electronico al usuario recordandole el
	 * usuario y contrase�a que ha olvidado
	 * 
	 * @param emailTo
	 * @param nameTo
	 * @param emailFrom
	 * @param nameFrom
	 * @param subject
	 * @param body
	 */
	private void enviar(String emailTo, String nameTo, String body) {

		Mail m = new Mail();

		String[] toArr = { emailTo, "misvacasapp@gmail.es" };
		m.setTo(toArr);
		m.setBody(body);
		try {
			m.send();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Correo enviado");
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
		int id = item.getItemId();
		if (id == R.id.ayuda) {
			System.out.println("click menu ayuda");
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}