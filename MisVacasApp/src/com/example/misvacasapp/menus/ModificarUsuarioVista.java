package com.example.misvacasapp.menus;

import com.example.misvacasapp.R;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

public class ModificarUsuarioVista extends ActionBarActivity{
	
	private String id_usuario;
	private String contraseña;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_administrar_cuenta);
		
		Bundle bundle = getIntent().getExtras();
		id_usuario = bundle.getString("id_usuario");
		contraseña = bundle.getString("contraseña");
	}

}
