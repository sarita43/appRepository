package com.example.misvacasapp;

import com.example.misvacasapp.llamadaWS.LlamadaUsuarioWS;
import com.example.misvacasapp.modelo.Usuario;
import com.google.gson.Gson;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends ActionBarActivity {
	
	private String usuario;
	private String contraseña;

	public void onClick(View v){
		
		usuario = ((TextView) findViewById(R.id.usuario)).getText().toString();
		contraseña = ((TextView) findViewById(R.id.contrasena)).getText().toString();
		
		Thread hilo = new Thread() {
			String res = "";
			
			LlamadaUsuarioWS llamada = new LlamadaUsuarioWS();
			
			public void run() {
				res = llamada.LlamadaUsuarioExistente(usuario, contraseña);
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						if (res.compareTo("true") == 0) {
							Toast.makeText(Login.this, "Conectando...",
									Toast.LENGTH_LONG).show();
							rol();
						} else if(res.compareTo("false")==0){
							Toast.makeText(Login.this,
									"Usuario no existe", Toast.LENGTH_LONG)
									.show();
						}else{
							Toast.makeText(Login.this,
									"Problemas de conexión", Toast.LENGTH_LONG)
									.show();
						}
					}
				});
			}
		};
		hilo.start();
	}
	
	private void rol(){
		Thread hilo = new Thread() {
			String res = "";
			Gson json = new Gson();
			LlamadaUsuarioWS llamada = new LlamadaUsuarioWS();
			
			public void run() {
				
				res=llamada.LlamadaUsuario(usuario, contraseña);
				final Usuario usuario = json.fromJson(res, Usuario.class);
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						if(usuario.getRol()==1){
							//Admin
						}else if(usuario.getRol()==0){
							lanzarUsuario();
						}
					}
				});
			}
		};
		hilo.start();
	}
	
	private void lanzarUsuario(){
		Intent i = new Intent(this, UsuarioVista.class);
		i.putExtra("id_usuario",usuario);
		startActivity(i);
		finish();
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.ayuda) {
			System.out.println("click menu ayuda");
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
