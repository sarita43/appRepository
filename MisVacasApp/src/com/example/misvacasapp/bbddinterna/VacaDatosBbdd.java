package com.example.misvacasapp.bbddinterna;

import java.sql.Date;
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
	
	public VacaDatosBbdd(Context context){
		vbbdd = new VacaBbdd(context);
		database = vbbdd.getWritableDatabase();
	}

	public VacaDatosBbdd(Context context, ArrayList<Vaca> lista) {
			vbbdd = new VacaBbdd(context);
			database = vbbdd.getWritableDatabase();
			vbbdd.onCreate(database);
			guardarVacas(lista);
	}
	
	public void guardarVacas(ArrayList<Vaca> lista){
		for (int i = 0; i < lista.size(); i++) {
			vaca = "insert into vaca values('" + lista.get(i).getId_vaca()
					+ "','" + lista.get(i).getRaza() + "','"
					+ lista.get(i).getFecha_nacimiento() + "','"
					+ lista.get(i).getId_madre() + "','"
					+ lista.get(i).getFoto() + "','"
					+ lista.get(i).getId_usuario() + "','"
					+ lista.get(i).getSexo() + "');";
			vbbdd.onUpgrade(database, i, i+1);
		}
	}
	
	public Vaca getVaca(String id_vaca){
		Vaca v = new Vaca();
		Cursor c = database.rawQuery("select * from vaca where id_vaca='"+id_vaca+"'", null);
		while(c.moveToNext()){
			v.setId_vaca(c.getString(0));
			v.setRaza(c.getString(1));
			v.setFecha_nacimiento(new Date(c.getLong(2)*1000));
			v.setId_madre(c.getString(3));
			v.setFoto(c.getString(4));
			v.setId_usuario(c.getString(5));
			v.setSexo(c.getString(6));
		}
		return v;
	}

	public ArrayList<Vaca> getListaVacas(String id_usuario) {
		ArrayList<Vaca> listaVacas = new ArrayList<Vaca>();
		Cursor c = database.rawQuery("select * from vaca where id_usuario='"
				+ id_usuario+"'", null);
		while(c.moveToNext()){
			Vaca v = new Vaca();
			v.setId_vaca(c.getString(0));
			v.setRaza(c.getString(1));
			v.setFecha_nacimiento(new Date(c.getLong(2)*1000));
			v.setId_madre(c.getString(3));
			v.setFoto(c.getString(4));
			v.setId_usuario(c.getString(5));
			v.setSexo(c.getString(6));
			listaVacas.add(v);
		}
		
		return listaVacas;
	}
	
	public int getRegistros(){
		return database.rawQuery("select * from vaca",null).getCount();
	}
	
}