package com.example.misvacasapp.bbddinterna;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import com.example.misvacasapp.modelo.Vaca;
import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Clase que crea las sentencias sql para que sean ejecutadas por la base de
 * datos
 * 
 * @author sara
 * 
 */
public class VacaDatosBbdd {

	// -----------------------------------Atributos------------------------------------------//
	/** Sentecia sql crear la tabla de animales */
	public static String tablaVacas = "create table if not exists vaca "
			+ "(id_vaca varchar(15) primary key not null, raza varchar(50),"
			+ "fecha_nacimiento date,id_madre varchar(15),foto blob,id_usuario varchar(20),sexo char)";
	/** Sentencia sql para crear un animal */
	public static String vaca;
	/** Ejecuta las sentencias sql */
	private VacaBbdd vbbdd;
	/** Base de datos sql */
	private SQLiteDatabase database;

	// ------------------------------------Métodos-------------------------------------------//
	/**
	 * Contructor de la clase
	 * 
	 * @param context
	 *            Contexto
	 */
	public VacaDatosBbdd(Context context) {
		vbbdd = new VacaBbdd(context);
		database = vbbdd.getWritableDatabase();
	}

	/**
	 * Constructor de la clase. Se le pasa una lista para rellenar la base de
	 * datos, se elimina antes para rellenarla con los nuevos datos
	 * 
	 * @param context
	 *            Contexto
	 * @param lista
	 *            ArrayList<Vaca> Lista de animales que para guardar en la base
	 *            de datos
	 */
	public VacaDatosBbdd(Context context, ArrayList<Vaca> lista) {
		vbbdd = new VacaBbdd(context);
		database = vbbdd.getWritableDatabase();
		database.execSQL("drop table if exists vaca");
		vbbdd.onCreate(database);

		añadirVacas(lista);
	}

	/**
	 * Método que guarda en la base de datos los animales que tiene un usuario
	 * 
	 * @param lista
	 *            ArrayList<Vaca> Lista de animales de un usuario
	 */
	public void añadirVacas(ArrayList<Vaca> lista) {
		for (int i = 0; i < lista.size(); i++) {
			vaca = "insert into vaca values('" + lista.get(i).getId_vaca()
					+ "','" + lista.get(i).getRaza() + "','"
					+ lista.get(i).getFecha_nacimiento() + "','"
					+ lista.get(i).getId_madre() + "','"
					+ lista.get(i).getFoto() + "','"
					+ lista.get(i).getId_usuario() + "','"
					+ lista.get(i).getSexo() + "');";
			vbbdd.onUpgrade(database, 1, 1);
		}
	}

	/**
	 * Método que añade un animal a la base de datos
	 * 
	 * @param vaca
	 *            Vaca. Vaca a añadir
	 */
	public void añadirVaca(Vaca vaca) {
		VacaDatosBbdd.vaca = "insert into vaca values('" + vaca.getId_vaca()
				+ "','" + vaca.getRaza() + "','" + vaca.getFecha_nacimiento()
				+ "','" + vaca.getId_madre() + "','" + vaca.getFoto() + "','"
				+ vaca.getId_usuario() + "','" + vaca.getSexo() + "');";
		vbbdd.onUpgrade(database, 1, 1);
	}

	/**
	 * Recoge la lista de animales de un usuario y las guarda en un array list
	 * 
	 * @param id_usuario
	 *            String. Id usuario
	 * @return ArrayList<Vaca>. Lista de animales
	 */
	public ArrayList<Vaca> getListaVacas(String id_usuario) {
		ArrayList<Vaca> listaVacas = new ArrayList<Vaca>();
		Cursor c = database.rawQuery("select * from vaca where id_usuario='"
				+ id_usuario + "'", null);
		while (c.moveToNext()) {
			Vaca v = new Vaca();
			v.setId_vaca(c.getString(0));
			v.setRaza(c.getString(1));
			v.setFecha_nacimiento(new Date(c.getLong(2) * 1000));
			v.setId_madre(c.getString(3));
			v.setFoto(c.getString(4));
			v.setId_usuario(c.getString(5));
			v.setSexo(c.getString(6));
			listaVacas.add(v);
		}
		c.close();
		return listaVacas;
	}

	/**
	 * Devuelve una animal tenien su id.
	 * 
	 * @param id_vaca
	 *            String. Id del animal
	 * @return Vaca
	 */
	@SuppressLint("SimpleDateFormat")
	public Vaca getVaca(String id_vaca) {
		Vaca v = new Vaca();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date parsed = null;

		Cursor c = database.rawQuery("select * from vaca where id_vaca='"
				+ id_vaca + "'", null);
		while (c.moveToNext()) {
			try {
				parsed = sdf.parse(c.getString(2));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			v.setId_vaca(c.getString(0));
			v.setRaza(c.getString(1));
			v.setFecha_nacimiento(new Date(parsed.getTime()));
			v.setId_madre(c.getString(3));
			v.setFoto(c.getString(4));
			v.setId_usuario(c.getString(5));
			v.setSexo(c.getString(6));
		}
		c.close();
		return v;
	}

	/**
	 * Método que elimina un animal de la base de datos
	 * 
	 * @param id_vaca
	 *            String. Id vaca
	 */
	public void eliminar(String id_vaca) {
		vaca = "DELETE FROM vaca WHERE id_vaca='" + id_vaca + "';";
		vbbdd.onUpgrade(database, 1, 1);
	}

	/**
	 * Método que actualiza la foto de un animal
	 * 
	 * @param vaca
	 *            Vaca
	 */
	public void actualizarFoto(Vaca vaca) {
		VacaDatosBbdd.vaca = "update vaca set foto='" + vaca.getFoto()
				+ "' where id_vaca='" + vaca.getId_vaca() + "';";
		vbbdd.onUpgrade(database, 1, 1);
	}

	/**
	 * Método que devuelve cuantos registros de animales hay en la base de datos
	 * 
	 * @return int. Registros de animales
	 */
	public int getRegistros() {
		return database.rawQuery("select * from vaca", null).getCount();
	}

	/**
	 * Método que devuelve cuantos registros de animales de un usuario hay en la
	 * base de datos
	 * 
	 * @param id_usuario
	 *            String. Id del usuario
	 * @return int. Registros de animales
	 */
	public int getRegistros(String id_usuario) {
		return database.rawQuery(
				"select * from vaca where id_usuario='" + id_usuario + "'",
				null).getCount();
	}

}