package com.example.misvacasapp.bbddinterna;

import java.util.ArrayList;

import com.example.misvacasapp.llamadaWS.LlamadaVacaWS;
import com.example.misvacasapp.modelo.Vaca;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class VacaDatosBbdd {

	private ArrayList<Vaca> lista;
	public static String tablaVacas = "create table if not exists vaca (id_vaca varchar(15) primary key not null,"
			+ " raza varchar(50),fecha_nacimiento date,id_madre varchar(15),foto blob,"
			+ "id_usuario varchar(20),sexo char)";

	public static String vaca;

	private VacaBbdd vbbdd;
	private SQLiteDatabase database;

	public VacaDatosBbdd(Context context) {
		lista = new ArrayList<Vaca>();
		getVacas("71460692");
		
		System.out.println("TAMAÑO DE LA LISTAAAAAAA"+lista.size());
		for (int i = 0; i < lista.size(); i++) {
			vaca = "insert into vaca values('" + lista.get(i).getId_vaca()
					+ "','" + lista.get(i).getRaza() + "','"
					+ lista.get(i).getFecha_nacimiento() + "','"
					+ lista.get(i).getId_vaca() + "','"
					+ lista.get(i).getFoto() + "','"
					+ lista.get(i).getId_usuario() + "','"
					+ lista.get(i).getSexo() + "');";
			vbbdd = new VacaBbdd(context);
			database = vbbdd.getWritableDatabase();
			vbbdd.onCreate(database);
		}

	}

	public void getVacas(final String id_usuario) {
		Thread hilo = new Thread() {
			String res = "";
			Gson json = new GsonBuilder().setPrettyPrinting()
					.setDateFormat("dd-MM-yyyy").create();
			LlamadaVacaWS llamada = new LlamadaVacaWS();

			public void run() {
				res = llamada.LlamadaListaVacas(id_usuario);
				lista = json.fromJson(res, new TypeToken<ArrayList<Vaca>>() {
				}.getType());
			}
		};
		hilo.start();
	}

}