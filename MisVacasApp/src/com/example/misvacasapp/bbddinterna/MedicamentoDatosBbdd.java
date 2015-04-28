package com.example.misvacasapp.bbddinterna;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.sql.Date;

import com.example.misvacasapp.modelo.Medicamento;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class MedicamentoDatosBbdd {

	public static String tablaMedicamentos = "create table if not exists medicamento (id_medicamento varchar(20),"
			+ "fecha date,tipo varchar(40),descripcion varchar(250),id_vaca varchar(20),primary key (id_medicamento,id_vaca))";

	public static String medicamento;

	private MedicamentoBbdd mbbdd;
	private SQLiteDatabase database;
	
	@SuppressLint("SimpleDateFormat")
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	java.util.Date parsed = null;

	public MedicamentoDatosBbdd(Context context) {
		mbbdd = new MedicamentoBbdd(context);
		database = mbbdd.getWritableDatabase();
	}

	public MedicamentoDatosBbdd(Context context,
			ArrayList<Medicamento> listaMedicamentos) {
		mbbdd = new MedicamentoBbdd(context);
		database = mbbdd.getWritableDatabase();
		database.execSQL("drop table if exists medicamento");
		mbbdd.onCreate(database);
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
			mbbdd.onUpgrade(database, 1, 1);
		}
	}

	public ArrayList<Medicamento> getMedicamentos(String id_vaca) {
		ArrayList<Medicamento> listaMedicamentos = new ArrayList<Medicamento>();
		Cursor c = database.rawQuery(
				"select * from medicamento where id_vaca='" + id_vaca + "'",
				null);
		while (c.moveToNext()) {
			try {
				parsed = sdf.parse(c.getString(1));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			Medicamento m = new Medicamento();
			m.setId_medicamento(c.getInt(0));
			m.setFecha(new Date(parsed.getTime()));
			m.setTipo(c.getString(2));
			m.setDescripcion(c.getString(3));
			m.setId_vaca(c.getString(4));
			listaMedicamentos.add(m);
		}
		c.close();
		return listaMedicamentos;
	}

	public Medicamento getMedicamento(String id_medicamento, String id_vaca) {
		Medicamento m = new Medicamento();
		Cursor c = database.rawQuery(
				"select * from medicamento where id_medicamento='"
						+ id_medicamento + "' and id_vaca='" + id_vaca + "'",
				null);
		while (c.moveToNext()) {
			try {
				parsed = sdf.parse(c.getString(1));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			m.setId_medicamento(c.getInt(0));
			m.setFecha(new Date(parsed.getTime()));
			m.setTipo(c.getString(2));
			m.setDescripcion(c.getString(3));
			m.setId_vaca(c.getString(4));
		}
		c.close();
		return m;
	}

	public void añadirMedicamento(Medicamento m){
		medicamento = "insert into medicamento values('"
				+ m.getId_medicamento() + "','"
				+ m.getFecha() + "','"
				+ m.getTipo() + "','"
				+ m.getDescripcion() + "','"
				+ m.getId_vaca() + "');";
		mbbdd.onUpgrade(database, 1, 1);
	}
	
	public void eliminarMedicamento(int id_medicamento, String id_vaca) {
		medicamento = "DELETE FROM medicamento WHERE id_vaca='" + id_vaca
				+ "' and id_medicamento='" + id_medicamento + "';";
		mbbdd.onUpgrade(database, 1, 1);
	}
	
	public void eliminarMedicamentos(String id_vaca) {
		medicamento = "DELETE FROM medicamento WHERE id_vaca='" + id_vaca
				+ "';";
		mbbdd.onUpgrade(database, 1, 1);
	}

}
