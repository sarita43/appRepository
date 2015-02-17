package com.example.misvacasapp.menus;

import java.util.ArrayList;

import com.example.misvacasapp.Login;
import com.example.misvacasapp.R;
import com.example.misvacasapp.adapter.AdapterListaMenu;
import com.example.misvacasapp.llamadaWS.LlamadaUsuarioWS;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
			nuevaVentana(ModificarUsuarioVista.class);
			break;
		case "\n Cambiar contraseña\n":
			nuevaVentana(NuevaContraseniaVista.class);
			break;
		case "\n Eliminar cuenta\n":
			alertaEliminarUsuario();
			break;
		default:
			break;
		}
	}
	
	private void nuevaVentana(Class ventanaNombre){
		Intent i = new Intent(this, ventanaNombre);
		i.putExtra("id_usuario",id_usuario);
		i.putExtra("contraseña",contraseña);
		startActivity(i);
	}
	
	private void eliminarCuenta(){
		Thread hilo = new Thread() {
			LlamadaUsuarioWS llamada = new LlamadaUsuarioWS();
			
			public void run() {
				
				llamada.eliminarUsuario(id_usuario);
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						
						nuevaVentana(Login.class);
						Toast.makeText(AdministrarCuentaVista.this,
								"Usuario elimidado", Toast.LENGTH_LONG)
								.show();
						finish();
					}
				});
			}
		};
		hilo.start();
	}
	
	private void alertaEliminarUsuario(){
		AlertDialog.Builder dialogo = new AlertDialog.Builder(this);
		dialogo.setMessage("¿Quiere eliminar la cuenta?");
		dialogo.setPositiveButton("Si", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				eliminarCuenta();
			}
		});
		dialogo.setNegativeButton("No", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		});
		
		dialogo.show();
	}
}
