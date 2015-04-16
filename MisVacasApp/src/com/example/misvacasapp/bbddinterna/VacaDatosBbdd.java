package com.example.misvacasapp.bbddinterna;

import java.util.ArrayList;
import com.example.misvacasapp.modelo.Vaca;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class VacaDatosBbdd {

	public static String tablaVacas = "create table if not exists vaca (id_vaca varchar(15) primary key not null,"
			+ " raza varchar(50),fecha_nacimiento date,id_madre varchar(15),foto blob,"
			+ "id_usuario varchar(20),sexo char)";

	public static String vaca;

	private VacaBbdd vbbdd;
	private SQLiteDatabase database;

	public VacaDatosBbdd(Context context, ArrayList<Vaca> lista) {
		vbbdd = new VacaBbdd(context);
		database = vbbdd.getWritableDatabase();
		vbbdd.onCreate(database);
		for (int i = 0; i < lista.size(); i++) {
			vaca = "insert into vaca values('" + lista.get(i).getId_vaca()
					+ "','" + lista.get(i).getRaza() + "','"
					+ lista.get(i).getFecha_nacimiento() + "','"
					+ lista.get(i).getId_vaca() + "','"
					+ lista.get(i).getFoto() + "','"
					+ lista.get(i).getId_usuario() + "','"
					+ lista.get(i).getSexo() + "');";
			vbbdd.onUpgrade(database, i, i+1);
		}

	}

	public void getIdVacas(String id_usuario) {
		Cursor c = database.rawQuery("select * from vaca where id_usuario="
				+ id_usuario, null);
		while(c.moveToNext()){
			//TODO devolver los ids o las vacas??????
			System.out.println( c.getString(0));
		}
	}
}