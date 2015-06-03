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

/**
 * Clase que crea las sentencias sql para que sean ejecutadas por la base de
 * datos
 * 
 * @author Sara Martínez López
 * 
 */
public class MedicamentoDatosBbdd {

	// ------------------------------------Atributos----------------------------//
	/** Sentencia sql para crear la tabla de medicamentos */
	public static String tablaMedicamentos = "create table if not exists medicamento "
			+ "(id_medicamento varchar(30) not null,fecha date,tipo varchar(40),"
			+ "descripcion varchar(250),id_vaca varchar(20) not null,"
			+ "constraint pk_medicamento primary key (id_medicamento,id_vaca));";
	/** Sentencia sql para crear un medicamento */
	public static String medicamento;
	/** Ejecuta las sentencias sql */
	private MedicamentoBbdd mbbdd;
	/** Base de datos sql */
	private SQLiteDatabase database;

	/** Formato de la fecha */
	@SuppressLint("SimpleDateFormat")
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	/** Fecha para hacer el cambio de fechas entre las bases de datos */
	java.util.Date parsed = null;

	// ----------------------------------------Métodos----------------------------------//
	/**
	 * Constructor de la clase
	 * 
	 * @param context
	 *            Contexto
	 */
	public MedicamentoDatosBbdd(Context context) {
		mbbdd = new MedicamentoBbdd(context);
		database = mbbdd.getWritableDatabase();
	}

	/**
	 * Constructor de la clase. Se le pasa una lista para rellenar la base de
	 * datos, se elimina antes para rellenarla con los nuevos datos
	 * 
	 * @param context
	 *            Contexto
	 * @param listaMedicamentos
	 *            ArrayList<Medicamento> Lista de medicamentos para guardar en
	 *            la base de datos
	 */
	public MedicamentoDatosBbdd(Context context,
			ArrayList<Medicamento> listaMedicamentos) {
		mbbdd = new MedicamentoBbdd(context);
		database = mbbdd.getWritableDatabase();
		database.execSQL("drop table if exists medicamento");
		mbbdd.onCreate(database);
		añadirMedicamentos(listaMedicamentos);
	}

	/**
	 * Guarda en la base de datos la lista de medicamentos que se pasan por
	 * parámetro
	 * 
	 * @param listaMedicamentos
	 *            ArrayList<Medicamento> Lista de medicamentos
	 */
	private void añadirMedicamentos(ArrayList<Medicamento> listaMedicamentos) {
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

	/**
	 * Método que añade un medicamento en la base de datos, pansadole por
	 * parámetro el medicamento a añadir
	 * 
	 * @param m
	 *            Medicamento. Medicamento a añadir
	 */
	public void añadirMedicamento(Medicamento m) {
		medicamento = "insert into medicamento values('"
				+ m.getId_medicamento() + "','" + m.getFecha() + "','"
				+ m.getTipo() + "','" + m.getDescripcion() + "','"
				+ m.getId_vaca() + "');";
		mbbdd.onUpgrade(database, 1, 1);
	}

	/**
	 * Recoge los medicamentos de un animal y los guarda en un arraylist de
	 * medicamentos
	 * 
	 * @param id_vaca
	 *            String Id del animal
	 * @return ArrayList<Medicamento> Lista de medicamentos que tiene un animal
	 */
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
		return listaMedicamentos;
	}

	/**
	 * Método que devuelve un medicamento de la lista de medicamentos de un
	 * animal
	 * 
	 * @param id_medicamento
	 *            String Id medicamento
	 * @param id_vaca
	 *            String Id del animal
	 * @return Medicamento
	 */
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
		return m;
	}

	/**
	 * Método que elimina todos los medicamentos de una animal
	 * 
	 * @param id_vaca
	 *            String Id del animal
	 */
	public void eliminarMedicamentos(String id_vaca) {
		medicamento = "DELETE FROM medicamento WHERE id_vaca='" + id_vaca
				+ "';";
		mbbdd.onUpgrade(database, 1, 1);
	}

	/**
	 * Método que elimina un medicamento de un animal
	 * 
	 * @param id_medicamento
	 *            String Id del medicamento
	 * @param id_vaca
	 *            String Id del animal
	 */
	public void eliminarMedicamento(int id_medicamento, String id_vaca) {
		medicamento = "DELETE FROM medicamento WHERE id_vaca='" + id_vaca
				+ "' and id_medicamento='" + id_medicamento + "';";
		mbbdd.onUpgrade(database, 1, 1);
	}

}
