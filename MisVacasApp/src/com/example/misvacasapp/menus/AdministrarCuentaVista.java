package com.example.misvacasapp.menus;

import java.util.ArrayList;

import com.example.misvacasapp.R;
import com.example.misvacasapp.UsuarioVista;
import com.example.misvacasapp.adapter.AdapterListaMenu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class AdministrarCuentaVista extends ActionBarActivity{
	
	private AdapterListaMenu adapter;
	private String id_usuario;
	private String contraseña;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_administrar_cuenta);
		
		Bundle bundle = getIntent().getExtras();
		id_usuario = bundle.getString("id_usuario");
		contraseña = bundle.getString("contraseña");
		
		ListView lista = (ListView)findViewById(R.id.listaAdministracionCuenta);
		ArrayList<String> items = new ArrayList<String>();
		items.add("\n Modificar usuario\n");
		items.add("\n Cambiar contraseña\n");
		items.add("\n Eliminar cuenta\n");
		adapter = new AdapterListaMenu(this, items);
		
		lista.setAdapter(adapter);
		
		lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				String item = adapter.getItem(position);
				elegirMenu(item);
			}
		});
	}

	private void elegirMenu(String item) {
		switch (item) {
		case "\n Modificar usuario\n":
			System.out.println("\n Modificar usuario\n");
			break;
		case "\n Cambiar contraseña\n":
			Intent i = new Intent(this, NuevaContraseniaVista.class);
			i.putExtra("id_usuario",id_usuario);
			i.putExtra("contraseña",contraseña);
			startActivity(i);
			break;
		case "\n Eliminar cuenta\n":
			System.out.println("\n Eliminar cuenta\n");
			break;
		default:
			break;
		}
	}

}
