package com.example.misvacasapp;

import java.util.ArrayList;

import com.example.misvacasapp.adapter.AdapterListaMenu;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ListView;

public class AdministrarCuentaVista extends ActionBarActivity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_administrar_cuenta);
		
		ListView lista = (ListView)findViewById(R.id.listaAdministracionCuenta);
		ArrayList<String> items = new ArrayList<String>();
		items.add("\n Modificar usuario\n");
		items.add("\n Cambiar contraseña\n");
		items.add("\n Eliminar cuenta\n");
		AdapterListaMenu adapter = new AdapterListaMenu(this, items);
		
		lista.setAdapter(adapter);
	}

}
