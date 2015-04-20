package com.example.misvacasapp.bbddinterna;

import java.util.ArrayList;
import java.util.Date;

import com.example.misvacasapp.modelo.Medicamento;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class MedicamentoDatosBbdd {

	public static String tablaMedicamentos = "create table if not exists medicamento (id_medicamento varchar(20),"
			+ "fecha date,tipo varchar(40),descripcion varchar(250),id_vaca varchar(20),primary key (id_medicamento,id_vaca))";

	public static String medicamento;

	private MedicamentoBbdd mbbdd;
	private SQLiteDatabase database;

	public MedicamentoDatosBbdd(Context context) {
		mbbdd = new MedicamentoBbdd(context);
		database = mbbdd.getWritableDatabase();
	}

	public MedicamentoDatosBbdd(Context context,
			ArrayList<Medicamento> listaMedicamentos) {
		mbbdd = new MedicamentoBbdd(context);
		database = mbbdd.getWritableDatabase();
		mbbdd.onConfigure(database);
		guardarMedicamentos(listaMedicamentos);
	}

	private void guardarMedicamentos(ArrayList<Medicamento> listaMedicamentos) {
		for (int i = 0; i < listaMedicamentos.size(); i++) {
			medicamento = "insert into medicamento values('"
					+ listaMedicamentos.get(i).getId_medicamento() + "','"
					+ listaMedicamentos.get(i).getFecha() + "','"
					+ listaMedicamentos.get(i).getTipo() + "','"
					+ listaMedicamentos.get(i).getDescripcion() + "','"
					+ listaMedicamentos.get(i).getId_vaca() + "');";
			mbbdd.onUpgrade(database, i, i + 1);
		}
	}

}
