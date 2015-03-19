package com.example.misvacasapp;
import com.example.misvacasapp.aniadir.NuevoUsuarioVista;

import android.content.Context;
import android.content.Intent;



public class LanzarVista {

	private Context context;
	
	public LanzarVista(Context context){
		this.context = context;
	}
	
	public void lanzarUsuarioVista(String usuario,String contraseña){
		Intent i = new Intent(context, UsuarioVista.class);
		i.putExtra("id_usuario", usuario);
		i.putExtra("contraseña", contraseña);
		context.startActivity(i);
	}
	
	public void lanzarNuevoUsusario(){
		Intent i = new Intent(context, NuevoUsuarioVista.class);
		context.startActivity(i);
	}
}
