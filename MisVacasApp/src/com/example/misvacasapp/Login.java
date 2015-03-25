package com.example.misvacasapp;

import java.util.ArrayList;

import com.example.misvacasapp.iterator.AgregadoUsuario;
import com.example.misvacasapp.iterator.IteratorListaUsuario;
import com.example.misvacasapp.llamadaWS.LlamadaUsuarioWS;
import com.example.misvacasapp.modelo.Usuario;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

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
	/**
	 * Id usuario que introduce a traves de la pantalla login
	 */
	private String usuario;
	/**
	 * Contrase�a del usuario que introduce a traves de la pantalla login
	 */
	private String contrase�a;

	/** Lista de usuarios de la aplicaci�n */
	private ArrayList<Usuario> lista;

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
				if (correoExistente(texto.getText().toString())) {
					Usuario u = getUsuario(texto.getText().toString());
					enviar(texto.getText().toString(),
							"",
							"misvacasapp@gmail.es",
							"Mis Vacas APP",
							"Correo de autenticacion",
							"Su usuario y contrase�a son:  Usuario: "
									+ u.getDni() + " Contrase�a: "
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

	private void getListaUsuarios() {
		lista = new ArrayList<Usuario>();
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

	private boolean correoExistente(String correo) {
		boolean resultado = false;
		AgregadoUsuario agregado = new AgregadoUsuario(lista);
		IteratorListaUsuario i = (IteratorListaUsuario) agregado
				.createIterator();
		while (i.hasNext()) {
			if (i.actualElement().getCorreo().equals(correo))
				resultado = true;
			i.next();
		}
		return resultado;
	}

	private Usuario getUsuario(String correo) {
		Usuario u = new Usuario();
		AgregadoUsuario agregado = new AgregadoUsuario(lista);
		IteratorListaUsuario i = (IteratorListaUsuario) agregado
				.createIterator();
		while (i.hasNext()) {
			if (i.actualElement().getCorreo().equals(correo))
				u = i.actualElement();
			i.next();
		}
		return u;
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
		getListaUsuarios();
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