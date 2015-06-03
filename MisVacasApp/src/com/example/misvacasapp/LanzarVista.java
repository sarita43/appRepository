package com.example.misvacasapp;

import com.example.misvacasapp.aniadir.AniadirMedicamentoVista;
import com.example.misvacasapp.aniadir.AniadirVacaVista;
import com.example.misvacasapp.aniadir.NuevoUsuarioVista;
import com.example.misvacasapp.graficos.ProducionVista;
import com.example.misvacasapp.menus.AdministrarCuentaVista;

import android.content.Context;
import android.content.Intent;

/**
 * Clase que se encarga de lanzar las vistas
 * 
 * @author Sara Mart�nez L�pez
 * 
 */
public class LanzarVista {
	// ----------------------------------Atributos------------------------------//
	private Context context;

	// ------------------------------------M�todos-------------------------------//

	/**
	 * Constructor de la clase
	 * 
	 * @param context
	 *            Constexto de la vista
	 */
	public LanzarVista(Context context) {
		this.context = context;
	}

	/**
	 * M�todo que lanza la vista del loguin
	 */
	public void lanzarLogin() {
		Intent i = new Intent(context, Login.class);
		context.startActivity(i);
	}

	/**
	 * M�todo que lanza la vista del usuario pasandole como par�metros el
	 * usuario y la contrase�a
	 * 
	 * @param id_usuario
	 * @param contrase�a
	 */
	public void lanzarUsuarioVista(String id_usuario, String contrase�a) {
		Intent i = new Intent(context, UsuarioVista.class);
		i.putExtra("id_usuario", id_usuario);
		i.putExtra("contrase�a", contrase�a);
		context.startActivity(i);
	}

	/**
	 * M�todo que lanza la vista para crear un usuario nuevo
	 */
	public void lanzarNuevoUsusario() {
		Intent i = new Intent(context, NuevoUsuarioVista.class);
		context.startActivity(i);
	}

	/**
	 * M�todo que lanza la vista para administrar la cuenta del usuario
	 * pasandole como par�metro el usuario y la contra�esa
	 * 
	 * @param id_usuario
	 * @param contrase�a
	 */
	public void lanzarAdministrarCuenta(String id_usuario, String contrase�a) {
		Intent i = new Intent(context, AdministrarCuentaVista.class);
		i.putExtra("id_usuario", id_usuario);
		i.putExtra("contrase�a", contrase�a);
		context.startActivity(i);
	}

	/**
	 * M�todo que lanza la vista de la ficha del animal pasandole como
	 * par�metros el id del animal y el id del usuario
	 * 
	 * @param id_vaca
	 * @param id_usuario
	 */
	public void lanzarVaca(String id_vaca, String id_usuario) {
		Intent i = new Intent(context, VacaVista.class);
		i.putExtra("id_vaca", id_vaca);
		i.putExtra("id_usuario", id_usuario);
		context.startActivity(i);
	}

	/**
	 * M�todo que lanza la vista para cambiar la foto pasandole como par�metro
	 * la el id del animal
	 * 
	 * @param id_vaca
	 */
	public void lanzarCambiarFoto(String id_vaca) {
		Intent i = new Intent(context, CambiarFotoVista.class);
		i.putExtra("id_vaca", id_vaca);
		context.startActivity(i);
	}

	/**
	 * M�todo que lanza la vista que a�ade un nuevo animal pasandole como
	 * par�metro el id del usuario
	 * 
	 * @param id_usuario
	 */
	public void lanzarA�adirVaca(String id_usuario) {
		Intent i = new Intent(context, AniadirVacaVista.class);
		i.putExtra("id_usuario", id_usuario);
		context.startActivity(i);
	}

	/**
	 * M�todo que lanza una vista del men�, pasandole como par�metro el id del
	 * usuario, la contrase�a y el nombre del item del men� que se quiere lanzar
	 * la vista
	 * 
	 * @param id_usuario
	 * @param contrase�a
	 * @param nombreVista
	 */
	@SuppressWarnings("rawtypes")
	public void lanzarItemMenu(String id_usuario, String contrase�a,
			Class nombreVista) {
		Intent i = new Intent(context, nombreVista);
		i.putExtra("id_usuario", id_usuario);
		i.putExtra("contrase�a", contrase�a);
		context.startActivity(i);
	}

	/**
	 * M�todo que lanza la vista de los graficos de producci�n de leche y carne
	 */
	public void lanzarProduccion() {
		Intent i = new Intent(context, ProducionVista.class);
		context.startActivity(i);
	}

	/**
	 * M�todo que lanza la vista de los medicamentos de un animal, pasando por
	 * par�metro el id del animal
	 * 
	 * @param id_vaca
	 */
	public void lanzarMedicamentos(String id_vaca) {
		Intent i = new Intent(context, MedicamentosVista.class);
		i.putExtra("id_vaca", id_vaca);
		context.startActivity(i);
	}

	/**
	 * M�todo que lanza la vista de la descripci�n del medicamento, pasandole
	 * por par�metro el id del medicamento y el id del animal
	 * 
	 * @param id_medicamento
	 * @param id_vaca
	 */
	public void lanzarMedicamento(int id_medicamento, String id_vaca) {
		Intent i = new Intent(context, MedicamentoVista.class);
		i.putExtra("id_medicamento", id_medicamento);
		i.putExtra("id_vaca", id_vaca);
		context.startActivity(i);
	}

	/**
	 * M�todo que lanza la vista para a�adir medicamentos, pasandole por
	 * par�metro el id del animal
	 * 
	 * @param id_vaca
	 */
	public void lanzarA�adirMedicamento(String id_vaca) {
		Intent i = new Intent(context, AniadirMedicamentoVista.class);
		i.putExtra("id_vaca", id_vaca);
		context.startActivity(i);
	}
}