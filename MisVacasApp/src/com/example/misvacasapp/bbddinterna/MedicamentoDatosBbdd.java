package com.example.misvacasapp.bbddinterna;

import java.util.ArrayList;
import java.sql.Date;

import com.example.misvacasapp.modelo.Medicamento;

import android.content.Context;
import android.database.Cursor;
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

	public ArrayList<Medicamento> getMedicamentos(String id_vaca) {
		ArrayList<Medicamento> listaMedicamentos = new ArrayList<Medicamento>();
		Cursor c = database.rawQuery(
				"select * from medicamento where id_vaca='" + id_vaca + "'",
				null);
		while (c.moveToNext()) {
			Medicamento m = new Medicamento();
			m.setId_medicamento(c.getInt(0));
			m.setFecha(new Date(c.getLong(1) * 1000));
			m.setTipo(c.getString(2));
			m.setDescripcion(c.getString(3));
			m.setId_vaca(c.getString(4));
			listaMedicamentos.add(m);
		}
		return listaMedicamentos;
	}

	public Medicamento getMedicamento(String id_medicamento, String id_vaca) {
		Medicamento m = new Medicamento();
		Cursor c = database.rawQuery(
				"select * from medicamento where id_medicamento='"
						+ id_medicamento + "' and id_vaca='"+id_vaca+"'", null);
		while (c.moveToNext()) {
			m.setId_medicamento(c.getInt(0));
			m.setFecha(new Date(c.getLong(1) * 1000));
			m.setTipo(c.getString(2));
			m.setDescripcion(c.getString(3));
			m.setId_vaca(c.getString(4));
		}
		return m;
	}

}
