package com.example.misvacasapp;

import java.util.ArrayList;

import com.example.misvacasapp.bbddinterna.MedicamentoDatosBbdd;
import com.example.misvacasapp.bbddinterna.VacaDatosBbdd;
import com.example.misvacasapp.iterator.AgregadoMedicamento;
import com.example.misvacasapp.iterator.AgregadoUsuario;
import com.example.misvacasapp.iterator.AgregadoVaca;
import com.example.misvacasapp.iterator.IteratorListaMedicamento;
import com.example.misvacasapp.iterator.IteratorListaUsuario;
import com.example.misvacasapp.iterator.IteratorListaVaca;
import com.example.misvacasapp.llamadaWS.LlamadaMedicamentoWS;
import com.example.misvacasapp.llamadaWS.LlamadaUsuarioWS;
import com.example.misvacasapp.llamadaWS.LlamadaVacaWS;
import com.example.misvacasapp.modelo.Medicamento;
import com.example.misvacasapp.modelo.Usuario;
import com.example.misvacasapp.modelo.Vaca;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import android.support.v7.app.ActionBarActivity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.SharedPreferences;
import android.os.AsyncTask;
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
	private ArrayList<Usuario> listaUsuarios;

	/** Lista de animales que tiene el usuario */
	private ArrayList<Vaca> listaVacas;
	/** Lista de medicamentos que tienen los animales de un usuario */
	private ArrayList<Medicamento> listaMedicamentos;

	/** Base de datos interna de los animales de un usuario */
	private VacaDatosBbdd vdbbdd;
	/**
	 * Base de datos interna de los medicamentos que tienen los animales de un
	 * usuario
	 */
	private MedicamentoDatosBbdd mbbdd;

	// M�todos
	/**
	 * M�todo que se ejecuta cuando se acciona el bot�n entrar, para entrar en
	 * la aplicaci�n. Recoge el usuario y contrase�a introducidos por el usuario
	 * y comprueba si existen y son correctos. Nota: Para ejecutar este m�todo
	 * tiene que tener internet y estar activado el servidor.
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
							guardarAutoLogin(usuario, contrase�a);
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
	 * Guarda el usuario y contrase�a para que no se vuelva a introducir mas
	 * veces.
	 * 
	 * @param id_usuario
	 *            String. ID o DNI usuario.
	 * @param contrase�a
	 *            String. Contrase�a de usuario.
	 */
	private void guardarAutoLogin(String id_usuario, String contrase�a) {
		SharedPreferences settings = getSharedPreferences("MisDatos",
				Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = settings.edit();
		editor.putString("id_usuario", id_usuario);
		editor.putString("contrase�a", contrase�a);
		editor.commit();
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
				Usuario usuario = json.fromJson(res, Usuario.class);
				if (res.compareTo("") == 0) {
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							Toast.makeText(
									Login.this,
									"El servidor no funciona. Intentelo de nuevo mas tarde.",
									Toast.LENGTH_LONG).show();
							setContentView(R.layout.activity_login);
						}
					});
				} else if (usuario.getRol() == 1) {
					// Admin
				} else if (usuario.getRol() == 0) {
					lanzarUsuario();
				}
			}
		};
		hilo.start();
	}

	/**
	 * Cambia a la vista del usuario. Crea la base de datos interna de los
	 * animales y de sus medicamentos y las rellena con los datos recogidos de
	 * la base de datos cloud.
	 * */
	private void lanzarUsuario() {

		vdbbdd = new VacaDatosBbdd(getApplicationContext());
		mbbdd = new MedicamentoDatosBbdd(getApplicationContext());

		if (vdbbdd.getRegistros() == 0
				|| vdbbdd.getRegistros(usuario) < vdbbdd.getRegistros()) {

			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					Toast.makeText(
							Login.this,
							"Sinconizando. Este proceso puede tardar unos minutos",
							Toast.LENGTH_LONG).show();
				}
			});

			vdbbdd = new VacaDatosBbdd(getApplicationContext(), getListaVacas());
			getListaMedicamentos();
			mbbdd = new MedicamentoDatosBbdd(getApplicationContext(),
					listaMedicamentos);
		}

		new LanzarVista(this).lanzarUsuarioVista(usuario, contrase�a);
		this.finish();

	}

	/**
	 * M�todo que se acciona cuando pulsas sobre "Nuevo Usuario". Cambia a la
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
					enviar(texto.getText().toString(), "",
							"misvacasapp@gmail.es", "Mis Vacas APP",
							"Correo de autenticacion",
							"Su usuario y contrase�a son: " + u.getDni());
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							Toast.makeText(Login.this, "No funciona",
									Toast.LENGTH_SHORT).show();
						}
					});

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
	private void enviar(final String emailTo, String nameTo,
			final String emailFrom, String nameFrom, final String subject,
			final String body) {
		new AsyncTask<Void, Void, Void>() {

			@Override
			protected void onPreExecute() {
			}

			@Override
			protected Void doInBackground(Void... params) {
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
				return null;
			}

			@Override
			protected void onPostExecute(Void res) {

			}
		}.execute();

		System.out.println("Correo enviado");
	}

	// /**
	// * Recoge todos los usuario de la base de datos cloud y los guarda en un
	// * arrayList
	// */
	// private void getListaUsuarios() {
	// listaUsuarios = new ArrayList<Usuario>();
	// Thread hilo = new Thread() {
	// String res = "";
	// Gson json = new GsonBuilder().setPrettyPrinting()
	// .setDateFormat("dd-MM-yyyy").create();
	// LlamadaUsuarioWS llamada = new LlamadaUsuarioWS();
	//
	// public void run() {
	// res = llamada.LlamadaListaUsuarios();
	// listaUsuarios = json.fromJson(res,
	// new TypeToken<ArrayList<Usuario>>() {
	// }.getType());
	// }
	// };
	// hilo.start();
	// }

	/**
	 * Recoge todos los animales de la base de datos cloud y los guarda en un
	 * arrayList
	 * 
	 * @return ArrayList<Vaca> listaVacas. Lista de vacas de un usuario
	 */
	public ArrayList<Vaca> getListaVacas() {
		String res = "";
		Gson json = new GsonBuilder().setPrettyPrinting()
				.setDateFormat("dd-MM-yyyy").create();
		LlamadaVacaWS llamada = new LlamadaVacaWS();

		res = llamada.LlamadaListaVacas(usuario);
		listaVacas = json.fromJson(res, new TypeToken<ArrayList<Vaca>>() {
		}.getType());

		return listaVacas;
	}

	/**
	 * Recoge los medicamentos de todos los animales que tiene un usuario y los
	 * guarda en un arrayList
	 */
	public void getListaMedicamentos() {
		AgregadoVaca agregado = new AgregadoVaca(listaVacas);
		IteratorListaVaca i = new IteratorListaVaca(agregado);
		while (i.hasNext()) {
			String res = "";
			ArrayList<Medicamento> listaAux = new ArrayList<Medicamento>();
			Gson json = new GsonBuilder().setPrettyPrinting()
					.setDateFormat("dd-MM-yyyy").create();
			LlamadaMedicamentoWS llamada = new LlamadaMedicamentoWS();
			res = llamada.LlamadaListaMedicamentos(i.actualElement()
					.getId_vaca());
			listaAux = json.fromJson(res,
					new TypeToken<ArrayList<Medicamento>>() {
					}.getType());
			AgregadoMedicamento agregadoMedicamento = new AgregadoMedicamento(
					listaAux);
			IteratorListaMedicamento iMedicamento = new IteratorListaMedicamento(
					agregadoMedicamento);
			while (iMedicamento.hasNext()) {
				listaMedicamentos.add(iMedicamento.actualElement());
				iMedicamento.next();
			}
			i.next();
		}
	}

	/**
	 * Recorre la lista de usuarios y mira si el correo pasado por parametro
	 * existe y si existe devuelve un "true".
	 * 
	 * @param correo
	 *            String. Correo del usuario
	 * @return boolean. True si existe correo, falso si no existe.
	 */
	private boolean correoExistente(String correo) {
		boolean resultado = false;
		AgregadoUsuario agregado = new AgregadoUsuario(listaUsuarios);
		IteratorListaUsuario i = (IteratorListaUsuario) agregado
				.createIterator();
		while (i.hasNext()) {
			if (i.actualElement().getCorreo().equals(correo))
				resultado = true;
			i.next();
		}
		return resultado;
	}

	/**
	 * Devuelve el usuario usuario si el correo pasado por parametro existe
	 * 
	 * @param correo
	 *            String. Correo del usuario
	 * @return Usuario u. Usuario
	 */
	private Usuario getUsuario(String correo) {
		Usuario u = new Usuario();
		AgregadoUsuario agregado = new AgregadoUsuario(listaUsuarios);
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
	 * A�ade la vista del login. Inicializa los atributos.
	 * 
	 * @param savedInstanceState
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		listaVacas = new ArrayList<Vaca>();
		listaMedicamentos = new ArrayList<Medicamento>();

		SharedPreferences prefs = getSharedPreferences("MisDatos",
				Context.MODE_PRIVATE);
		String id_usuarioGuardado = prefs.getString("id_usuario", "");
		String contrase�aGuardado = prefs.getString("contrase�a", "");
		if (id_usuarioGuardado.equals("") && contrase�aGuardado.equals("")) {
			setContentView(R.layout.activity_login);
			// getListaUsuarios();
		} else {
			usuario = id_usuarioGuardado;
			contrase�a = contrase�aGuardado;
			rol();
		}
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
			AlertDialog.Builder alerta = new AlertDialog.Builder(this);
			alerta.setTitle("Ayuda");
			alerta.setMessage("Introduce el usuario y contrase�a para entrar en la aplicaci�n."
					+ "\n"
					+ "Si no esta registrado pulse en bot�n Nuevo usuario."
					+ "\n"
					+ "Si no recuerda el usuario y contrase�a pulse en recordar usuario");
			alerta.show();
			return true;
		} else if (id == R.id.recordar_usuario) {
			alertaCorreo();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}