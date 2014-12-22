package com.example.misvacasapp.menus;

import com.example.misvacasapp.Login;
import com.example.misvacasapp.R;
import com.example.misvacasapp.llamadaWS.LlamadaUsuarioWS;
import com.example.misvacasapp.modelo.Usuario;
import com.google.gson.Gson;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class NuevaContraseñaVista extends ActionBarActivity{
	
	private String id_usuario;
	private String contraseña;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cambiar_contrasena);
		
		Bundle bundle = getIntent().getExtras();
		id_usuario = bundle.getString("id_usuario");
		contraseña = bundle.getString("contraseña");
	}
	
	public void onClickCambioContraseña(View view){
		final TextView contraseñaActual = (TextView)findViewById(R.id.contrasena_actual_texto);
		TextView contraseñaNueva = (TextView)findViewById(R.id.nueva_contrasena_texto);
		TextView contraseñaNuevaRepetida = (TextView)findViewById(R.id.repita_nueva_contrasena_texto);
		
		Thread hilo = new Thread() {
			String res = "";
			Gson json = new Gson();
			Usuario usuario = new Usuario();
			
			LlamadaUsuarioWS llamada = new LlamadaUsuarioWS();
			
			public void run() {
				res = llamada.LlamadaUsuario(id_usuario, contraseña);
				usuario = json.fromJson(res, Usuario.class);
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						if(usuario.getContraseña().equals(contraseñaActual)){
							
						}else{
							Toast.makeText(NuevaContraseñaVista.this, "Contraseña incorrecta",
									Toast.LENGTH_LONG).show();
						}
					}
				});
			}
		};
		hilo.start();
	}
}
