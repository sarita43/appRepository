package com.example.misvacasapp.bbddinterna;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import com.example.misvacasapp.modelo.Produccion;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class ProduccionDatosBbdd {
	// -----------------------------------Atributos------------------------------------------//
	/** Sentecia sql crear la tabla de la produccion de leche y carne */
	public static String tablaProduccion = "create table if not exists produccion "
			+ "(id_produccion integer  primary key autoincrement not null,fecha date,"
			+ "tipo varchar(10),cantidad integer);";
	/** Sentencia sql para añadir una produccion */
	public static String produccion;
	/** Ejecuta las sentencias sql */
	private ProduccionBbdd pbbdd;
	/** Base de datos sql */
	private SQLiteDatabase database;

	/** Formato de la fecha */
	@SuppressLint("SimpleDateFormat")
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	/** Fecha para hacer el cambio de fechas */
	java.util.Date parsed = null;

	// ----------------------------------------Métodos----------------------------------//
	/**
	 * Constructor de la clase
	 * 
	 * @param context
	 *            Contexto
	 */
	public ProduccionDatosBbdd(Context context) {
		pbbdd = new ProduccionBbdd(context);
		database = pbbdd.getWritableDatabase();
	}

	/**
	 * Constructor de la clase
	 * 
	 * @param context
	 *            Contexto
	 * @param listaMedicamentos
	 *            ArrayList<Medicamento> Lista de medicamentos para guardar en
	 *            la base de datos
	 */
	public ProduccionDatosBbdd(Context context,
			ArrayList<Produccion> listaProduccion) {
		pbbdd = new ProduccionBbdd(context);
		database = pbbdd.getWritableDatabase();
		database.execSQL("drop table if exists produccion");
		pbbdd.onCreate(database);
		añadirProducciones(listaProduccion);
	}

	private void añadirProducciones(ArrayList<Produccion> listaProduccion) {
		for (int i = 0; i < listaProduccion.size(); i++) {
			produccion = "insert into produccion values('"
					+ listaProduccion.get(i).getId_produccion() + "','"
					+ listaProduccion.get(i).getFecha() + "','"
					+ listaProduccion.get(i).getTipo() + "','"
					+ listaProduccion.get(i).getCantidad() + "');";
			pbbdd.onUpgrade(database, 1, 1);
		}

	}

	public void añadirProduccion(Produccion prod) {
		produccion = "insert into produccion (fecha,tipo,cantidad)values('"
				+ prod.getFecha() + "','" + prod.getTipo() + "','"
				+ prod.getCantidad() + "');";
		pbbdd.onUpgrade(database, 1, 1);
	}

	public ArrayList<Produccion> getProducciones(){
		ArrayList<Produccion> listaProduccion = new ArrayList<Produccion>();
		Cursor c = database.rawQuery(
				"select * from produccion", null);
		while (c.moveToNext()) {
			Produccion p = new Produccion();
			p.setId_produccion(c.getInt(0));
			try {
				parsed = sdf.parse(c.getString(1));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			p.setFecha(new Date(parsed.getTime()));
			p.setTipo(c.getString(2));
			p.setCantidad(c.getInt(3));
			
			listaProduccion.add(p);
		}
		return listaProduccion;
	}
	public ArrayList<Produccion> getListaCarne() {
		ArrayList<Produccion> listaProduccion = new ArrayList<Produccion>();
		Cursor c = database.rawQuery(
				"select * from produccion where tipo='Carne'", null);
		while (c.moveToNext()) {
			Produccion p = new Produccion();
			try {
				parsed = sdf.parse(c.getString(1));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			p.setFecha(new Date(parsed.getTime()));
			p.setTipo("Carne");
			p.setCantidad(c.getInt(3));
			listaProduccion.add(p);
		}
		return listaProduccion;
	}

	public ArrayList<Produccion> getListaLeche() {
		ArrayList<Produccion> listaProduccion = new ArrayList<Produccion>();
		
		Cursor c = database.rawQuery(
				"select * from produccion where tipo='Leche'", null);
		while (c.moveToNext()) {
			Produccion p = new Produccion();
			try {
				parsed = sdf.parse(c.getString(1));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			p.setFecha(new Date(parsed.getTime()));
			p.setTipo("Leche");
			p.setCantidad(c.getInt(3));
			listaProduccion.add(p);
		}
		return listaProduccion;
	}
}
