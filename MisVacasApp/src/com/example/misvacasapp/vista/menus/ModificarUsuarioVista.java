package com.example.misvacasapp.vista.menus;

import com.example.misvacasapp.R;
import com.example.misvacasapp.controlador.UsuarioControlador;
import com.example.misvacasapp.modelo.Usuario;
import com.example.misvacasapp.vista.LanzarVista;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Clase de la actividad modificar usuario
 * <p>
 * En ella se implementan los métodos que se utilizan para manejar la vista de
 * modificar usuario
 * </p>
 * 
 * @author Sara Martinez Lopez
 * */
public class ModificarUsuarioVista extends ActionBarActivity {
	// Atributos
	/** Id del usuario */
	private String id_usuario;
	/** Contraseña del usuario */
	private String contraseña;
	/** TextView que aparece en la vista que es el nombre del usuario */
	private TextView nombre;
	/** TextView que aparece en la vista que es el primer apellido del usuario */
	private TextView apellido1;
	/** TextView que aparece en la vista que es el segundo apellido del usuario */
	private TextView apellido2;
	/** TextView que aparece en la vista que es la dirección del usuario */
	private TextView direccion;
	/** TextView que aparece en la vista que es la población del usuario */
	private TextView poblacion;
	/** TextViwn que aparece en la vista que es el teléfono del usuario */
	private TextView telefono;

	// Métodos
	/**
	 * Añade la vista de modificar usuario Recoge el usuario y la contraseña de
	 * administrar cuenta Inicializa parametros
	 * */
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

	/**
	 * Rellena los campos de la vista recogiendolos llamando al servicio web del
	 * usuario Recoge el usuario como String y despues lo convierte a tipo
	 * Usuario
	 * */
	private void rellenarCamposTexto() {
		Usuario usuario = new UsuarioControlador().getUsuario(id_usuario,
				contraseña);

		nombre.setText(usuario.getNombre());
		apellido1.setText(usuario.getApellido1());
		apellido2.setText(usuario.getApellido2());
		direccion.setText(usuario.getDireccion());
		poblacion.setText(usuario.getPoblacion());
		telefono.setText(Integer.toString(usuario.getTelefono()));

	}

	/**
	 * Método que se ejecuta cuando se aprieta el botón aceptar. LLama al
	 * servicio web para actualizar los campos del usuario que han sido
	 * modificados Comprueba que el teléfono no sea vacio, si no, no puedes
	 * añadir el usuario
	 * 
	 * @param view
	 *            Vista
	 * */
	public void onClickActualizarUsuario(View view) {

		if (telefono.getText().toString().trim().length() == 0) {
			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					Toast.makeText(ModificarUsuarioVista.this,
							"Telefono no puede ser vacio", Toast.LENGTH_LONG)
							.show();
				}
			});
		} else {
			new UsuarioControlador().actualizarUsuario(id_usuario, nombre
					.getText().toString(), apellido1.getText().toString(),
					apellido2.getText().toString(), direccion.getText()
							.toString(), poblacion.getText().toString(),
					Integer.parseInt(telefono.getText().toString()));
			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					Toast.makeText(ModificarUsuarioVista.this,
							"El usuario ha sido modificado correctamente",
							Toast.LENGTH_LONG).show();
				}
			});

			modificarUsuarioOk();
		}
	}

	/**
	 * Después de que se ha modificado el usuario ya se puede volver a la vista
	 * anterior AdministrarCuentaVista
	 * */
	private void modificarUsuarioOk() {
		new LanzarVista(this).lanzarItemMenu(id_usuario, contraseña, AdministrarCuentaVista.class);
		finish();
	}
}