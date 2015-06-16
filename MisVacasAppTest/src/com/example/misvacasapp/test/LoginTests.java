package com.example.misvacasapp.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.example.misvacasapp.LanzarVista;
import com.example.misvacasapp.Login;
import com.example.misvacasapp.R;
import com.example.misvacasapp.UsuarioVista;
import com.example.misvacasapp.VacaVista;

import android.app.Activity;
import android.app.Instrumentation;
import android.app.Instrumentation.ActivityMonitor;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.SystemClock;
import android.sax.StartElementListener;
import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.test.ViewAsserts;
import android.test.suitebuilder.annotation.MediumTest;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class LoginTests extends ActivityInstrumentationTestCase2<Login> {

	private TextView usuario;
	private TextView contraseña;
	private Button entrar;
	private Login login;

	public LoginTests() {
		super("com.example.misvacasapp", Login.class);
	}

	@Before
	protected void setUp() throws Exception {
		
		super.setUp();
		login = this.getActivity();
		this.usuario = (TextView) login.findViewById(R.id.usuario);
		this.contraseña = (TextView) login.findViewById(R.id.contrasena);
		this.entrar = (Button) login.findViewById(R.id.botonAceptar);
	}

	@After
	protected void tearDown() throws Exception {
		super.tearDown();
		// TODO BORRAR DATOS GUARDADOS: DATOS DEL USUARIO Y CONTRASEÑA
		SharedPreferences settings = login.getSharedPreferences("MisDatos",
				Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = settings.edit();
		editor.putString("id_usuario", "");
		editor.putString("contraseña", "");
		editor.commit();
		
	}

	@UiThreadTest
	@Test
	public void testOnClick() {
		usuario.setText("71460692");
		contraseña.setText("sara");
		

		login.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				// click button and open next activity.
				entrar.performClick();
			}
		});

	}

	@UiThreadTest
	public void testTestView() {
		usuario.setText("71460692");
		contraseña.setText("sara");
		assertEquals("71460692", usuario.getText().toString());
	}

	@Test
	public void testOnClickNuevoUsuario() {
		fail("Not yet implemented");
	}

	@Test
	public void testOnClickPedirContraseña() {
		fail("Not yet implemented");
	}

	@Test
	public void testOnCreateBundle() {
		fail("Not yet implemented");
	}

	@Test
	public void testOnCreateOptionsMenuMenu() {
		fail("Not yet implemented");
	}

	@Test
	public void testOnOptionsItemSelectedMenuItem() {
		fail("Not yet implemented");
	}

}
