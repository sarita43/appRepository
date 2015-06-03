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
 * @author Sara Martínez López
 * 
 */
public class LanzarVista {
	// ----------------------------------Atributos------------------------------//
	private Context context;

	// ------------------------------------Métodos-------------------------------//

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
	 * Método que lanza la vista del loguin
	 */
	public void lanzarLogin() {
		Intent i = new Intent(context, Login.class);
		context.startActivity(i);
	}

	/**
	 * Método que lanza la vista del usuario pasandole como parámetros el
	 * usuario y la contraseña
	 * 
	 * @param id_usuario
	 * @param contraseña
	 */
	public void lanzarUsuarioVista(String id_usuario, String contraseña) {
		Intent i = new Intent(context, UsuarioVista.class);
		i.putExtra("id_usuario", id_usuario);
		i.putExtra("contraseña", contraseña);
		context.startActivity(i);
	}

	/**
	 * Método que lanza la vista para crear un usuario nuevo
	 */
	public void lanzarNuevoUsusario() {
		Intent i = new Intent(context, NuevoUsuarioVista.class);
		context.startActivity(i);
	}

	/**
	 * Método que lanza la vista para administrar la cuenta del usuario
	 * pasandole como parámetro el usuario y la contrañesa
	 * 
	 * @param id_usuario
	 * @param contraseña
	 */
	public void lanzarAdministrarCuenta(String id_usuario, String contraseña) {
		Intent i = new Intent(context, AdministrarCuentaVista.class);
		i.putExtra("id_usuario", id_usuario);
		i.putExtra("contraseña", contraseña);
		context.startActivity(i);
	}

	/**
	 * Método que lanza la vista de la ficha del animal pasandole como
	 * parámetros el id del animal y el id del usuario
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
	 * Método que lanza la vista para cambiar la foto pasandole como parámetro
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
	 * Método que lanza la vista que añade un nuevo animal pasandole como
	 * parámetro el id del usuario
	 * 
	 * @param id_usuario
	 */
	public void lanzarAñadirVaca(String id_usuario) {
		Intent i = new Intent(context, AniadirVacaVista.class);
		i.putExtra("id_usuario", id_usuario);
		context.startActivity(i);
	}

	/**
	 * Método que lanza una vista del menú, pasandole como parámetro el id del
	 * usuario, la contraseña y el nombre del item del menú que se quiere lanzar
	 * la vista
	 * 
	 * @param id_usuario
	 * @param contraseña
	 * @param nombreVista
	 */
	@SuppressWarnings("rawtypes")
	public void lanzarItemMenu(String id_usuario, String contraseña,
			Class nombreVista) {
		Intent i = new Intent(context, nombreVista);
		i.putExtra("id_usuario", id_usuario);
		i.putExtra("contraseña", contraseña);
		context.startActivity(i);
	}

	/**
	 * Método que lanza la vista de los graficos de producción de leche y carne
	 */
	public void lanzarProduccion() {
		Intent i = new Intent(context, ProducionVista.class);
		context.startActivity(i);
	}

	/**
	 * Método que lanza la vista de los medicamentos de un animal, pasando por
	 * parámetro el id del animal
	 * 
	 * @param id_vaca
	 */
	public void lanzarMedicamentos(String id_vaca) {
		Intent i = new Intent(context, MedicamentosVista.class);
		i.putExtra("id_vaca", id_vaca);
		context.startActivity(i);
	}

	/**
	 * Método que lanza la vista de la descripción del medicamento, pasandole
	 * por parámetro el id del medicamento y el id del animal
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
	 * Método que lanza la vista para añadir medicamentos, pasandole por
	 * parámetro el id del animal
	 * 
	 * @param id_vaca
	 */
	public void lanzarAñadirMedicamento(String id_vaca) {
		Intent i = new Intent(context, AniadirMedicamentoVista.class);
		i.putExtra("id_vaca", id_vaca);
		context.startActivity(i);
	}
}