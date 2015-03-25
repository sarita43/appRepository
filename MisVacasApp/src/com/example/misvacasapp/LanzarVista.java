package com.example.misvacasapp;

import com.example.misvacasapp.aniadir.AniadirMedicamentoVista;
import com.example.misvacasapp.aniadir.AniadirVacaVista;
import com.example.misvacasapp.aniadir.NuevoUsuarioVista;
import com.example.misvacasapp.menus.AdministrarCuentaVista;

import android.content.Context;
import android.content.Intent;

public class LanzarVista {
	private Context context;

	public LanzarVista(Context context) {
		this.context = context;
	}

	public void lanzarLogin(){
		Intent i = new Intent(context, Login.class);
		context.startActivity(i);
	}
	
	public void lanzarUsuarioVista(String id_usuario, String contraseña) {
		Intent i = new Intent(context, UsuarioVista.class);
		i.putExtra("id_usuario", id_usuario);
		i.putExtra("contraseña", contraseña);
		context.startActivity(i);
	}

	public void lanzarNuevoUsusario() {
		Intent i = new Intent(context, NuevoUsuarioVista.class);
		context.startActivity(i);
	}

	public void lanzarAdministrarCuenta(String id_usuario, String contraseña) {
		Intent i = new Intent(context, AdministrarCuentaVista.class);
		i.putExtra("id_usuario", id_usuario);
		i.putExtra("contraseña", contraseña);
		context.startActivity(i);
	}

	public void lanzarVaca(String id_vaca, String id_usuario) {
		Intent i = new Intent(context, VacaVista.class);
		i.putExtra("id_vaca", id_vaca);
		i.putExtra("id_usuario", id_usuario);
		context.startActivity(i);
	}

	public void lanzarAñadirVaca(String id_usuario) {
		Intent i = new Intent(context, AniadirVacaVista.class);
		i.putExtra("id_usuario", id_usuario);
		context.startActivity(i);
	}

	@SuppressWarnings("rawtypes")
	public void lanzarItemMenu(String id_usuario, String contraseña,
			Class nombreVista) {
		Intent i = new Intent(context, nombreVista);
		i.putExtra("id_usuario", id_usuario);
		i.putExtra("contraseña", contraseña);
		context.startActivity(i);
	}

	public void lanzarMedicamentos(String id_vaca) {
		Intent i = new Intent(context, MedicamentosVista.class);
		i.putExtra("id_vaca", id_vaca);
		context.startActivity(i);
	}

	public void lanzarMedicamento(int id_medicamento, String id_vaca) {
		Intent i = new Intent(context, MedicamentoVista.class);
		i.putExtra("id_medicamento", id_medicamento);
		i.putExtra("id_vaca", id_vaca);
		context.startActivity(i);
	}
	
	public void lanzarAñadirMedicamento(String id_vaca,String listaMedicamentos){
		Intent i = new Intent(context, AniadirMedicamentoVista.class);
		i.putExtra("id_vaca", id_vaca);
		i.putExtra("listaMedicamentos", listaMedicamentos);
		context.startActivity(i);
	}

}