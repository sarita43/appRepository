package com.example.misvacasapp.vista;

import java.util.ArrayList;

import com.example.misvacasapp.controlador.MedicamentoControlador;
import com.example.misvacasapp.modelo.Medicamento;
import com.example.misvacasapp.vista.aniadir.AniadirMedicamentoVista;
import com.example.misvacasapp.vista.aniadir.AniadirVacaVista;
import com.example.misvacasapp.vista.aniadir.NuevoUsuarioVista;

import android.content.Context;
import android.content.Intent;

public class LanzarVista {

	private Context context;
	
	public LanzarVista(Context context){
		this.context = context;
	}
	
	public void lanzarUsuarioVista(String id_usuario,String contraseña){
		Intent i = new Intent(context, UsuarioVista.class);
		i.putExtra("id_usuario", id_usuario);
		i.putExtra("contraseña", contraseña);
		context.startActivity(i);
	}
	
	public void lanzarNuevoUsusario(){
		Intent i = new Intent(context, NuevoUsuarioVista.class);
		context.startActivity(i);
	}
	
	public void lanzarAdministrarCuenta(String id_usuario,String contraseña){
		Intent i = new Intent(context, UsuarioVista.class);
		i.putExtra("id_usuario", id_usuario);
		i.putExtra("contraseña", contraseña);
		context.startActivity(i);
	}
	
	public void lanzarVaca(String id_vaca, String id_usuario){
		Intent i = new Intent(context, VacaVista.class);
		i.putExtra("id_vaca", id_vaca);
		i.putExtra("id_usuario", id_usuario);
		context.startActivity(i);
	}
	
	public void lanzarAñadirVaca(String id_usuario){
		Intent i = new Intent(context, AniadirVacaVista.class);
		i.putExtra("id_usuario", id_usuario);
		context.startActivity(i);
	}
	
	@SuppressWarnings("rawtypes")
	public void lanzarItemMenu(String id_usuario,String contraseña, Class nombreVista){
		Intent i = new Intent(context, nombreVista);
		i.putExtra("id_usuario", id_usuario);
		i.putExtra("contraseña", contraseña);
		context.startActivity(i);
	}
	
	public void lanzarMedicamentos(String id_vaca){
		Intent i = new Intent(context, MedicamentosVista.class);
		i.putExtra("id_vaca", id_vaca);
		context.startActivity(i);
	}
	
	public void lanzarMedicamento(int id_medicamento, String id_vaca){
		Intent i = new Intent(context, MedicamentoVista.class);
		i.putExtra("id_medicamento", id_medicamento);
		i.putExtra("id_vaca", id_vaca);
		context.startActivity(i);
	}
	
	public void añadirMedicamento(String id_vaca){
		ArrayList<Medicamento> listaMedicamentos = new MedicamentoControlador().listaMedicamentos(id_vaca);
		Intent i = new Intent(context, AniadirMedicamentoVista.class);
		i.putExtra("id_vaca", id_vaca);
		i.putExtra("listaMedicamentos", listaMedicamentos);
		context.startActivity(i);
	}
}
