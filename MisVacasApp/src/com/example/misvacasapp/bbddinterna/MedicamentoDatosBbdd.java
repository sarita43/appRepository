package com.example.misvacasapp.bbddinterna;

import java.util.ArrayList;

import com.example.misvacasapp.modelo.Medicamento;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class MedicamentoDatosBbdd {

	public static String tablaMedicamentos = "create table medicamento (id_medicamento varchar(15) primary key not null,"
			+ "fecha date,tipo varchar(30),descripcion varchar(200),id_vaca varchar(15);";

	public String medicamento;

	private MedicamentoBbdd mbbdd;
	private SQLiteDatabase database;

	public MedicamentoDatosBbdd(Context context) {
		mbbdd = new MedicamentoBbdd(context);
		database = mbbdd.getWritableDatabase();
	}

	public MedicamentoDatosBbdd(Context context,
			ArrayList<Medicamento> listaMedicamentos) {
		if (tablaExiste() == 0) {
			mbbdd = new MedicamentoBbdd(context);
			database = mbbdd.getWritableDatabase();
			mbbdd.onConfigure(database);
			guardarMedicamentos(listaMedicamentos);
		}
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

	public int tablaExiste() {
		return database
				.rawQuery(
						"select name from sqlite_master WHERE name='medicamento'",
						null).getCount();
	}
}
